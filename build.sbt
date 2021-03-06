organization := "org.rogach"

name := "scallop"

scalaVersion := "2.12.0"

crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.0")

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:postfixOps",
  "-language:reflectiveCalls",
  "-language:existentials",
  "-language:implicitConversions",
  "-Xlint"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

val versRgx = """[0-9]+\.[0-9]+\.[0-9]+""".r

version := versRgx.findFirstIn(io.Source.fromFile("README.md").getLines.filter(_.contains("libraryDependencies")).mkString).get

licenses := Seq(
  "MIT License" -> url("http://www.opensource.org/licenses/mit-license.php")
)

homepage := Some(url("https://github.com/scallop/scallop"))

scmInfo := Some(
  ScmInfo(
    browseUrl = url("http://github.com/scallop/scallop"),
    connection = "scm:git:git@github.com:scallop/scallop.git"
  )
)

pomExtra := (
  <developers>
    <developer>
      <id>clhodapp</id>
      <name>Chris Hodapp</name>
      <url>http://clhodapp.net</url>
    </developer>
    <developer>
      <id>rogach</id>
      <name>Platon Pronko</name>
      <url>http://rogach.org</url>
    </developer>
  </developers>
)

pomIncludeRepository := { x => false }

publishTo := {
   val snapshot = false
   if (snapshot)
     Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
   else
     Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

scalacOptions in (Compile, doc) ++= Opts.doc.sourceUrl("https://github.com/scallop/scallop/blob/develop/€{FILE_PATH}.scala")

parallelExecution in Test := false

site.settings

site.includeScaladoc("")

ghpages.settings

git.remoteRepo := "git@github.com:scallop/scallop.git"

// fix for paths to source files in scaladoc
doc in Compile := {
  Seq("bash","-c",""" for x in $(find target/scala-2.12/api/ -type f); do sed -i "s_`pwd`/__" $x; done """).!
  (doc in Compile).value
}

enablePlugins(spray.boilerplate.BoilerplatePlugin)
