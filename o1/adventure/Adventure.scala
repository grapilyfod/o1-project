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
  val title = "MAI"

  /** Areas */
  //TODO : Give better descriptions to the areas.
  private val pond           = Area("Pond", ("Your eyes slowly open after what feels like a long nap. Around you a mossy, overgrown land stretches far and wide." +
    " Stone cottages lean against huge rusted machines, and copper pipes grow out of the ground like tree roots." +
    " Frogs watch you from the lily pads. Little sparks drift through the air like fireflies. You look down at your feet and spot a small tin knight, next to it written in bold: " +
    " MAI".map(Runes.toRunes)))
  private val crossroads     = Area("Crossroads", "The pond path ends in a clearing in the tall grass beneath a leaning lamppost with no flame in it. A crooked signpost points to the path further with diffrent words carved into it:" + " Moss Village".map(Runes.toRunes)
    + " to the north-east" + " Clocktower".map(Runes.toRunes)
    + "to the north. Far above the trees, a clocktower's hands creep forward with a slow, patient tick")
  private val clocktower     = Area("Clocktower",  "The clocktower rises above everything, it's ticking so loud you can feel it in your heart. "
    + "The great door at its foot is sealed with a ring of runes : " + " clock".map(Runes.toRunes) + "Behind it, the tick counts down the hours until dawn.")
  private val village        = Area("Moss Village", "Crooked cottages with roofs of thick green moss huddle around a well that hums quietly to itself. Strings of dim paper lanters hand between the chimneys, and from every window comes the soft croak of frogs telling eachother stories." +
    "A wooden sign at the village gate reads " + "moss village" + "Beneath the tick of the clocktower, nobody seems to be in a hurry.")
  private val workshop       = Area("Workshop", "Rusted gears the size of carts lean against walls of scrap and copper, and half-finished machines sit under sheets of dusty cloth. Three blueprints are pinned to the far wall, each with a rough drawing and a rune label underneath: "
  + "dragon" + "beside a coiled, winged machine," + "frog choir" + "beside a row of tiny singing frogs, and " + "lantern knight " + "beside a small armoured figure holding a light. " +
    "A fourth, newer sheet shows a crystal music box labelled " + " quartz jukebox" + ". Someone spent a lifetime here, and it feels like they only just stepped out.")
  private val library        = Area("Library",  "Tall shelves curl up into the fog, packed with books whose pages are blank where the mist has eaten the words. A whisper drifts between the shelves, soft and kind, telling you that forgetting doesn't hurt. " +
    "A few books have survived, their covers still marked with careful rune titles. It is quiet here, and tempting to stay.")
  private val clocktowerPeak = Area("Clocktower Peak", "At the top, the whole land lies spread beneath you: the pond, the village, the workshop and the library, each with its own glow of sparks. The break of dawn is drawing nearer " +
    "In front of the clock face stands a smooth stone panel with three empty slots in a row, as if waiting for something to be carved into it." +
    " You feel sure you have something to say, and you almost remember what.")

  private val wilderness     = Area("Wilderness", "In front of you lies an empty wilderness with paths twisting away in every direction.\n" +
    "In the middle of the area,you can see an old wooden sign with runic symbols carved on it.\n" +
    " 2" + "south".map(Runes.toRunes) +
    " 1 " + "west".map(Runes.toRunes) +
    " 1 " + "north".map(Runes.toRunes) +
    " 1 " + "west".map(Runes.toRunes))
  private val text = "Only deserted land in the distance. Every path seems to lead the same way with no end in sight."
  private val maze_1 = Area("Wilderness", this.text)
  private val maze_2 = Area("Wilderness", this.text)
  private val maze_3 = Area("Wilderness", this.text)
  private val maze_4 = Area("Wilderness", this.text)
  private val hidden_valley = Area("Valley of the damned", "You see light at the end of the tunnel. A flower valley appears before your eyes.\n" +
    "You get the sense of feeling that nobody has stepped foot onto this area for years.\n" +
    "There is a wooden chest in the middle of the area, surrounded by flowers of all kind.")
  /** World Items */
  val knight_figurine: Item = Item("knight figurine", "Looks broken like no one has used it for a long long time")
  val ember: Item = Item("ember", "A small, warm ember that never goes out. ")
  val old_book: Item = Item("old book", "The cover reads: " + "stories".map(Runes.toRunes) + "\n" + "Inside, only one tale survives: a little knight who carried a light, so that the dark wasn't scary anymore.")
  val diary: Item = Item("diary", "A diary in shaky handwriting. Most pages are blank, eaten by the fog. One survives:\n" + "\"She's scared of the dark.\n" + "Just needs a bit of light.\"")
  val runic_page: Item =Item("torn page", "A page covered entirely in symbols:\n" + "the light remembers what the mind forgets".map(Runes.toRunes))
  val workshop_key: Item = Item("key", "A golden key. Seems dusty.\n" + "Could be useful somewhere in the village.")
  val drawing: Item = Item("drawing", "A crayon drawn warrior on a paper. Underneath contains written letters:\n" + "grandpa".map(Runes.toRunes) + ". Mai")
  //TODO: Change the destintion and Win condition
  /** Area neighbors */
  pond      .setNeighbors(Vector("south" -> crossroads))
  crossroads.setNeighbors(Vector("south" -> clocktower, "north" -> pond))
  clocktower.setNeighbors(Vector("east" -> village, "inside" -> clocktowerPeak, "north" -> crossroads))
  village   .setNeighbors(Vector("west" -> clocktower, "north" -> workshop, "south" -> library, "east" -> wilderness))
  workshop  .setNeighbors(Vector("south" -> village))
  library   .setNeighbors(Vector("north" -> village))
  clocktowerPeak.setNeighbors(Vector("down" -> clocktower))
  wilderness.setNeighbors(Vector("west" -> village, "south" -> maze_1, "east" -> wilderness, "north" -> wilderness))
  hidden_valley.setNeighbors(Vector("out" -> wilderness))
  def maze_path(current_area: Area, real_path: String, next: Area) =
  for path <- Vector("south", "east", "north", "west") do
    if path == real_path then
      current_area.setNeighbor(path, next)
    else
      current_area.setNeighbor(path, wilderness)
  maze_path(this.maze_1, "south", this.maze_2)
  maze_path(this.maze_2, "west", this.maze_3)
  maze_path(this.maze_3, "north", this.maze_4)
  maze_path(this.maze_4, "west", this.hidden_valley)
  //TODO
  // HOW TO ADD ITEMS : <area>.addItems(Item("Name", "Description")
  pond.addItem(knight_figurine)
  library.addItem(old_book)
  library.addItem(diary)
  library.addItem(runic_page)
  hidden_valley.addItem(workshop_key)
  hidden_valley.addItem(drawing)
  //TODO
  // HOW TO ADD PROJECTS : <area>.addprojects(WorkshopProject("Name", Turn count to build, "Description", materials)
  workshop.addprojects(WorkshopProject("lantern knight", 3, "A small figure of a knight holding a light", Vector(knight_figurine, ember)))
  workshop.addprojects(WorkshopProject("dragon", 10, "A miniature mechanised dragon", Vector(ember)))
  workshop.addprojects(WorkshopProject("frog quoir", 8, "a wooden carving of wooden frogs that makes a pleasant tune", Vector(ember)))
  workshop.addprojects(WorkshopProject("quartz jukebox", 6, "a crystal music box", Vector(ember)))
  /** Add NPCS */
  //TODO: ADD all Npcs to the game
  village.addNPC(NPC("diane", Vector("As you approach the mysterious individual, she smiles in the distance. \n\"Once there was a little knight...\"\nShe looks at you.",
    "\"...so that the dark wasn't scary anymore.\""), runic = "or"))

  village.addNPC(NPC("light", Vector(
  "\"You look like you need a light. Here, take my last ember. Make something good with it.\"",
  "\"Go on, the ember won't wait forever. Neither will dawn.\""),
  reward = Some(ember), runic = "se"))

  village.addNPC(NPC("papyrus", Vector(
    "Papyrus the toad puffs out his chest. \"So you're an inventor huh? You have to build a DRAGON! Everyone loves dragons!\"",
    "\"The bigger it is, the better! People would talk about it for years!\""), runic = "hi"))

  village.addNPC(NPC("vivian", Vector(
    "A young frog in a bright scarf is humming to herself. \"Oh! Do you like music? Everyone here used to sing.\"",
    "\"Wouldn't it be wonderful if someone built a choir of little clockwork frogs? They could sing all night long!\"",
    "\"It might take a few hours to build, but think how happy it would make everyone!\"",
    "\"Music is the best gift. Much better than some old toy, don't you think?\""), runic = "mn"))

  library.addNPC(NPC("archivist", Vector(
    "An ancient frog peers at you over tiny glasses. \"Ah. You can't read any of this, can you?\"",
    "\"These symbols aren't a foreign language. Each one is simply a letter.\"",
    "\"Each letter you understand is another step towards realising the true meaning of why you are here.\"",
    "\"Look at things whose names you already know. Their symbols will teach you the letters.\"",
    "\"A wise inventor once wrote: a dragon takes a hundred nights, a light takes one evening.\"")))
  //TODO
  // HOW TO ADD PROJECTS : <area>.addNPC(NPC("Name", "Dialogue")
  /** The character that the player controls in the game. */
  val player = Player(pond, this)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40


  /** Determines if the adventure is complete, that is, if the player has won. */
  def goodEnding = this.normalEnding && this.player.hasRemembered

  def TimeRanOut = this.turnCount >= this.timeLimit

  def buildWrong = TimeRanOut && !this.player.has("lantern knight")

  def normalEnding = this.player.usedItem && this.player.has("lantern knight")

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.goodEnding || this.TimeRanOut || this.buildWrong ||this.normalEnding || this.player.hasQuit || this.turnCount >= this.timeLimit

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage =
    "Somewhere, a clock is ticking.\n\n" +
    "You don't remember your name, or how you got here. You only have the strange feeling\n" +
    "that you promised to finish something before dawn... but not what, or for whom.\n\n" +
    "(Type help to see what you can do.)"


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether the player has completed their quest. */
  def goodbyeMessage =
    if this.goodEnding then
      "You press your hand to the stone tablet and carve the spell, slowly as if the shape of the letters were coming back to you." + " MAI".map(Runes.toRunes)  +
        " \n The runes glow, and the whole land holds its breath. One by one, the strange symbols you could not read all night turn into words. The note in the mud by the pond becomes clear at last." +
        "[I'm here come back]. \nThe lantern knight lights, the fog pulls back, and the clock stops ticking. You rememner a small hand holding a tin knight. You remember inventing an alphabet at a kitchen table, so that two people could write notes nobody else could read. You remember her name." +
        " \nFar away, a girl looks at the little knight in her room, glowing in the dark, and she is not afraid. 'Grandpa?' Dawn comes, and you re there to see it You win!"
    else if this.normalEnding then {
      "You wind the spring, set the ember and close the little visor. The lantern knight wakes, lifts its light toward the clock face, and a thin golden beam leaves the tower. It crosses the pond, the village, the fog and keeps going, out of the land altogether. "
      + "\n Far away, a girl wakes in a dim room and sees a small knight holding a light. She smiles, and she reaches for it. You feel it happen, even from here."
      + "\n But spell on the stone panel stays unanswered. You know there was something you meant to say, and you cannot find it. The sawn comes anyway, genle and golden, and it carries your light away. It was a good gift. It could have been more. "
    } else  if this.buildWrong then {
      "TYou look down at your work. It is beautifull, magnificent, but nowhere near finished. The gears hang loosse nad the light you needed is nowhere in it"
        + "\nthe fog creeps in from every side, soft and patient. The voices that were whispering all night are quiet now, and somehow that is worse. The sparks above you dim one by one. Somewhere very far away, someone is waiting for a gift that will not arrive." +
        " The last thing your hear is the clock, ticking more slowly. \n\n Game Over."
    } else if TimeRanOut then {
      "The clocktower strikes, and the sound rolls over the land like a wave. Above the trees the sky is turning pale, and you are still far from the top"
      + "\nthe fog creeps in from every side, soft and patient. The voices that were whispering all night are quiet now, and somehow that is worse. The sparks above you dim one by one. Somewhere very far away, someone is waiting for a gift that will not arrive." +
        " The last thing your hear is the clock, ticking more slowly. \n\n Game Over."
    } else
      "\"Quitter!\""


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

