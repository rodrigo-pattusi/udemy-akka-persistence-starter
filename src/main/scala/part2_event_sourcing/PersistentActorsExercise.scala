package part2_event_sourcing

import akka.actor.ActorLogging
import akka.persistence.PersistentActor

import scala.collection.mutable

object PersistentActorsExercise extends App {

  /*
    Persistent actor for avoting station
    Keep:
      - the citizens who voted
      - the poll: mapping between a candidate and the number of received votes so far

    The actor must be able to recover its state if it's shut down or restarted
   */
  case class Vote(citizenPID: String, candidate: String)

  case class VoteRecorded(citizenPID: String, candidate: String)

  class VotingStation extends PersistentActor with ActorLogging {

    // ignore the mutable state for now
    val citizens: mutable.Set[String] = new mutable.HashSet[String]()
    val poll: mutable.Map[String, Int] = new mutable.HashMap[String, Int]()


    override def persistenceId: String = ???

    override def receiveCommand: Receive = {
      case vote@Vote(citizenPID, candidate) =>
        /*
          1) create the event
          2) persist the event
          3) handle a state change after persisting is successful
         */
        persist(vote) { _ => // COMMAND sourcing
          handleInternalStateChange(vote)
        }
      case "print" =>
        log.info("Current state: ")
    }

    def handleInternalStateChange(vote: Vote): Unit = {
      if (!citizens.contains(vote.citizenPID)){
        citizens.add(vote.citizenPID)
        val votes = poll.getOrElse(vote.candidate, 0)
        poll.put(vote.candidate, votes + 1)
      }
    }

    override def receiveRecover: Receive = ???
  }

}
