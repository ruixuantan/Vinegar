val scala3Version = "3.1.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "vinegar",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "com.lihaoyi"   %% "ujson" % "2.0.0",
      "org.scalameta" %% "munit" % "0.7.29" % Test
    )
  )
