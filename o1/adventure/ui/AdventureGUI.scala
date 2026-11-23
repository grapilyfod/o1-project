package o1.adventure.ui

import scala.swing.*
import scala.swing.event.*
import javax.swing.UIManager
import o1.adventure.{Adventure, Runes, RuneFont}

import java.awt.{Font, GraphicsEnvironment}
import javax.swing.text.{SimpleAttributeSet, StyleConstants}
import java.awt.{Dimension, Insets, Point}
import scala.language.{adhocExtensions, postfixOps} // enable extension of Swing classes



////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it’s not necessary
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////

/** Starts a GUI version of the Adventure game application in a separate window.
  * (Cf. `startAdventureTextUI`.) The GUI reads its input from a text field and displays
  * information about the game world in uneditable text areas.
  *
  * **NOTE TO STUDENTS: In this course, you don’t need to understand how this object works
  * on the inside. It’s enough to know that you can use this file to start the program.** */
@main def startAdventureGUI() =
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)


  def top = new MainFrame:

    // Access to the application’s internal logic:

    val game = Adventure()
    val player = game.player

    // Components:

    val locationInfo = new TextPane:
      editable = false
      preferredSize = Dimension(600, 150)
    val turnOutput = new TextPane:
      editable = false
      preferredSize = Dimension(600,180)
    val input = new TextField(40):
      minimumSize = preferredSize
    this.listenTo(input.keys)
    val turnCounter = Label()

    // Events:

    this.reactions += {
      case keyEvent: KeyPressed =>
        if keyEvent.source == this.input && keyEvent.key == Key.Enter && !this.game.isOver then
          val command = this.input.text.trim
          if command.nonEmpty then
            this.input.text = ""
            this.playTurn(command)
    }

    // Layout:

    this.contents = new GridBagPanel:
      import scala.swing.GridBagPanel.Anchor.*
      import scala.swing.GridBagPanel.Fill
      layout += Label("Location:") -> Constraints(0, 0, 1, 1, 0, 1, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Command:")  -> Constraints(0, 1, 1, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += Label("Events:")   -> Constraints(0, 2, 1, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += turnCounter        -> Constraints(0, 3, 2, 1, 0, 0, NorthWest.id, Fill.None.id, Insets(8, 5, 5, 5), 0, 0)
      layout += locationInfo       -> Constraints(1, 0, 1, 1, 1, 1, NorthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)
      layout += input              -> Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, Insets(5, 5, 5, 5), 0, 0)
      layout += turnOutput         -> Constraints(1, 2, 1, 1, 1, 1, SouthWest.id, Fill.Both.id, Insets(5, 5, 5, 5), 0, 0)

    // Menu:
    this.menuBar = new MenuBar:
      contents += new Menu("Program"):
        val quitAction = Action("Quit")( dispose() )
        contents += MenuItem(quitAction)

    // Set up the GUI’s initial state:
    this.title = game.title
    this.updateInfo(this.game.welcomeMessage)
    this.location = Point(50, 50)
    this.minimumSize = Dimension(200, 200)
    this.pack()
    this.input.requestFocusInWindow()


    def playTurn(command: String) =
      val turnReport = this.game.playTurn(command)
      if this.player.hasQuit then
        this.dispose()
      else
        this.updateInfo(turnReport)
        this.input.enabled = !this.game.isOver


    def styleRunes(pane :TextPane) = {
      
      val doc = pane.peer.getStyledDocument
      val text = doc.getText(0, doc.getLength)
      //Sets attributes for normal text
      val NormalTextAttributes = new SimpleAttributeSet()
      StyleConstants.setFontSize(NormalTextAttributes, 14)
      //Sets attriutes for Rune text
      val runeAttrbs = new SimpleAttributeSet()
      StyleConstants.setFontSize(runeAttrbs, 16)
      StyleConstants.setFontFamily(runeAttrbs, RuneFont.familyName)
      doc.setCharacterAttributes(0, text.length, NormalTextAttributes, true)
      
      for index <- text.indices do 
        if Runes.isRune(text(index)) then doc.setCharacterAttributes(index, 1, runeAttrbs, false)
    }
    
    
    def updateInfo(info: String) =
      if !this.game.isOver then {
        this.turnOutput.text = info
        styleRunes(this.turnOutput)
      } else {
        this.turnOutput.text = info + "\n\n" + this.game.goodbyeMessage
        styleRunes(this.turnOutput)
      }
      this.locationInfo.text = this.player.location.fullDescription
      styleRunes(this.locationInfo)
      this.turnCounter.text = "Turns played: " + this.game.turnCount

  end top

  top.open()

  // Enable this code to work even under the -language:strictEquality compiler option:
  given CanEqual[Component, Component] = CanEqual.derived
  given CanEqual[Key.Value, Key.Value] = CanEqual.derived

end startAdventureGUI