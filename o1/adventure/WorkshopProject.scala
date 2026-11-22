package o1.adventure

class WorkshopProject(val name: String, val time: Int, val description : String, val materials: Vector[Item]):
  
  override def toString = this.name

