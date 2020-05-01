package vyacheslav.vasilev

import scala.swing.event.Event

case class SnakeMoveEvent(direction: Direction) extends Event
case class StartGameEvent() extends Event
case class PauseGameEvent() extends Event
case class RestartGameEvent() extends Event