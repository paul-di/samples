name := "SimpleMapReduce"

version := "1.0"

scalaVersion := "2.11.10"

libraryDependencies += "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.7.3" % Provided
libraryDependencies += "org.apache.orc" % "orc-mapreduce" % "1.3.3" % Provided
libraryDependencies += "com.opencsv" % "opencsv" % "3.9" 

mainClass := Some("com.github.paul_di.samples.mr.SimpleMRMain")

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)


fork := true
