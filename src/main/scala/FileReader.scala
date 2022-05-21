import java.io.File
import scala.annotation.tailrec
import scala.collection.immutable.HashSet

object FileReader:
  case class FileFilters(dirs: HashSet[String], extensions: HashSet[String], files: HashSet[String])

  case class FileAttr(fullPath: String, subDirs: Array[String], fileName: String, extension: String)

  def recursiveListFiles(dir: File): Array[File] =
    val subFolder = dir.listFiles.filter(!_.isHidden)
    subFolder ++ subFolder.filter(_.isDirectory).flatMap(recursiveListFiles)

  def transform(file: File): FileAttr =
    val fullPath = file.toString
    val split = fullPath.split("/")
    val extension = fullPath.split("\\.").last
    FileAttr(fullPath = fullPath, subDirs = split.dropRight(1), fileName = split.last, extension = extension)

  def filterFileExtension(file: FileAttr, extensions: HashSet[String]): Boolean =
    !extensions.contains(file.extension)

  def filterSubDirectory(file: FileAttr, dirs: HashSet[String]): Boolean =
    !file.subDirs.exists(dirs.contains)

  def filterFiles(file: FileAttr, files: HashSet[String]): Boolean =
    !files.contains(file.fileName)

  def getFiles(rootPath: String, fileFilters: FileFilters): Array[String] =
    recursiveListFiles(File(rootPath))
      .filter(_.isFile)
      .map(transform)
      .filter(file =>
        filterSubDirectory(file, fileFilters.dirs) &&
        filterFileExtension(file, fileFilters.extensions) &&
        filterFiles(file, fileFilters.files))
      .map(_.fullPath)
