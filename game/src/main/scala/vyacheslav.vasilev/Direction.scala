package vyacheslav.vasilev

sealed abstract class Direction(val d: (Int, Int)) {
  override def toString: String = d.toString()
}

object Direction {
  val values: Seq[Direction] =
    Seq(Up, Down, Left, Right)

  def findByDirection(d: (Int, Int)): Option[Direction] =
    values.find(_.d == d)

  case object Up extends Direction((0, -1))

  case object Down extends Direction((0, 1))

  case object Left extends Direction((-1, 0))

  case object Right extends Direction((1, 0))
}
