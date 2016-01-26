package name.sccu.stats

import name.sccu.csv4s.CsvParser
import org.apache.commons.cli.{CommandLineParser, DefaultParser, Options, _}

import scala.collection.mutable
import scala.io.Source
import scala.util.{Failure, Success, Try}

object Main {

  def main(args: Array[String]) {
    val options = buildOptions()
    val parseResult = parseArgOpts(options, args)
    if (parseResult.isFailure) {
      System.err.println("Parsing failed.  Reason: " + parseResult.failed.get.getMessage)
      val formatter = new HelpFormatter
      formatter.printHelp("stat", options)
      return
    }

    val cmd = parseResult.get

    val filename = cmd.getOptionValue('i')
    val src = Source.fromFile(filename)

    val parser = CsvParser(src)
    val headerOption = parser.headerOption
    val itr = parser.buffered
    val columnCount = parser.headerOption match {
      case Some(h) => h.size
      case None => if (itr.hasNext) itr.head.size else 0
    }

    println(itr.head)
    val header = if (headerOption.isDefined) headerOption.get else mutable.ArrayBuffer[String]()
    val s = Summarizer()

    s.addAggregator(new CountingAggregator())
    fieldIndices(cmd) foreach { i => s.addAggregator(new StringAggregator(i, header(i))) }

    itr.foldLeft(s) { case (agg, e) => agg(e) }

    println(s.result)
  }

  private def fieldIndices(cmd: CommandLine): Seq[Int] = {
    cmd.getOptionValue("f").
      split(",").
      map(_.trim.toInt - 1)
  }

  private def buildOptions(): Options = {
    val options = new Options

    options.addOption(Option.
      builder("f").
      hasArg().
      argName("field-num-list").
      desc("A list of field numbers separated by commas")
      build())

    options.addOption(Option.
      builder("i").
      hasArg().
      argName("input-file").
      desc("Input file path")
      build())

    options
  }

  private def parseArgOpts(options: Options, args: Array[String]): Try[CommandLine] = {
    try {
      val cliParser: CommandLineParser = new DefaultParser
      Success(cliParser.parse(options, args))
    } catch {
      case e: ParseException => Failure(e)
    }
  }


}
