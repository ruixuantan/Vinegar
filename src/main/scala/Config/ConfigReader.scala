package Config

import java.nio.file.{Files, Paths}
import scala.collection.immutable.HashSet

class ConfigReader(val configPath: String):
  private val codeConfigFile = configPath + "code_config.json"
  private val fileFiltersFile = configPath + "file_filters.json"

  def arrToHashSet(array: ujson.Value): HashSet[String] =
    HashSet() ++ array.arr.map(_.str)

  def readJson(file: String): ujson.Value =
    val jsonString = Files.readString(Paths.get(file))
    ujson.read(jsonString)

  def readFileFilters: FileFilters =
    val data = readJson(fileFiltersFile)
    FileFilters(
      dirs = arrToHashSet(data("dirs")),
      extensions = arrToHashSet(data("extensions")),
      files = arrToHashSet(data("files")),
    )

  def readCodeConfig: CodeConfig =
    val data = readJson(codeConfigFile)
    CodeConfig(
      commentTokens = arrToHashSet(data("commentTokens")),
      braceTokens = arrToHashSet(data("braceTokens")),
      importKeywords = arrToHashSet(data("importKeywords")),
    )

object ConfigReader:
  def apply(configPath: String) = new ConfigReader(configPath)
