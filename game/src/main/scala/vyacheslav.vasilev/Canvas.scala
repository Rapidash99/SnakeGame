package vyacheslav.vasilev

import java.awt.Color

import scala.swing._
import scala.swing.event.KeyPressed
import scala.swing.event.Key
import Direction._


class Canvas (var n: Int, var snake: Snake, var apple: Apple) extends Component {
  preferredSize = new Dimension(800, 800)
  focusable = true
  listenTo(keys)

  reactions += {
    case KeyPressed(_, Key.Up, _, _) =>
      //if he don't want to go backward or the same direction
      if (snake.body.dropRight(1).last != snake.nextHeadIf(Up) && snake.direction != Up)
      publish(SnakeMoveEvent(Up))
    case KeyPressed(_, Key.Down, _, _) =>
      if (snake.body.dropRight(1).last != snake.nextHeadIf(Down) && snake.direction != Down)
      publish(SnakeMoveEvent(Down))
    case KeyPressed(_, Key.Left, _, _) =>
      if (snake.body.dropRight(1).last != snake.nextHeadIf(Left) && snake.direction != Left)
      publish(SnakeMoveEvent(Left))
    case KeyPressed(_, Key.Right, _, _) =>
      if (snake.body.dropRight(1).last != snake.nextHeadIf(Right) && snake.direction != Right)
      publish(SnakeMoveEvent(Right))

    case KeyPressed(_, Key.Space, _, _) =>
      publish(PauseGameEvent())
  }
//  reactions += {
//    case KeyPressed(_, _, _, _) =>
//      publish(StartGameEvent())
//  }

  override def paintComponent(g: Graphics2D): Unit = {
    val d = size
    val minSide = d.height min d.width
    val x0 = (d.width - minSide) / 2 + 10
    val y0 = (d.height - minSide) / 2 + 10
    val wid = (minSide - 10) / n

    //draw green borders
    g.setColor(Color.GREEN)
    g.fillRect(x0 - 3, y0 - 3, minSide + 6, minSide + 6)

    //draw black game field
    g.setColor(Color.BLACK)
    g.fillRect(x0 + 3, y0 + 3, minSide - 6, minSide - 6)

    //draw snake body as white rectangles
    g.setColor(Color.WHITE)
    snake.body.foreach{
      case (x, y) => g.fillRect(x0 + 5 + x * wid, y0 + 5 + y * wid, wid - 10, wid - 10)
    }

    //draw snake head as a cyan rectangle
    g.setColor(Color.CYAN)
    g.fillRect(x0 + 5 + snake.head._1 * wid, y0 + 5 + snake.head._2 * wid, wid - 10, wid - 10)

    //draw apple as a red rectangle
    g.setColor(Color.RED)
    g.fillRect(x0 + 5 + apple.x * wid, y0 + 5 + apple.y * wid, wid - 10, wid - 10)
  }
}
