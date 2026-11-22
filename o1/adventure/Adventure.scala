package o1.adventure
import o1.adventure.Runes

/** The class `Adventure` represents text adventure games. An adventure consists of a player and
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  * N.B. This version of the class has a lot of “hard-coded” information that pertains to a very
  * specific adventure game that involves a small trip through a twisted forest. All newly created
  * instances of class `Adventure` are identical to each other. To create other kinds of adventure
  * games, you will need to modify or replace the source code of this class. */
class Adventure:

  /** the name of the game */
  val title = "Memoir"

  /** Areas */
  //TODO : Give better descriptions to the areas.
  private val pond           = Area("Pond", ("Your eyes slowly open after what feels like a long nap. Around you a mossy, overgrown land stretches far and wide." +
    " Stone cottages lean against huge rusted machines, and copper pipe grow out of the ground like tree roots." +
    " Frogs watch you from the lily pads. Little sparks drift through the air like fireflies. You look down at your feet and spot a small tin knight, next to it written in bold " +
    " MAI".map(Runes.toRunes)))
  private val clocktower     = Area("Clocktower", "A huge clocktower stands before you it's incessant ticking making your whole soul vibrate. It's door is sealed with a strange symbol")
  private val village        = Area("Moss Village", "Village of Frog people")
  private val workshop       = Area("Area", "A workshop, seems abandoned")
  private val library        = Area("Library", "All the books are written in an unknown language")
  private val clocktowerPeak = Area("Clocktower", "You can see everything from here, you know you have something to say but don't know why")

  /** World Items */
  val tinknight: Item = Item("tin knight", "Tin Knight".map(Runes.toRunes) + ". Looks broken like no one has used it for a long long time")
  
  //TODO: Change the destintion and Win condition
  private val destination = clocktowerPeak
/** Area neighbors */
  pond      .setNeighbors(Vector("south" -> clocktower))
  clocktower.setNeighbors(Vector("east" -> village, "inside" -> clocktowerPeak, "north" -> pond))
  village   .setNeighbors(Vector("west" -> clocktower, "north" -> workshop, "south" -> library))
  workshop  .setNeighbors(Vector("south" -> village))
  library   .setNeighbors(Vector("north" -> village))
  clocktowerPeak.setNeighbors(Vector("down" -> clocktower))
  //TODO
  // HOW TO ADD ITEMS : <area>.addItems(Item("Name", "Description")
  pond.addItem(tinknight)
  //TODO
  // HOW TO ADD PROJECTS : <area>.addprojects(WorkshopProject("Name", Turn count to build, "Description", materials)
  pond.addprojects(WorkshopProject("test", 3, "A test project", Vector(tinknight)))
/** Add NPCS */
  //TODO: ADD all Npcs to the game
  val ember: Item = Item("ember", "A small, warm ember that never goes out. It would make a perfect light.")

  village.addNPC(NPC("wandering trader", Vector("As you approach the mysterious individual, she smiles in the distance. \n\"Once there was a little knight...\"\nShe looks at you.",
    "\"...so that the dark wasn't scary anymore.\"")))

  village.addNPC(NPC("lamplighter", Vector(
  "\"You look like you need a light. Here, take my last ember. Make something good with it.\"",
  "\"Go on, the ember won't wait forever. Neither will dawn.\""),

  reward = Some(ember)))
  // HOW TO ADD PROJECTS : <area>.addNPC(NPC("Name", "Dialogue")
  /** The character that the player controls in the game. */
  val player = Player(pond, this)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40


  /** Determines if the adventure is complete, that is, if the player has won. */
  //TODO: Have the correct win condition
  def isComplete =
    this.player.location == this.clocktowerPeak && this.player.has("remote") && this.player.has("battery")

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount >= this.timeLimit

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "You are lost in the woods. Find your way back home.\n\nBetter hurry, 'cause Scalatut elämät is on real soon now. And you can't miss Scalkkarit, right?"


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether the player has completed their quest. */
  def goodbyeMessage =
    if this.isComplete then
      "Home at last... and phew, just in time! Well done!"
    else if this.turnCount == this.timeLimit then
      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
    else  // game over due to player quitting
      "Quitter!"


  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String): String =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      this.turnCount += 1
    outcomeReport.getOrElse(s"""Unknown command: "$command".""")

end Adventure

