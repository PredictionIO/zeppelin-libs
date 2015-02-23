name := "zeppelin-libs"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-common" % "2.4.0",
  "org.apache.hadoop" % "hadoop-mapreduce-client-common" % "2.4.0",
  "com.twitter" % "parquet-hadoop" % "1.6.0rc3")

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
  cp filter { _.data.getName match {
    case "commons-beanutils-1.7.0.jar" => true
    case "commons-beanutils-core-1.8.0.jar" => true
    case _ => false
  }}
}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "maven", "org.slf4j", "slf4j-api", xs @ _*) => MergeStrategy.first
  case PathList("org", "apache", "hadoop", "yarn", xs @ _*) => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
