package vyacheslav.vasilev

import scala.util.Random

case class Apple(x: Int, y: Int) {
  def values: (Int, Int) = (x, y)
}

object Apple {
  private val random = Random

  def apply(n: Int, body: Seq[(Int, Int)]): Apple = {
    val ap_x = random.nextInt(n)
    val ap_y = random.nextInt(n)

    /*if apple is spawned on the snake, just make another apple
     in average case it's faster than creating the set of all good points*/

    if (body.contains((ap_x, ap_y)))
      apply(n, body)
    else
      new Apple(ap_x, ap_y)
  }

  def apply(x: Int, y: Int): Apple = new Apple(x, y)
}
