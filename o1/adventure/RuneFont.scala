package o1.adventure
import java.awt.Font
import java.awt.GraphicsEnvironment

object RuneFont {
  val stream = getClass.getResourceAsStream("/o1/adventure/Runes_font.ttf")
  val font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(24f)
  GraphicsEnvironment.getLocalGraphicsEnvironment.registerFont(font)

  val familyName = font.getFamily

}
