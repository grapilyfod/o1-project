package o1.adventure

class NPC(val name : String, val dialogue_lines: Vector[String], val reward: Option[Item] = None):

  private var line_num = 0

  private var rewarded = false

  def speakYourMind: String =
    val line = this.dialogue_lines(this.line_num)
    val current_pos =
      if this.dialogue_lines.size > 1 then
        s"\n(${this.line_num + 1}/${this.dialogue_lines.size})"
      else
        ""
    if this.line_num < this.dialogue_lines.size - 1 then
      this.line_num += 1
    line + current_pos

  def give_reward(): Option[Item] =
    if this.rewarded then
      None
    else
      this.rewarded = true
      this.reward

  override def toString = this.name
end NPC

