package part2_event_sourcing

import akka.actor.{ActorLogging, ActorSystem, Props}
import akka.persistence.PersistentActor

import scala.collection.mutable

object Snapshots extends App{

  // COMMANDS
  case class ReceiveMessage(contents: String) // message FROM your contact
  case class SentMessage(contents: String) // message TO your contact

  // EVENTS
  case class ReceivedMessageRecord(id: Int, contents: String)
  case class SentMessageRecord(id: Int, contents: String)

  object Chat{
    def props(owner: String, contact: String) = Props(new Chat(owner, contact))
  }
  class Chat(owner: String, contact: String) extends PersistentActor with ActorLogging{

    val MAX_MESSAGES = 10

    var currentMessageId = 0
    val lastMessages = new mutable.Queue[]()

    override def persistenceId: String = s"$owner-$contact-chat"

    override def receiveCommand: Receive = ???

    override def receiveRecover: Receive = ???
  }

  val system = ActorSystem("SnapshotDemo")
  val chat = system.actorOf(Chat.props("daniel123", "martin345"))


}
