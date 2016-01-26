package name.sccu.stats

import scala.collection.mutable

class Summarizer() {
  private val aggregators = mutable.ArrayBuffer[Aggregator]()

  def addAggregator(agg: Aggregator) = {
    aggregators += agg
  }

  def apply(row: Seq[String]): Summarizer = {
    for (agg <- aggregators) {
      agg(row)
    }
    this
  }

  def result: String = {
    aggregators map (_.result) mkString "\n"
  }
}

object Summarizer {
  def apply(): Summarizer = new Summarizer()
}



