package Analyzer

import java.io.File
import java.util.Scanner
import scala.annotation.tailrec
import scala.collection.immutable.HashSet

object Analyzer:
  def isComment(line: Array[String], commentTokens: HashSet[String]): Boolean =
    commentTokens.contains(line.head)

  def isImport(line: Array[String], importKeywords: HashSet[String]): Boolean =
    line.exists(importKeywords.contains)

  def isBrace(line: Array[String], braceTokens: HashSet[String]): Boolean =
    line.length == 1 && braceTokens.contains(line.head)

  def isEmpty(line: Array[String]): Boolean =
    line.length == 1 && line.head == "\n"

  def analyzeLine(line: Array[String], res: FileResults, config: Config): FileResults =
    if (isEmpty(line) || isBrace(line, config.braceTokens) || isComment(line, config.importKeywords))
      res.copy(totalLineCount = res.totalLineCount + 1)
    else if (isImport(line, config.importKeywords))
      res.copy(totalLineCount = res.totalLineCount + 1, importLineCount = res.importLineCount + 1)
    else
      res.copy(totalLineCount = res.totalLineCount + 1, functionalLineCount = res.functionalLineCount + 1)

  @tailrec
  def nextLine(scanner: Scanner, res: FileResults, config: Config): FileResults =
    if (scanner.hasNext()) {
      val strArr = scanner.nextLine.strip.split("\\s+")
      nextLine(scanner, analyzeLine(strArr, res, config), config)
    } else {
      res
    }

  def analyze(filePath: String, config: Config): FileResults =
    val res = FileResults()
    val scanner = new Scanner(new File(filePath))
    nextLine(scanner, res, config)
