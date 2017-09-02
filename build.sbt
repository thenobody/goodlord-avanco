name := "goodlord-avanco"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies ++= {
  val log4jVersion = "2.8.2"
  Seq(
    "com.github.scopt" %% "scopt" % "3.7.0",
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,
    "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
    "org.scalatest" %% "scalatest" % "3.0.4" % "test",
    "org.mockito" % "mockito-core" % "2.9.0" % "test"
  )
}
        