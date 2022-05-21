import Analyzer.Config
import FileReader.{FileFilters, getFiles}

import java.io.File
import scala.collection.immutable
import java.io

@main def main(path: String): Unit =
  val fileFilters = FileFilters(
    dirs = immutable.HashSet("env", "build", "__pycache__", "dist"),
    extensions = immutable.HashSet("pyc", "yml", "txt", "md", "cfg"),
    files = immutable.HashSet("LICENSE"),
  )
  val config = Config(
    commentTokens = immutable.HashSet("#", "//", "/*", "*/", "*"),
    braceTokens = immutable.HashSet("{", "}"),
    importKeywords = immutable.HashSet("import", "#include"),
  )
   val res =  getFiles(path, fileFilters)
     .map(Analyzer.analyze(_, config))
     .foldLeft(RepoResults())(_.add(_))
   print(res)