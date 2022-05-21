package Config

import scala.collection.immutable.HashSet

case class CodeConfig(
    commentTokens: HashSet[String],
    braceTokens: HashSet[String],
    importKeywords: HashSet[String]
)
