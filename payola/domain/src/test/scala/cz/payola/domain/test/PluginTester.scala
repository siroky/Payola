package cz.payola.domain.test

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import cz.payola.domain.entities.analyses.plugins.SparqlQuery
import cz.payola.domain.rdf.Graph
import cz.payola.scala2json.JSONSerializer

/*
class PluginTester extends FlatSpec with ShouldMatchers
{

    val testRDF = """<rdf:RDF
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
xmlns:dc="http://purl.org/dc/elements/1.1/"
xmlns:cc="http://creativecommons.org/ns#"
xmlns:foaf="http://xmlns.com/foaf/0.1/"
xmlns:s="http://www.w3.org/2000/01/rdf-schema#"
xmlns:owl="http://www.w3.org/2002/07/owl#"
xmlns:con="http://www.w3.org/2000/10/swap/pim/contact#"
xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#">
    <rdf:Description rdf:about="http://dig.csail.mit.edu/2008/2002/01/tr-automation/tr.rdf">
       <dc:title>W3C Standards and Technical Reports</dc:title>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf">
        <cc:license rdf:resource="http://creativecommons.org/licenses/by-nc/3.0/"/>
        <dc:title>Tim Berners-Lee's FOAF file</dc:title>
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/PersonalProfileDocument"/>
        <foaf:maker rdf:resource="http://www.w3.org/People/Berners-Lee/card#i"/>
        <foaf:primaryTopic rdf:resource="http://www.w3.org/People/Berners-Lee/card#i"/>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#cm">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <s:seeAlso rdf:resource="http://www.koalie.net/foaf.rdf"/>
        <foaf:name>Coralie Mercier</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#dj">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <s:seeAlso rdf:resource="http://www.grorg.org/dean/foaf.rdf"/>
        <foaf:homepage rdf:resource="http://www.grorg.org/dean/"/>
        <foaf:mbox rdf:resource="mailto:dean@w3.org"/>
        <foaf:mbox rdf:resource="mailto:dino@grorg.org"/>
        <foaf:mbox_sha1sum>6de4ff27ef927b9ba21ccc88257e41a2d7e7d293</foaf:mbox_sha1sum>
        <foaf:name>Dean Jackson</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#edd">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <s:seeAlso rdf:resource="http://heddley.com/edd/foaf.rdf"/>
        <foaf:homepage rdf:resource="http://heddley.com/edd/"/>
        <foaf:mbox rdf:resource="mailto:edd@usefulinc.com"/>
        <foaf:mbox rdf:resource="mailto:edd@xml.com"/>
        <foaf:mbox rdf:resource="mailto:edd@xmlhack.com"/>
        <foaf:name>Edd Dumbill</foaf:name>
        <foaf:nick>edd</foaf:nick>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#libby">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <owl:sameAs rdf:resource="http://swordfish.rdfweb.org/people/libby/rdfweb/webwho.xrdf#me"/>
        <foaf:img rdf:resource="http://swordfish.rdfweb.org/~libby/libby.jpg"/>
        <foaf:mbox rdf:resource="mailto:libby.miller@bristol.ac.uk"/>
        <foaf:name>Libby Miller</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#ss">
        <foaf:name>Susie Stephens</foaf:name>
        <foaf:organization rdf:resource="http://dbpedia.org/resource/Eli_Lilly_and_Company"/>
    </rdf:Description>
    <rdf:Description rdf:about="http://bblfish.net/people/henry/card#me">
       <foaf:name>Henry Story</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dbpedia.org/resource/John_Gage">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:img rdf:resource="http://upload.wikimedia.org/wikipedia/commons/d/de/John_Gage.jpg"/>
        <foaf:name>John Gage</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dbpedia.org/resource/John_Klensin">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:name>John Klensin</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dbpedia.org/resource/John_Markoff">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:name>John Markoff</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dbpedia.org/resource/John_Seely_Brown">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <owl:sameAs rdf:resource="http://www4.wiwiss.fu-berlin.de/bookmashup/persons/John+Seely+Brown"/>
        <foaf:homepage rdf:resource="http://www.johnseelybrown.com/"/>
        <foaf:img rdf:resource="http://transliteracies.english.ucsb.edu/images/participants/t/brown-john-seely-2.jpg"/>
        <foaf:name>John Seely Brown</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dbpedia.org/resource/Tim_Bray">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:name>Tim Bray</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/2007/01/camp/data#course">
       <foaf:maker rdf:resource="http://www.w3.org/People/Berners-Lee/card#i"/>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/2007/wiki/people/JoeLambda#JL">
       <foaf:firstName>Joe</foaf:firstName><foaf:name>Joe Lambda</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/2007/wiki/people/RobertHoffmann#RMH">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:name>Robert Hoffmann</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/breadcrumbs/blog/4">
        <dc:title>timbl's blog</dc:title>
        <s:seeAlso rdf:resource="http://dig.csail.mit.edu/breadcrumbs/blog/feed/4"/>
        <foaf:maker rdf:resource="http://www.w3.org/People/Berners-Lee/card#i"/>
    </rdf:Description>
    <rdf:Description rdf:about="http://dig.csail.mit.edu/data#DIG">
       <foaf:member rdf:resource="http://www.w3.org/People/Berners-Lee/card#i"/>
    </rdf:Description>
    <rdf:Description rdf:about="http://eikeon.com/foaf.rdf#eikeon">
       <foaf:name>Daniel Krech</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://hometown.aol.com/chbussler/foaf/chbussler.foaf#me">
       <foaf:name>Christoph Bussler</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://id.ecs.soton.ac.uk/person/1269">
       <foaf:name>Nicholas Gibbins</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://id.ecs.soton.ac.uk/person/1650">
       <foaf:name>Wendy Hall</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://id.ecs.soton.ac.uk/person/2686">
       <foaf:name>Nigel Shadbolt</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://id.ecs.soton.ac.uk/person/60">
       <foaf:name>Les Carr</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://inamidst.com/sbp/foaf#Sean">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:name>Sean Palmer</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://my.opera.com/chaals/xml/foaf#me">
       <foaf:name>Charles McCathieNevile</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://my.opera.com/danbri/xml/foaf#me">
        <s:seeAlso rdf:resource="http://danbri.livejournal.com/data/foaf"/>
        <owl:sameAs rdf:resource="http://danbri.org/foaf.rdf#danbri"/>
        <owl:sameAs rdf:resource="http://www4.wiwiss.fu-berlin.de/dblp/resource/person/336851"/>
        <foaf:mbox_sha1sum>70c053d15de49ff03a1bcc374e4119b40798a66e</foaf:mbox_sha1sum>
    </rdf:Description>
    <rdf:Description rdf:about="http://my.opera.com/howcome/xml/foaf#howcome">
       <foaf:name>Håkon Wium Lie</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://myopenlink.net/dataspace/person/kidehen#this">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:name>Kingsley Idehen</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://norman.walsh.name/knows/who#norman-walsh">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:name>Norman Walsh</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://people.apache.org/~oshani/foaf.rdf#me">
       <foaf:name>Oshani Seneviratne</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://people.csail.mit.edu/lkagal/foaf#me">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:mailbox rdf:resource="mailto:lalana@csail.mit.edu"/>
        <foaf:name>Lalana Kagal</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://people.csail.mit.edu/psz/foaf.rdf#me">
       <foaf:name>Peter Szolovits</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://people.w3.org/simon/foaf#i">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:name>Simon J. Hernandez</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://qdos.com/people/tom.xrdf#me">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:name>Tom Ilube</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://research.microsoft.com/~henrikn/foaf.xml#me">
       <foaf:name>Henrik Nielsen</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://rit.mellon.org/Members/ihf/foaf.rdf#me">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:homepage rdf:resource="http://www.mellon.org/about_foundation/staff/program-area-staff/irafuchs"/>
        <foaf:img rdf:resource="http://www.sun.com/products-n-solutions/edu/images/jelc/fuchs.jpg"/>
        <foaf:name>Ira Fuchs</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://teole.jfouffa.org/People/Teole/card.rdf#me">
       <foaf:name>Philippe Le Hégaret</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://users.ecs.soton.ac.uk/mc/mcfoaf.rdf#me">
       <foaf:name>mc schraefel</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://web.mit.edu/shinnyih/foaf.rdf#">
       <foaf:name>Shinnyih Huang</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://wiki.ontoworld.org/index.php/_IRW2006">
        <dc:title>Identity, Reference and the Web workshop 2006</dc:title>
        <con:participant rdf:resource="http://www.w3.org/People/Berners-Lee/card#i"/>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.aaronsw.com/about.xrdf#aaronsw">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <s:seeAlso rdf:resource="http://www.aaronsw.com/about.xrdf"/>
        <foaf:mbox rdf:resource="mailto:me@aaronsw.com"/>
        <foaf:name>Aaron Swartz</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.cambridgesemantics.com/people/about/lee">
       <foaf:name>Lee Feigenbaum</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.cs.umd.edu/~hendler/2003/foaf.rdf#jhendler">
       <foaf:name>Jim Hendler</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.dajobe.org/foaf.rdf#i">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:mailbox rdf:resource="mailto:dave@dajobe.org"/>
        <foaf:name>Dave Beckett</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.ecs.soton.ac.uk/~dt2/dlstuff/www2006_data#panel-panelk01">
        <s:label>The Next Wave of the Web (Plenary Panel)</s:label>
        <con:participant rdf:resource="http://www.w3.org/People/Berners-Lee/card#i"/>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.isi.edu/~gil/foaf.rdf#me">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:name>Yolanda Gill</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.ivan-herman.net/foaf.rdf#me">
        <foaf:mbox_sha1sum>5ac8032d5f6012aa1775ea2f63e1676bafd5e80b</foaf:mbox_sha1sum>
        <foaf:mbox_sha1sum>c21b7ed00d78a35efcd8e567f8fd9cca71058c5</foaf:mbox_sha1sum>
        <foaf:mbox_sha1sum>eccd01ba8ce2391a439e9b052a9fbf37eae9f732</foaf:mbox_sha1sum>
        <foaf:name>Ivan Herman</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.kjetil.kjernsmo.net/foaf#me">
       <foaf:name>Kjetil Kjernsmo</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.lassila.org/ora.rdf#me">
       <foaf:name>Ora Lassila</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.mindswap.org/2004/owl/mindswappers#Bijan.Parsia">
       <foaf:name>Bijan Parsia</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.mindswap.org/2004/owl/mindswappers#Jennifer.Golbeck">
       <foaf:name>Jennifer Golbeck</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.w3.org/People/Berners-Lee/card#amy">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <s:label>Amy van der Hiel</s:label>
        <s:seeAlso rdf:resource="http://people.w3.org/amy/foaf.rdf"/>
        <con:familyName>van der Hiel</con:familyName>
        <con:givenName>Amy</con:givenName>
        <foaf:mbox rdf:resource="mailto:amy@w3.org"/>
        <foaf:mbox_sha1sum>1839a1cc2e719a85ea7d9007f587b2899cd94064</foaf:mbox_sha1sum>
        <foaf:name>Amy van der Hiel</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.w3.org/People/Berners-Lee/card#i">
        <foaf:img rdf:resource="http://www.w3.org/Press/Stock/Berners-Lee/2001-europaeum-eighth.jpg"/>
        <foaf:knows rdf:resource="http://bblfish.net/people/henry/card#me"/>
        <foaf:knows rdf:resource="http://danbri.org/foaf#danbri"/>
        <foaf:knows rdf:resource="http://dbpedia.org/resource/John_Gage"/>
        <foaf:knows rdf:resource="http://dbpedia.org/resource/John_Klensin"/>
        <foaf:knows rdf:resource="http://dbpedia.org/resource/John_Markoff"/>
        <foaf:knows rdf:resource="http://dbpedia.org/resource/John_Seely_Brown"/>
        <foaf:knows rdf:resource="http://dbpedia.org/resource/Tim_Bray"/>
        <foaf:knows rdf:resource="http://dig.csail.mit.edu/2007/wiki/people/JoeLambda#JL"/>
        <foaf:knows rdf:resource="http://dig.csail.mit.edu/2007/wiki/people/RobertHoffmann#RMH"/>
        <foaf:knows rdf:resource="http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#cm"/>
        <foaf:knows rdf:resource="http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#edd"/>
        <foaf:knows rdf:resource="http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#libby"/>
        <foaf:knows rdf:resource="http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#ss"/>
        <foaf:knows rdf:resource="http://dig.csail.mit.edu/People/RRS"/>
        <foaf:knows rdf:resource="http://dig.csail.mit.edu/People/yosi#YES"/>
        <foaf:knows rdf:resource="http://eikeon.com/foaf.rdf#eikeon"/>
        <foaf:knows rdf:resource="http://heddley.com/edd/foaf.rdf#edd"/>
        <foaf:knows rdf:resource="http://hometown.aol.com/chbussler/foaf/chbussler.foaf#me"/>
        <foaf:knows rdf:resource="http://id.ecs.soton.ac.uk/person/1269"/>
        <foaf:knows rdf:resource="http://id.ecs.soton.ac.uk/person/1650"/>
        <foaf:knows rdf:resource="http://id.ecs.soton.ac.uk/person/2686"/>
        <foaf:knows rdf:resource="http://id.ecs.soton.ac.uk/person/60"/>
        <foaf:knows rdf:resource="http://inamidst.com/sbp/foaf#Sean"/>
        <foaf:knows rdf:resource="http://my.opera.com/chaals/xml/foaf#me"/>
        <foaf:knows rdf:resource="http://my.opera.com/howcome/xml/foaf#howcome"/>
        <foaf:knows rdf:resource="http://myopenlink.net/dataspace/person/kidehen#this"/>
        <foaf:knows rdf:resource="http://norman.walsh.name/knows/who#norman-walsh"/>
        <foaf:knows rdf:resource="http://people.apache.org/~oshani/foaf.rdf#me"/>
        <foaf:knows rdf:resource="http://people.csail.mit.edu/crowell/foaf.rdf#crowell"/>
        <foaf:knows rdf:resource="http://people.csail.mit.edu/lkagal/foaf#me"/>
        <foaf:knows rdf:resource="http://people.csail.mit.edu/psz/foaf.rdf#me"/>
        <foaf:knows rdf:resource="http://people.csail.mit.edu/ryanlee/about#ryanlee"/>
        <foaf:knows rdf:resource="http://people.w3.org/simon/foaf#i"/>
        <foaf:knows rdf:resource="http://presbrey.mit.edu/foaf.rdf#presbrey"/>
        <foaf:knows rdf:resource="http://qdos.com/people/tom.xrdf#me"/>
        <foaf:knows rdf:resource="http://research.microsoft.com/~henrikn/foaf.xml#me"/>
        <foaf:knows rdf:resource="http://rit.mellon.org/Members/ihf/foaf.rdf#me"/>
        <foaf:knows rdf:resource="http://teole.jfouffa.org/People/Teole/card.rdf#me"/>
        <foaf:knows rdf:resource="http://users.ecs.soton.ac.uk/mc/mcfoaf.rdf#me"/>
        <foaf:knows rdf:resource="http://web.mit.edu/ruthdhan/www/foaf.rdf#ruthdhan"/>
        <foaf:knows rdf:resource="http://web.mit.edu/shinnyih/foaf.rdf#"/>
        <foaf:knows rdf:resource="http://www.aaronsw.com/about.xrdf#aaronsw"/>
        <foaf:knows rdf:resource="http://www.aaronsw.com/about.xrdf#aaronsw"/>
        <foaf:knows rdf:resource="http://www.cambridgesemantics.com/people/about/lee"/>
        <foaf:knows rdf:resource="http://www.cs.umd.edu/~hendler/2003/foaf.rdf#jhendler"/>
        <foaf:knows rdf:resource="http://www.dajobe.org/foaf.rdf#i"/>
        <foaf:knows rdf:resource="http://www.isi.edu/~gil/foaf.rdf#me"/>
        <foaf:knows rdf:resource="http://www.ivan-herman.net/foaf.rdf#me"/>
        <foaf:knows rdf:resource="http://www.kjetil.kjernsmo.net/foaf#me"/>
        <foaf:knows rdf:resource="http://www.lassila.org/ora.rdf#me"/>
        <foaf:knows rdf:resource="http://www.mindswap.org/2004/owl/mindswappers#Bijan.Parsia"/>
        <foaf:knows rdf:resource="http://www.mindswap.org/2004/owl/mindswappers#Jennifer.Golbeck"/>
        <foaf:knows rdf:resource="http://www.w3.org/People/Berners-Lee/card#amy"/>
        <foaf:knows rdf:resource="http://www.w3.org/People/Connolly/#me"/>
        <foaf:knows rdf:resource="http://www.w3.org/People/EM/contact#me"/>
        <foaf:knows rdf:resource="http://www.w3.org/People/Jacobs/contact.rdf#IanJacobs"/>
        <foaf:knows rdf:resource="http://www.w3.org/People/djweitzner/foaf#DJW"/>
        <foaf:knows rdf:resource="http://www.w3.org/People/karl/karl-foaf.xrdf#me"/>
        <foaf:knows rdf:parseType="Resource">
            <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
            <s:seeAlso rdf:resource="http://dannyayers.com/me.rdf"/>
            <foaf:mbox_sha1sum>669fe353dbef63d12ba11f69ace8acbec1ac8b17</foaf:mbox_sha1sum>
            <foaf:name>Danny Ayers</foaf:name>
        </foaf:knows>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.w3.org/People/Connolly/#me">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <s:seeAlso rdf:resource="http://www.w3.org/People/Connolly/home-smart.rdf"/>
        <foaf:mbox rdf:resource="mailto:connolly@w3.org"/>
        <foaf:name>Dan Connolly</foaf:name>
        <foaf:nick>DanCon</foaf:nick>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.w3.org/People/EM/contact#me">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <s:seeAlso rdf:resource="http://www.w3.org/People/EM/contact"/>
        <foaf:homepage rdf:resource="http://purl.org/net/eric/"/>
        <foaf:img rdf:resource="http://www.ilrt.bristol.ac.uk/people/cmdjb/events/dc7/orig/eric.png"/>
        <foaf:img rdf:resource="http://www.oclc.org/~emiller/capture.jpg"/>
        <foaf:mbox rdf:resource="mailto:em@w3.org"/>
        <foaf:name>Eric Miller</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.w3.org/People/Jacobs/contact.rdf#IanJacobs">
       <foaf:name>Ian Jacobs</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.w3.org/People/djweitzner/foaf#DJW">
        <rdf:type rdf:resource="http://xmlns.com/foaf/0.1/Person"/>
        <foaf:mbox_sha1sum>032c319f439f63efba54f4fa51bfb3a3fafedfbe</foaf:mbox_sha1sum>
        <foaf:name>Daniel J Weitzner</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.w3.org/People/karl/karl-foaf.xrdf#me">
        <s:seeAlso rdf:resource="http://www.w3.org/People/karl/karl-foaf.xrdf"/>
        <foaf:mbox rdf:resource="mailto:karl@w3.org"/>
        <foaf:name>Karl Dubost</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www.w3.org/data#W3C">
        <s:label>W3C</s:label>
        <s:seeAlso rdf:resource="http://dig.csail.mit.edu/2008/2002/01/tr-automation/tr.rdf"/>
        <con:publicHomePage rdf:resource="http://www.w3.org/"/>
        <foaf:homepage rdf:resource="http://dig.csail.mit.edu/2008/"/>
        <foaf:logo rdf:resource="http://dig.csail.mit.edu/2008/Icons/w3c_home"/>
        <foaf:name>World Wide Web Consortium</foaf:name>
    </rdf:Description>
    <rdf:Description rdf:about="http://www4.wiwiss.fu-berlin.de/booksMeshup/books/006251587X">
        <dc:creator rdf:resource="http://www.w3.org/People/Berners-Lee/card#i"/>
        <dc:title>Weaving the Web: The Original Design and Ultimate Destiny of the World Wide Web</dc:title>
    </rdf:Description>
</rdf:RDF>
"""

    val selectQuery = """PREFIX foaf:    <http://xmlns.com/foaf/0.1/>
PREFIX vcard:   <http://www.w3.org/2001/vcard-rdf/3.0#>
CONSTRUCT   { <http://example.org/person#Alice> vcard:FN ?name }
WHERE       { ?x foaf:name ?name }"""

    "SPARQL query plugin" should "return valid graph" in {
        // Create the analysis
      //  val initialPlugin = new SparqlQuery()
        val graph: Graph = Graph(testRDF)

      //  val param = initialPlugin.queryParameter.createValue(selectQuery)
      //  val result = initialPlugin.evaluate(graph, List(param), { d: Double => })

        val s: JSONSerializer = new JSONSerializer()
        println(s.serialize(graph))

    }



}

*/
