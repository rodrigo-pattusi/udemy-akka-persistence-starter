package part2_event_sourcing

import akka.actor.{ActorLogging, ActorSystem, Props}
import akka.persistence.PersistentActor

object Snapshots extends App{

  // COMMANDS
  case class ReceiveMessage(contents: String) // message FROM your contact
  case class SentMessa

  // EVENTS

  object Chat{
    def props(owner: String, contact: String) = Props(new Chat(owner, contact))
  }
  class Chat(owner: String, contact: String) extends PersistentActor with ActorLogging{

    override def persistenceId: String = s"$owner-$contact-chat"

    override def receiveCommand: Receive = ???

    override def receiveRecover: Receive = ???
  }

  val system = ActorSystem("SnapshotDemo")
  val chat = system.actorOf(Chat.props("daniel123", "martin345"))

}
