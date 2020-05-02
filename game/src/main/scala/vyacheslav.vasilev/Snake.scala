package vyacheslav.vasilev

import vyacheslav.vasilev.Direction._

class Snake(val body: Seq[(Int, Int)], var direction: Direction) {
  val head: (Int, Int) = body.last

  def move(grow: Boolean): Snake = {
    val newBody = body :+ (head + direction.d)

    //if the snake will eat the apple, don't move the tail
    if (grow)
      Snake(newBody, direction)
    else
      Snake(newBody.drop(1), direction)
  }

  def changeDirection(direction: Direction): Unit =
    this.direction = direction

  def willSurvive(n: Int): Boolean = {
    val (x, y) = nextHead()

    //if the snake will not collide with it's body or borders
    !body.drop(1).contains((x, y)) &&
    x < n && y < n && x >= 0 && y >= 0
  }

  def willEat(apple: Apple): Boolean = {
    val (x, y) = nextHead()

    x == apple.x &&
    y == apple.y
  }

  def nextHead(): (Int, Int) =
    head + direction.d

  def nextHead(dir: Direction): (Int, Int) =
    head + dir.d

  override def equals(obj: Any): Boolean =
    obj match {
      case snake: Snake =>
        snake.head == head && snake.body == body && snake.direction == direction
      case _ =>
        false
    }

  implicit class IntTupleAdd(t: (Int, Int)) {
    def +(p: (Int, Int)): (Int, Int) = (p._1 + t._1, p._2 + t._2)
  }
}

object Snake {
  def apply(): Snake = new Snake(defaultBody(), Down)

  def defaultBody(): Seq[(Int, Int)] = Seq((0, 0), (0, 1), (0, 2))

  def apply(body: Seq[(Int, Int)], direction: Direction) = new Snake(body, direction)
}
