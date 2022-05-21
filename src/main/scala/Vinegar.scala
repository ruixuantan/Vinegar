import Analyzer.{Analyzer, RepoResults}
import Config.{CodeConfig, ConfigReader, FileFilters}
import FileReader.FileReader

import java.io.File
import scala.collection.immutable
import java.io

object Vinegar:
  @main def main(path: String, configPath: String): Unit =
    val configReader = ConfigReader(configPath)
    val fileReader = FileReader(path, configReader.readFileFilters)
    val analyzer = Analyzer(configReader.readCodeConfig)
    if (fileReader.dirExists) {
      println(s"Analyzing $path")
      val res = fileReader.getFiles
        .map(analyzer.analyze)
        .foldLeft(RepoResults())(_.add(_))
      println(res)
    } else {
      println(s"Directory $path not located")
    }
