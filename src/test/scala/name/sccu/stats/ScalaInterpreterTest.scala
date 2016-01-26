package name.sccu.stats

import org.scalatest.FlatSpec

class ScalaInterpreterTest extends FlatSpec {

  behavior of "ScalaInterpreter"

  it should "interpret code snippets." in {
    val n = 1000000

    val start1 = new java.util.Date()
    val size1 = (1 to n) grouped 1 count {
      _ (0).toString.toInt % 3 != 0
    }
    println(s"size1 = $size1")
    val end1 = new java.util.Date()
    println(s"Time1 is ${end1.getTime - start1.getTime}")

    val list1 = (1 to n) map (_.toString) grouped 1
    val list2 = (1 to n) map (_.toString) grouped 1
    val start2 = new java.util.Date()

    val isMultipleOfThree = ScalaInterpreter.interpretPredicate("row(0).toInt % 3 == 0")
    val isNotMultipleOfThree = ScalaInterpreter.interpretPredicate("row(0).toInt % 3 != 0")
    assert(isNotMultipleOfThree.isSuccess)

    val countMultipleOfThree = list1 count isMultipleOfThree.get
    val countNotMultipleOfThree = list2 count isNotMultipleOfThree.get
    println(s"s1: $countMultipleOfThree, s2: $countNotMultipleOfThree")
    val end2 = new java.util.Date()
    println(s"Time2 is ${end2.getTime - start2.getTime}")
  }

}
