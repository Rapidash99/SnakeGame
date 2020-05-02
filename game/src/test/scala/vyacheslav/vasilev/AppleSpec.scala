package vyacheslav.vasilev

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AppleSpec extends AnyFlatSpec with Matchers {
  val body: Seq[(Int, Int)] = Seq((0, 0), (0, 1), (0, 2))
  val n: Int                = 5

  "apple" should "not be created inside the snake" in {
    (1 to 10000).foreach { _ =>
      val (x, y) = Apple.apply(n, body).values
      body.contains((x, y)) shouldBe false
    }
  }
  "apple" should "not be created outside the field" in {

    (1 to 10000).foreach { _ =>
      val (x, y) = Apple.apply(n, body).values
      (x >= 0 && x < n && y >= 0 && y < n) shouldBe true
    }
  }
}
