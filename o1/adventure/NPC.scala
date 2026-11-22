package o1.adventure

class NPC(val name : String, val dialogue_lines: Vector[String], val reward: Option[Item] = None):

  private var line_num = 0

  private var rewarded = false

  def speakYourMind: String =
    val line = this.dialogue_lines(this.line_num)
    if this.line_num < this.dialogue_lines.size - 1 then
      this.line_num += 1
    line

  def give_reward(): Option[Item] =
    if this.rewarded then
      None
    else
      this.rewarded = true
      this.reward

  override def toString = this.name
end NPC

