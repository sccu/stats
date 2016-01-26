package name.sccu.stats

import scala.collection.mutable

trait Aggregator {
  def apply(row: Seq[String]): Aggregator

  def result: String
}

class CountingAggregator extends Aggregator {
  private var counter: Int = 0

  override def apply(row: Seq[String]): Aggregator = {
    counter += 1
    this
  }

  override def result: String = s"""Line Count: $counter"""
}

class StringAggregator(index: Int, title: String = "") extends Aggregator {
  private val values = mutable.ArrayBuffer[String]()

  override def apply(row: Seq[String]): StringAggregator = {
    values += row(index)
    this
  }

  override def result: String = {
    s"""${index + 1}. $title
       |  Values: ${values.take(3).mkString("", ", ", ", ...")}
       |  NonBlank: ${values.count(_.trim.nonEmpty)}""".stripMargin
  }
}


