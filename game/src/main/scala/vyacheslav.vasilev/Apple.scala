package vyacheslav.vasilev

import scala.util.Random

case class Apple (x: Int, y: Int) {
  def printCoordinates(): Unit = println("Apple: (" + x + "," + y + ")")
}

object Apple {
  private val random = Random

  def apply(n: Int, body: Seq[(Int, Int)]): Apple = {
    val ap_x = random.nextInt(n)
    val ap_y = random.nextInt(n)

    if (body.contains((ap_x, ap_y)))
      apply(n, body)
    else
      new Apple(ap_x, ap_y)
  }
}