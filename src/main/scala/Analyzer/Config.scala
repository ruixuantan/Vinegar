package Analyzer

import scala.collection.immutable.HashSet

case class Config(
    commentTokens: HashSet[String],
    braceTokens: HashSet[String],
    importKeywords: HashSet[String]
)
