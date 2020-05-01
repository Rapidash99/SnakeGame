package vyacheslav.vasilev

import java.awt.event.ActionEvent
import javax.swing.Timer
import scala.swing._

object Timer {
  def apply(interval: Int): Timer = new Timer(interval, null)
}

class GameUI(var n: Int, var snake: Snake, var apple: Apple) extends MainFrame {
  var paused = true
  title = "SnakeGame"

  val canvas: Canvas = new Canvas(n, snake, apple)
  val newGameButton: Button = Button("New Game") { publish(RestartGameEvent()) }
  val scoreLabel: Label = new Label("Score: 0")
  val quitButton: Button = Button("Quit") (sys.exit(0))
  val pausedLabel: Label = new Label("Paused. Press \"Space\"")
  val buttonLine: BoxPanel = new BoxPanel(Orientation.Horizontal) {
    contents += newGameButton
    contents += Swing.HGlue
    contents += scoreLabel
    contents += Swing.HGlue
    contents += quitButton
    border = Swing.EmptyBorder(5, 5, 5, 5)
  }

  restrictHeight(buttonLine)

  contents = new BoxPanel(Orientation.Vertical) {
    contents += pausedLabel
    contents += canvas
    contents += Swing.VStrut(10)
    contents += buttonLine
    border = Swing.EmptyBorder(10, 10, 10, 10)
  }

  listenTo(canvas)
  reactions += {
    case SnakeMoveEvent(direction) =>
      snake.changeDirection(direction)
    case RestartGameEvent() =>
      restartGame()
    case PauseGameEvent() =>
      pauseGame()
  }

  private def restrictHeight(s: Component): Unit =
    s.maximumSize = new Dimension(Short.MaxValue, s.preferredSize.height)

  def pauseGame(): Unit = {
    if (paused) {
      GameUI.startTimer()
      paused = false
      pausedLabel.visible = false
    }
    else {
      GameUI.stopTimer()
      paused = true
      pausedLabel.visible = true
    }
  }

  def restartGame(): Unit = {
    snake = Snake()
    apple = Apple(n, snake.body)
    canvas.snake = snake
    canvas.apple = apple
    paused = true
    pausedLabel.visible = true
    GameUI.stopTimer()
    repaint()
    canvas.requestFocus()
  }

  override def repaint(): Unit = {
    canvas.snake = snake
    canvas.apple = apple
    canvas.repaint()
    scoreLabel.text = "Score: " + score()
  }

  def score(): Int = snake.body.size - 3

}

object GameUI {
  val timer: Timer = Timer(150)

  def main(args: Array[String]): Unit = {
    val n = 16
    val ui = new GameUI(n, Snake(), Apple(n, Snake.defaultBody()))
    ui.visible = true

    def op(): Unit = {
      if (ui.snake.willSurvive(n)) {
        if (ui.snake.willEat(ui.apple)) {
          ui.snake = ui.snake.move(grow = true)
          ui.apple = Apple(n, ui.snake.body)
        }
        else
          ui.snake = ui.snake.move(grow = false)
        ui.repaint()
      }
      else
        timer.stop()
    }

    timer.addActionListener((_: ActionEvent) => op())
  }

  def startTimer(): Unit = timer.start()
  def stopTimer(): Unit = timer.stop()
  def restartTimer(): Unit = timer.restart()
}
