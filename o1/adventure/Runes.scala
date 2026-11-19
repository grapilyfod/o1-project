package o1.adventure

object Runes {
  def toRunes(c: Char):Char =
    if c.toUpper >= 'A' && c.toUpper <= 'Z' then
      val distanceToA = c.toUpper - 'A'
      (0xE000 + distanceToA).toChar
    else
      c
      
  def isRune(c: Char): Boolean = c.toUpper > 0xE000 && c.toUpper < 0xE019
}
