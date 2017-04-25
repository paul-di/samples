name := "SimpleMapReduce"

version := "1.0"

scalaVersion := "2.11.10"

libraryDependencies += "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.7.3" % Provided
libraryDependencies += "org.apache.orc" % "orc-mapreduce" % "1.3.3"
libraryDependencies += "com.opencsv" % "opencsv" % "3.9"
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test


mainClass := Some("com.github.paul_di.samples.mr.SimpleMRMain")

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

fork := true

//adding mr libs to dependency (without scope: Provided) leeds to crashes of jar assembly due to class duplication.
// Merge strategies prevents this but can cause some unpredictable issues. Usually provided scope is better choice
assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", xs @ _*) => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
