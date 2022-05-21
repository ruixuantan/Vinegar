case class FileResults(
    importLineCount: Long = 0,
    functionalLineCount: Long = 0,
    totalLineCount: Long = 0
)

case class RepoResults(
    files: Long = 0,
    importLineCount: Long = 0,
    functionalLineCount: Long = 0,
    totalLineCount: Long = 0
) {
  def add(other: FileResults): RepoResults =
    RepoResults(
      files = files + 1,
      importLineCount = importLineCount + other.importLineCount,
      functionalLineCount = functionalLineCount + other.functionalLineCount,
      totalLineCount = totalLineCount + other.totalLineCount
    )

  override def toString: String =
    s"""
      |Results:
      |#files = $files
      |#imports = $importLineCount
      |#functional lines = $functionalLineCount
      |#total lines = $totalLineCount
      |""".stripMargin
}
