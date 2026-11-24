package o1.adventure

import scala.collection.mutable.Map

/** A `Player` object represents a player character controlled by the real-life user
  * of the program.
  *
  * A player object’s state is mutable: the player’s location and possessions can change,
  * for instance.
  *
  * @param startingArea  the player’s initial location */
class Player(startingArea: Area, game: Adventure):
  private val items = Map[String, Item]()
  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false       // one-way flag
  var hasRemembered = false
  var usedItem = false

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the player’s current location. */
  def location = this.currentLocation

  def use(target: String) =
    if this.location.name == "Clocktower Peak" then {
      usedItem = true
      "This is the right thing to do."
    } else 
      "Items are only usable at the top of the clocktower."



  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player’s current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) =
    val destination = this.location.neighbor(direction)
    val hasBuilt = Vector("dragon", "lantern knight", "frog choir", "quartz jukebox").exists(this.has)
    if direction == "inside" && this.location.name == "Clocktower" && !this.has("lantern knight") then
      "The clocktower door won't budge. It's waiting for you to bring" + "lantern knight".map(Runes.toRunes)
    else if destination.exists(_.name == "Workshop") && !this.has("key") then
      "The workshop is locked. There is a small keyhole on the door.\nPerhaps you can find a way to unlock it?"
    else
      this.currentLocation = destination.getOrElse(this.currentLocation)
      if destination.isDefined then s"You go $direction." else s"You can't go $direction."

  def reminisce(spell: String) =
    if spell.toLowerCase == "mai" then
      hasRemembered = true
      "Your memory starts to clear up"
    else
      "Nothing here stirs a memory"


  /** Signals that the player wants to quit the game. Returns a description of what happened
    * within the game as a result (which is the empty string, in this case). */
  def quit() =
    this.quitCommandGiven = true
    ""

  def has(itemName: String) = this.items.contains(itemName)

  def get(itemName: String): String =
    this.location.removeItem(itemName) match
      case Some(item) =>
        this.items += itemName -> item
        s"You pick up the $itemName."
      case None =>
        s"There is no $itemName here to pick up."

  def drop(itemName: String): String =
    this.items.remove(itemName) match
      case Some(item) =>
        this.location.addItem(item)
        s"You drop the $itemName."
      case None =>
        "You don't have that!"

  def examine(itemName: String): String =
    this.items.get(itemName) match
      case Some(item) => s"You look closely at the " + s"$itemName".map(Runes.toRunes) + "\n" + s"${item.description}"
      case None       => "If you want to examine something, you need to pick it up first."

  def inventory: String =
    if this.items.isEmpty then "You are empty-handed."
    else "You are carrying:\n" + this.items.keys.mkString("\n")
    /** Returns a brief description of the player’s state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

  def talk(target: String): String =
    this.location.population(target) match
      case Some(npc) =>
        val line = npc.speakYourMind
        val reward_item = npc.give_reward()
        reward_item.foreach( item => this.items += item.name -> item )
        line + reward_item.map( item => s"\n You got: ${item.name}" ).getOrElse("")
      case None      => "There is nobody called that here."
  
  def build(projectName: String): String =
    this.location.projects(projectName) match
      case Some(project) => 
        val missing = project.materials.filterNot(material => this.has(material.name))
        if missing.nonEmpty then { 
          val names = missing.map(_.name).mkString(", ")
          s"you are missing $names"
        } else { 
          for material <- project.materials do
            if material.name != "ember" then
              this.items.remove(material.name)
          val creation = Item(project.name, project.description)
          items += project.name -> creation
          if project.name == "lantern knight" then
            game.turnCount = 10
            s"Successfully built ${project.name}" + "\n\n You spent a long time in the workshop, the first lights of dawn breaks the sky. You should bring this to the top of the clocktower as soon as possible"
          else
            game.turnCount = 5
            s"Successfully built ${project.name}" + "\n\n You spent a way too long time in the workshop, it's almost dawn. You should bring this to the top of the clocktower as soon as possible but you don't think you'll make it."

        }
      case None => "The tools to build anything should be in the workshop"

  def help: String = "Commands : \n go <direction> \n get <item> \n drop <item> \n examine <item> \n inventory \n talk <target> (continue using talk for more dialogue with the NPC) \n build <project> \n use <item> \n quit" + "\n reminisce".map(Runes.toRunes) + "<person>".map(Runes.toRunes)


end Player

