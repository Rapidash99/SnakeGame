package vyacheslav.vasilev

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import vyacheslav.vasilev.Direction._

//комменты

class GameUISpec extends AnyFlatSpec with Matchers {
  val n = 5

  "ui" should "continue game after PauseGameEvent publishing" in {
    val ui = new GameUI(n, Snake(), Apple(n, Snake.defaultBody()))
    ui.canvas.publish(PauseGameEvent())

    ui.paused shouldBe false
    ui.pausedPanel.visible shouldBe false
  }

  "ui" should "stop game after PauseGameEvent publishing" in {
    val ui = new GameUI(n, Snake(), Apple(n, Snake.defaultBody()))
    ui.canvas.publish(PauseGameEvent())
    ui.canvas.publish(PauseGameEvent())

    ui.paused shouldBe true
    ui.pausedPanel.visible shouldBe true
  }

  "ui" should "start new game after RestartGameEvent publishing" in {
    val ui = new GameUI(n, Snake(), Apple(n, Snake.defaultBody()))
    ui.snake.move(grow = false)

    ui.canvas.publish(RestartGameEvent())

    ui.snake.equals(Snake()) shouldBe true
    ui.paused shouldBe true
    ui.pausedPanel.visible shouldBe true
    ui.finished shouldBe false
    ui.gameOverPanel.visible shouldBe false
  }

  "ui" should "change snake direction after SnakeMoveEvent publishing" in {
    val ui = new GameUI(n, Snake(), Apple(n, Snake.defaultBody()))
    ui.snake.direction shouldBe Down

    ui.canvas.publish(SnakeMoveEvent(Left))
    ui.snake.direction shouldBe Left
  }

  "ui" should "end the game after snake bumping" in {
    val ui = new GameUI(n, Snake(), Apple(n, Snake.defaultBody()))
    ui.canvas.publish(PauseGameEvent())

    ui.snake.changeDirection(Left)
    if (ui.snake.willSurvive(n))
      ui.snake.move(grow = false)
    else
      ui.endGame()

    ui.paused shouldBe true
    ui.finished shouldBe true
    ui.gameOverPanel.visible shouldBe true
    ui.finalScoreLabel.text shouldBe "Final score: 0"
  }
}
