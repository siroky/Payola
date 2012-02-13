logLevel := Level.Warn

resolvers ++= Seq(
    DefaultMavenRepository,
    "sbt-idea-repo" at "http://mpeltonen.github.com/maven/",
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    Resolver.url("Play", url("http://download.playframework.org/ivy-releases/"))(Resolver.ivyStylePatterns)
)

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "0.11.0")

addSbtPlugin("play" % "sbt-plugin" % "2.0-RC1-SNAPSHOT")

libraryDependencies += "play" %% "play" % "2.0-RC1-SNAPSHOT"

libraryDependencies += "com.google.javascript" % "closure-compiler" % "r1741"
