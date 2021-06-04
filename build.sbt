import sbtcrossproject.{crossProject, CrossType}

name := "gansmith"

version := "0.0.1"

scalaVersion := "2.13.1"

lazy val server = (project in file("server"))
  .settings(commonSettings)
  .settings(
    name := "gansmith-server",
    scalaJSProjects := Seq(client),
    Assets / pipelineStages := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
    // triggers scalaJSPipeline when using compile or continuous compilation
    Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Seq(
      "com.vmunier" %% "scalajs-scripts" % "1.1.2",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.slick" %% "slick-codegen" % "3.3.2",
      "com.typesafe.play" %% "play-json" % "2.9.2",
      "org.postgresql" % "postgresql" % "42.2.16",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
      "org.mindrot" % "jbcrypt" % "0.4"
    ),
    // Compile the project before generating Eclipse files, so that generated .scala or .class files for views and routes are present
    EclipseKeys.preTasks := Seq(Compile / compile)

  ).enablePlugins(PlayScala).dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(commonSettings)
  .settings(
    name := "gansmith-client",
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "1.1.0",
      "org.querki" %%% "jquery-facade" % "2.0",
      "me.shadaj" %%% "slinky-core" % "0.6.7",
      "me.shadaj" %%% "slinky-web" % "0.6.7",
      "com.typesafe.play" %% "play-json" % "2.9.2"
    )
  ).enablePlugins(ScalaJSPlugin, ScalaJSWeb).dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(
    name := "Play-Videos-Shared",
    commonSettings,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %%% "play-json" % "2.9.2"
    ))

lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.12.10",
  organization := "edu.trinity",
  libraryDependencies ++= Seq(
    jdbc ,
    ehcache ,
    ws ,
    specs2 % Test ,
    guice
  )
)
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"