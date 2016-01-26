package name.sccu.stats

import org.scalatest.FlatSpec

class MainTest extends FlatSpec {

  behavior of "Main"

  it should "parser an example." in {
    Main.main(Array("-f", "1,6,9,25,26", "-i", "./example/culture_poi.csv"))
  }

  it should "print usage properly." in {
    Main.main(Array("-q"))
  }
}
