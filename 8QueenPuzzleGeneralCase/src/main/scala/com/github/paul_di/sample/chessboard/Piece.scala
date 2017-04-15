package com.github.paul_di.sample.chessboard

sealed abstract class Piece extends Ordered[Piece] {
  /** @return priority of  based on count of attacked squares. lower processed first */
  protected def priority: Int

  /** @return true if square is underAttack by piece (self). if(square == self) behavior is undefined */
  def isSquareUnderAttack(self: Square, square: Square): Boolean

  override def toString = this.getClass.getName.toLowerCase

  override def compare(that: Piece): Int = this.priority.compare(that.priority)
}

object Piece {
  def fromString(s: String): Piece = {
    s.toLowerCase match {
      case "queen" => Queen
      case "castle" => Castle
      case "bishop" => Bishop
      case "king" => King
      case "knight" => Knight
      case otherPiece => throw new Exception(s"Unsupported piece type: $otherPiece")
    }
  }
}

case object Queen extends Piece {
  override protected def priority = 10

  override def isSquareUnderAttack(self: Square, square: Square): Boolean = {
    self.x == square.x ||
      self.y == square.y ||
      (self.x - self.y) == (square.x - square.y) ||
      (self.x + self.y) == (square.x + square.y)
  }
}

case object King extends Piece {
  override protected def priority = 40

  override def isSquareUnderAttack(self: Square, square: Square): Boolean = {
    Math.abs(self.x - square.x) < 2 && Math.abs(self.y - square.y) < 2
  }
}

case object Castle extends Piece {
  override protected def priority = 20

  override def isSquareUnderAttack(self: Square, square: Square): Boolean = {
    self.x == square.x || self.y == square.y
  }
}

case object Knight extends Piece {
  override protected def priority = 50

  override def isSquareUnderAttack(self: Square, square: Square): Boolean = {
    val xDistance = Math.abs(self.x - square.x)
    val yDistance = Math.abs(self.y - square.y)
    (xDistance == 1 && yDistance == 2) || (xDistance == 2 && yDistance == 1)
  }
}

case object Bishop extends Piece {
  override protected def priority = 30

  override def isSquareUnderAttack(self: Square, square: Square): Boolean = {
    (self.x - self.y) == (square.x - square.y) || (self.x + self.y) == (square.x + square.y)
  }
}
