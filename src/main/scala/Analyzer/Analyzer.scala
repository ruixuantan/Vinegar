package Analyzer

import Config.CodeConfig

import java.io.File
import java.util.Scanner
import scala.annotation.tailrec
import scala.collection.immutable.HashSet

class Analyzer(val config: CodeConfig):
  def isComment(line: Array[String]): Boolean =
    config.commentTokens.contains(line.head)

  def isImport(line: Array[String]): Boolean =
    line.exists(config.importKeywords.contains)

  def isBrace(line: Array[String]): Boolean =
    line.length == 1 && config.braceTokens.contains(line.head)

  def isEmpty(line: Array[String]): Boolean =
    line.length == 1 && line.head == "\n"

  def analyzeLine(line: Array[String], res: FileResults): FileResults =
    if (isEmpty(line) || isBrace(line) || isComment(line))
      res.copy(totalLineCount = res.totalLineCount + 1)
    else if (isImport(line))
      res.copy(totalLineCount = res.totalLineCount + 1, importLineCount = res.importLineCount + 1)
    else
      res.copy(totalLineCount = res.totalLineCount + 1, functionalLineCount = res.functionalLineCount + 1)

  @tailrec
  final def nextLine(scanner: Scanner, res: FileResults): FileResults =
    if (scanner.hasNext()) {
      val strArr = scanner.nextLine.strip.split("\\s+")
      nextLine(scanner, analyzeLine(strArr, res))
    } else {
      res
    }

  def analyze(filePath: String): FileResults =
    val res = FileResults()
    val scanner = new Scanner(new File(filePath))
    nextLine(scanner, res)
    
object Analyzer:
  def apply(config: CodeConfig) = new Analyzer(config)
