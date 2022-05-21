import Analyzer.{Config, RepoResults}
import FileReader.{FileFilters, FileReader}

import java.io.File
import scala.collection.immutable
import java.io

object Vinegar extends App:
  val fileFilters = FileFilters(
    dirs = immutable.HashSet("env", "build", "__pycache__", "dist"),
    extensions = immutable.HashSet("pyc", "yml", "txt", "md", "cfg"),
    files = immutable.HashSet("LICENSE"),
  )
  val config = Config(
    commentTokens = immutable.HashSet("#", "//", "/*", "*/", "*"),
    braceTokens = immutable.HashSet("{", "}", "(", ")"),
    importKeywords = immutable.HashSet("import", "#include"),
  )

  @main def main(path: String): Unit =
    val fileReader = FileReader(path)
    if (fileReader.dirExists) {
      val res = fileReader.getFiles(fileFilters)
        .map(Analyzer.Analyzer.analyze(_, config))
        .foldLeft(RepoResults())(_.add(_))
      print(res)
    } else {
      print(s"Directory $path not located")
    }
