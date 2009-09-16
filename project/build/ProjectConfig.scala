import sbt._

class ProjectConfig(info: ProjectInfo) extends DefaultProject(info)
{
  val scalaTest = "org.scalatest" % "scalatest" % "0.9.5" 
  val jetlang = "org.jetlang" %  "jetlang"  % "0.2.0"
  val jetlangrepo = "Jetlang Repository for Maven" at "http://jetlang.googlecode.com/svn/repo/"
  
}