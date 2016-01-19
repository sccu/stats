package name.sccu.stats

import scala.tools.nsc.interpreter._
import scala.util.{Failure, Success, Try}

import scala.tools.nsc.Settings

object ScalaInterpreter {
  private val defaultSettings = {
    val s = new Settings()
    s.usejavacp.value = true
    s
  }

  private val interpreter = new IMain(defaultSettings)

  def interpretPredicate(code: String): Try[Seq[String] => Boolean] = {
    val result = interpreter.quietRun(s"val predicate: Seq[String] => Boolean = row => { $code }")
    result match {
      case IR.Success =>
        Success(interpreter.valueOfTerm("predicate").get.asInstanceOf[Seq[String] => Boolean])
      case _ =>
        Failure(new IllegalArgumentException(s"""Failed to parse code: "$code"."""))

    }
  }

}
