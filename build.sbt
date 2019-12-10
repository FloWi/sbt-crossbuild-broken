val scala_2_13 = "2.13.1"
val scala_2_12 = "2.12.10"
val scala_2_11 = "2.11.12"

val commonDependencies = Seq(
  "org.typelevel"              %% "cats-core"     % catsVersion,
)


resolvers += Resolver.bintrayRepo("cakesolutions", "maven") //needed for scala-kafka-client

lazy val commonSettings = Seq(
  addCompilerPlugin(
    "org.typelevel" %% "kind-projector" % kindProjectorPluginVersion
  ),
  organization := "de.flwi",
  name         := "sbt-crossbuild-broken-buildimage",
  scalaVersion := scala_2_12,
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
  javaOptions ++= Seq(
    "-Xms512M",
    "-Xmx2048M",
    "-XX:MaxPermSize=2048M",
    "-XX:+CMSClassUnloadingEnabled"
  ),
  parallelExecution in Test := false,
  //fork := true,
  // Only necessary for SNAPSHOT releases
  resolvers     += Resolver.sonatypeRepo("snapshots"),
  scalacOptions ++= Seq("-deprecation", "-unchecked"),
// compilerPlugin for < 2.13
  libraryDependencies ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n >= 13 => Nil
      case _                       => compilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full) :: Nil
    }
  },
  Compile / scalacOptions ++= {
    CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n >= 13 => "-Ymacro-annotations" :: Nil
      case _                       => Nil
    }
  },

  libraryDependencies ++= commonDependencies
)

// to make sbt happy about crossBuild you need to have a subProject that has crossScalaVersions
lazy val sampleProject = (project in file("sampleProject"))
  .settings(
    commonSettings,
    name := "sampleProject",
    crossScalaVersions := Seq(scala_2_11, scala_2_12, scala_2_13)
  )

lazy val root = (project in file("."))
  .aggregate(sampleProject)
  .settings(
    commonSettings,
    crossScalaVersions := Seq()
  )
