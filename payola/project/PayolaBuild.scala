import scala.collection.mutable
import mutable.ListBuffer
import scala.io.Source
import scala.tools.nsc.io
import sbt._
import Keys._
import PlayProject._

object PayolaBuild extends Build
{
    val compileAndPackage = TaskKey[File]("cp", "Compiles and packages the project in one step.")

    val cleanBeforeTests = TaskKey[Unit]("clean-before-tests", "Cleans the test target directories.")

    /** Common settings of all projects. */
    object Settings
    {
        val scalaVersion = "2.9.1"
    }

    /** Common settings of the S2Js projects. */
    object S2JsSettings
    {
        val version = "0.2"

        val compilerJarName = "s2js-compiler_%s-%s.jar".format(Settings.scalaVersion, version)

        val adaptersJarName = "s2js-adapters_%s-%s.jar".format(Settings.scalaVersion, version)

        val compilerTestsTarget = file("s2js/compiler/target/tests")
    }

    /** Common settings of the Payola projects. */
    object PayolaSettings
    {
        val version = "0.1"

        val organization = "Payola"

        val libDir = file("lib")
    }

    /** Settings of the web project. */
    object WebSettings
    {
        val serverBaseDir = file("web/server")

        val javascriptsDir = serverBaseDir / "public/javascripts"
    }

    /** Common default settings of all projects. */
    val defaultSettings = Defaults.defaultSettings ++ Seq(
        scalaVersion := Settings.scalaVersion,
        scalacOptions ++= Seq(
            "-deprecation",
            "-unchecked",
            "-encoding", "utf8"
        ),
        libraryDependencies ++= Seq(
            "org.scalatest" %% "scalatest" % "1.6.1" % "test"
        ),
        resolvers ++= Seq(
            DefaultMavenRepository
        ),
        compileAndPackage <<= (packageBin in Compile),
        (test in Test) <<= (test in Test).dependsOn(compileAndPackage)
    )

    /** Common default settings of the S2Js projects. */
    val s2JsSettings = defaultSettings ++ Seq(
        version := S2JsSettings.version
    )

    /** Common settings of the Payola projects. */
    val payolaSettings = defaultSettings ++ Seq(
        version := PayolaSettings.version,
        organization := PayolaSettings.organization
    )

    /**
      * The Payola solution. All projects have to be listed in the aggregate method.
      */
    lazy val payolaProject = Project(
        "payola", file("."), settings = payolaSettings
    ).aggregate(
        s2JsProject, scala2JsonProject, dataProject, modelProject, webProject
    )

    lazy val s2JsProject = Project(
        "s2js", file("s2js"), settings = s2JsSettings
    ).aggregate(
        s2JsAdaptersProject, s2JsCompilerProject, s2JsRuntimeProject
    )

    lazy val s2JsAdaptersProject = Project(
        "adapters", file("s2js/adapters"),
        settings = s2JsSettings ++ Seq(
            compileAndPackage <<= compileAndPackage.map {jarFile: File =>
                IO.copyFile(jarFile, PayolaSettings.libDir / S2JsSettings.adaptersJarName)
                jarFile
            }
        )
    )

    lazy val s2JsCompilerProject = Project(
        "compiler", file("s2js/compiler"),
        settings = s2JsSettings ++ Seq(
            libraryDependencies ++= Seq(
                "org.scala-lang" % "scala-compiler" % Settings.scalaVersion
            ),
            testOptions ++= Seq(
                Tests.Argument("-Dwd=" + S2JsSettings.compilerTestsTarget.absolutePath),
                Tests.Argument("-Dcp=" + new io.Directory(PayolaSettings.libDir).files.map(_.path).mkString(";"))
            ),
            cleanBeforeTests := {
                new io.Directory(S2JsSettings.compilerTestsTarget).deleteRecursively()
            },
            compileAndPackage <<= compileAndPackage.map {jarFile: File =>
                IO.copyFile(jarFile, PayolaSettings.libDir / S2JsSettings.compilerJarName)
                jarFile
            },
            (test in Test) <<= (test in Test).dependsOn(cleanBeforeTests)
        )
    ).dependsOn(
        s2JsAdaptersProject
    )

    /** A project that is compiled to JavaScript using Scala to JavaScript compiler (beside standard compilation). */
    object ScalaToJsProject
    {
        val compilerJar = file("lib/" + S2JsSettings.compilerJarName)

        val adaptersJar = file("lib/" + S2JsSettings.adaptersJarName)

        def apply(name: String, path: File, outputDir: File, projectSettings: Seq[Project.Setting[_]]) = {
            Project(
                name, path,
                settings = projectSettings ++ Seq(
                    scalacOptions ++= Seq(
                        "-Xplugin:" + compilerJar.absolutePath,
                        "-P:s2js:outputDirectory:" + outputDir.absolutePath
                    ),
                    clean <<= clean.map {_ =>
                        new io.Directory(outputDir).deleteRecursively()
                    }
                )
            ).dependsOn(
                s2JsAdaptersProject, s2JsCompilerProject
            )
        }
    }

    lazy val s2JsRuntimeProject = ScalaToJsProject(
        "runtime", file("s2js/runtime"), WebSettings.javascriptsDir / "runtime", s2JsSettings
    )

    lazy val scala2JsonProject = Project(
        "scala2json", file("scala2json"), settings = payolaSettings
    )

    lazy val dataProject = Project(
        "data", file("data"),
        settings = payolaSettings ++ Seq(
            libraryDependencies ++= Seq(
                "joda-time" % "joda-time" % "2.0",
                "org.apache.jena" % "jena-core" % "2.7.0-incubating"
                //"com.hp.hpl.jena" % "jena" % "2.6.4"
            )
        )
    ).dependsOn(
        scala2JsonProject
    )

    lazy val modelProject = Project(
        "model", file("model"), settings = payolaSettings
    ).dependsOn(
        scala2JsonProject, dataProject
    )

    lazy val webProject = Project(
        "web", file("web"), settings = payolaSettings
    ).aggregate(
        webSharedProject, webClientProject, webServerProject
    )

    lazy val webSharedProject = ScalaToJsProject(
        "shared", file("web/shared"), WebSettings.javascriptsDir / "shared", payolaSettings
    )

    lazy val webClientProject = ScalaToJsProject(
        "client", file("web/client"), WebSettings.javascriptsDir / "client", payolaSettings
    ).dependsOn(
        webSharedProject
    )

    lazy val webServerProject = PlayProject(
        "server", PayolaSettings.version, Nil, path = file("web/server"), mainLang = SCALA
    ).settings(
        compileAndPackage <<= (packageBin in Compile).map {jarFile: File =>
            // Retrieve the dependencies.
            val files = new io.Directory(WebSettings.javascriptsDir).deepFiles.filter(_.extension == "js")
            val providedSymbolFiles = new mutable.HashMap[String, String]
            val fileRequiredSymbols = new mutable.HashMap[String, mutable.ArrayBuffer[String]]
            files.foreach {file =>
                val path = file.toAbsolute.path.toString
                fileRequiredSymbols += path -> new mutable.ArrayBuffer[String]

                val fileContent = Source.fromFile(path).getLines.mkString
                """goog\.provide\(\s*['\"]([^'\"]+)['\"]\s*\);""".r.findAllIn(fileContent).matchData.foreach {m =>
                    providedSymbolFiles += m.group(1) -> path
                }
                """goog\.require\(\s*['\"]([^'\"]+)['\"]\s*\);""".r.findAllIn(fileContent).matchData.foreach {m =>
                    fileRequiredSymbols(path) += m.group(1)
                }
            }
            
            // Check whether all required symbols are provided.
            val errorFile = fileRequiredSymbols.find(_._2.exists(file => !providedSymbolFiles.contains(file)))
            if (errorFile.isDefined) {
                throw new Exception("Dependency '%s' declared in te file '%s' wasn't found.".format(
                    errorFile.get._2.find(file => !providedSymbolFiles.contains(file)).get.toString,
                    errorFile.get._1.toString
                ))
            }

            // Construct the file dependency graph from the symbol dependency graph.
            val fileDependencyGraph = fileRequiredSymbols.mapValues(_.map(o => providedSymbolFiles(o)))

            /**
              * Creates a single JavaScript file containing all required dependencies for the specified entry point.
              * @param entryPointSymbol The symbol that will be used in the html page as an entry point to the
              *     JavaScript application.
              */
            def glueScript(entryPointSymbol: String) {
                if (!providedSymbolFiles.contains(entryPointSymbol)) {
                    throw new Exception("The entry point '%s' wasn't found.".format(entryPointSymbol))
                }
                val processedFiles = new mutable.HashSet[String]
                val visitedFiles = new mutable.HashSet[String]
                val buffer = new ListBuffer[String]

                def processFile(file: String) {
                    if (!processedFiles.contains(file)) {
                        if (visitedFiles.contains(file)) {
                            throw new Exception("A cycle in JavaScript file dependencies detected. " +
                                "Check the file '%s'.".format(file))
                        }
                        visitedFiles += file

                        fileDependencyGraph(file).foreach(processFile(_))

                        buffer += "////////////////////////////////////////////////////////////////////////////////"
                        buffer += "// %s".format(file)
                        buffer += "////////////////////////////////////////////////////////////////////////////////"
                        buffer ++= Source.fromFile(file).getLines
                        buffer += "\n\n"
                        
                        visitedFiles -= file
                        processedFiles += file
                    }
                }
                
                // Load the necessary libraries.
                processFile((WebSettings.javascriptsDir / "bootstrap.js").absolutePath.toString)
                processFile(providedSymbolFiles(entryPointSymbol))
                new io.File(WebSettings.javascriptsDir / (entryPointSymbol + ".js")).writeAll(buffer.mkString("\n"))
            }
            
            glueScript("cz.payola.web.client.presenters.Index")
            jarFile
        }
    ).dependsOn(
        webSharedProject, webClientProject
    )
}
