package o1.adventure.ui

import o1.adventure.*
import scala.io.StdIn.*

/** Starts a fully text-based version of the Adventure game in the console.
  * (Cf. `startAdventureGUI`.)
  *
  * NOTE: The AdventureDraft module is not even close to being well-designed.
  * See Chapter 9.3 in the course materials. */
@main def startAdventureTextUI() =
  TextUI(Adventure()).run()


private class TextUI(val game: Adventure):
  val player = game.player

  /** Runs the game. First, a welcome message is printed, then the player gets the chance to
    * play any number of turns until the game is over, and finally a goodbye message is printed. */
  def run() =
    println(this.game.welcomeMessage)
    while !this.game.isOver do
      this.printAreaInfo()
      this.playTurn()
    println("\n" + this.game.goodbyeMessage)


  /** Prints out a description of the player character’s current location, as seen by the character. */
  def printAreaInfo() =
    val area = this.player.location
    println("\n\n" + area.name)
    println("-" * area.name.length)
    println(area.fullDescription + "\n")


  /** Requests a command from the player, plays a game turn accordingly, and prints out a
    * report of what happened.  */
  def playTurn() =
    println()
    val command = readLine("Command: ")
    val turnReport = this.game.playTurn(command)
    if turnReport.nonEmpty then
      println(turnReport)

end TextUI

