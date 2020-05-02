package vyacheslav.vasilev

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import vyacheslav.vasilev.Direction._

class SnakeSpec extends AnyFlatSpec with Matchers {
  val n = 5

  "snake" should "not survive if it goes into the wall" in {
    var snake = Snake(Seq((0, 2), (0, 1), (0, 0)), Up)
    snake.willSurvive(n) shouldBe false

    snake.changeDirection(Left)
    snake.willSurvive(n) shouldBe false

    snake = Snake(Seq((4, 2), (4, 3), (4, 4)), Down)
    snake.willSurvive(n) shouldBe false

    snake.changeDirection(Right)
    snake.willSurvive(n) shouldBe false
  }

  "snake" should "not survive if it goes into itself" in {
    var snake = Snake(Seq((0, 2), (0, 1), (0, 0)), Down)
    snake.willSurvive(n) shouldBe false

    snake = Snake(Seq((2, 0), (3, 0), (4, 0)), Left)
    snake.willSurvive(n) shouldBe false

    snake = Snake(Seq((4, 2), (4, 3), (4, 4)), Up)
    snake.willSurvive(n) shouldBe false

    snake = Snake(Seq((2, 4), (1, 4), (0, 4)), Right)
    snake.willSurvive(n) shouldBe false
  }

  "snake" should "survive if it doesn't goes into the wall or itself" in {
    var snake = Snake()
    snake.willSurvive(n) shouldBe true

    snake = snake.move(grow = false)
    snake.willSurvive(n) shouldBe true

    snake.changeDirection(Right)

    snake = snake.move(grow = false)
    snake.willSurvive(n) shouldBe true

    snake = snake.move(grow = false)
    snake.willSurvive(n) shouldBe true
  }

  "snake" should "survive if it goes into the free cell that is currently occupied by its tail" in {
    val snake = Snake(Seq((0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (1, 1), (1, 2), (1, 3)), Left)
    snake.willSurvive(n) shouldBe true
  }

  "snake" should "not survive if it goes into the occupied cell that is currently occupied by its tail" in {
    val snake = Snake(Seq((0, 4), (0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (1, 1), (1, 2), (1, 3)), Left)
    snake.willSurvive(n) shouldBe false
  }

  "snake" should "correctly understand if there are apple in front of him" in {
    val snake = Snake(Seq((0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (1, 1), (1, 2), (1, 3)), Down)
    var apple = Apple(1, 4)
    snake.willEat(apple) shouldBe true

    apple = Apple(2, 3)
    snake.willEat(apple) shouldBe false
  }

  "snake.move" should "return correct new snake" in {
    var snake = Snake(Seq((0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (1, 1), (1, 2)), Down)
    snake = snake.move(grow = false)

    snake.body shouldBe Seq((0, 2), (0, 1), (0, 0), (1, 0), (1, 1), (1, 2), (1, 3))
    snake.head shouldBe (1, 3)
    snake.direction shouldBe Down

    snake = snake.move(grow = true)

    snake.body shouldBe Seq((0, 2), (0, 1), (0, 0), (1, 0), (1, 1), (1, 2), (1, 3), (1, 4))
    snake.head shouldBe (1, 4)
    snake.direction shouldBe Down
  }

  "snake.nextHead" should "return correct next head" in {
    val snake = Snake(Seq((0, 3), (0, 2), (0, 1), (0, 0), (1, 0), (1, 1), (1, 2)), Down)
    snake.nextHead() shouldBe (1, 3)
    snake.nextHead(Right) shouldBe (2, 2)
  }
}
