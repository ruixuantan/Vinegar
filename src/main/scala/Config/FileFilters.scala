package Config

import scala.collection.immutable.HashSet

case class FileFilters(dirs: HashSet[String], extensions: HashSet[String], files: HashSet[String])
