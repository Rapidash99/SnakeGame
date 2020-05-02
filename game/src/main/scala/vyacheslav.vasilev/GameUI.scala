package vyacheslav.vasilev

import java.awt.event.ActionEvent

import javax.swing.Timer

import scala.swing._

object Timer {
  def apply(interval: Int): Timer = new Timer(interval, null)
}

class GameUI(var n: Int, var snake: Snake, var apple: Apple) extends MainFrame {
  title = "SnakeGame"
  val canvas: Canvas        = new Canvas(n, snake, apple)
  val newGameButton: Button = Button("New Game") { publish(RestartGameEvent()) }
  val scoreLabel: Label     = new Label("Score: 0")
  val quitButton: Button    = Button("Quit")(sys.exit(0))
  val pausedPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += new Label("Paused")
    contents += new Label("Press \"Space\" to continue")
  }
  val finalScoreLabel: Label = new Label("Final score: 0")
  val gameOverPanel: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += new Label("Game Over")
    contents += finalScoreLabel
    contents += new Label("Press \"Space\" to continue")
    contents += new Label("Click \"New Game\" to restart")
  }
  val buttonLine: BoxPanel = new BoxPanel(Orientation.Horizontal) {
    contents += newGameButton
    contents += Swing.HGlue
    contents += scoreLabel
    contents += Swing.HGlue
    contents += quitButton
    border = Swing.EmptyBorder(5, 5, 5, 5)
  }
  var paused = true

  gameOverPanel.visible = false
  var finished = false

  restrictHeight(buttonLine)

  contents = new BoxPanel(Orientation.Vertical) {
    contents += pausedPanel
    contents += gameOverPanel
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
      if (finished)
        continueGame()
      else
        pauseGame()
  }

  def pauseGame(): Unit = {
    if (paused) {
      paused = false
      pausedPanel.visible = false
      GameUI.startTimer()
    } else {
      GameUI.stopTimer()
      paused = true
      pausedPanel.visible = true
    }
  }

  def endGame(): Unit = {
    GameUI.stopTimer()
    paused = true
    finished = true
    gameOverPanel.visible = true
    finalScoreLabel.text = "Final score: %d".format(score())
  }

  def continueGame(): Unit = {
    paused = false
    gameOverPanel.visible = false
    finished = false
    GameUI.startTimer()
    repaint()
  }

  def restartGame(): Unit = {
    GameUI.stopTimer()
    snake = Snake()
    apple = Apple(n, snake.body)
    canvas.snake = snake
    canvas.apple = apple
    paused = true
    pausedPanel.visible = true
    gameOverPanel.visible = false
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

  private def restrictHeight(s: Component): Unit =
    s.maximumSize = new Dimension(Short.MaxValue, s.preferredSize.height)
}

object GameUI {
  val timer: Timer = Timer(150)

  def main(args: Array[String]): Unit = {
    val n  = 16
    val ui = new GameUI(n, Snake(), Apple(n, Snake.defaultBody()))
    ui.visible = true

    def op(): Unit = {
      if (ui.snake.willSurvive(n)) {
        if (ui.snake.willEat(ui.apple)) {
          ui.snake = ui.snake.move(grow = true)
          ui.apple = Apple(n, ui.snake.body)
        } else
          ui.snake = ui.snake.move(grow = false)
        ui.repaint()
      } else
        ui.endGame()
    }

    timer.addActionListener((_: ActionEvent) => op())
  }

  def startTimer(): Unit   = timer.start()
  def stopTimer(): Unit    = timer.stop()
  def restartTimer(): Unit = timer.restart()
}
