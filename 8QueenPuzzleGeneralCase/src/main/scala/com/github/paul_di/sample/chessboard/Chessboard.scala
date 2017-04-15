package com.github.paul_di.sample.chessboard

abstract class Chessboard {
  /** @return List of pieces with positions on the board */
  def pieces: List[(Piece, Square)]

  /** @return Squares that are not occupied by any piece and not under attack from any piece */
  def freeSquares: Array[Square]

  /** @return New chess board with piece put on it. piece collisions is not checked! */
  def putPiece(piece: Piece, position: Square): Chessboard
}

object Chessboard {
  def apply(xSize: Int, ySize: Int): Chessboard = {
    new EmptyBoard(xSize, ySize)
  }
}

private class EmptyBoard(xSize: Int, ySize: Int) extends Chessboard {
  override def pieces: List[(Piece, Square)] = Nil

  override def putPiece(piece: Piece, position: Square): Chessboard = {
    new RecursiveBoard(this, piece, position)
  }

  override val freeSquares = {
    {
      for (x <- 0 until xSize; y <- 0 until ySize)
        yield Square(x, y)
    }.toList.sorted.toArray
  }
}

private class RecursiveBoard(board: Chessboard, piece: Piece, position: Square) extends Chessboard {
  override lazy val pieces: List[(Piece, Square)] = (piece, position) :: board.pieces

  override def putPiece(piece: Piece, position: Square): Chessboard = {
    new RecursiveBoard(this, piece, position)
  }

  override lazy val freeSquares: Array[Square] = {
    board.freeSquares.filter(square => !(piece.isSquareUnderAttack(position, square) || position == square))
  }
}

case class Square(x: Int, y: Int) extends Ordered[Square] {
  override def compare(b: Square): Int = {
    val xCompare = this.x.compare(b.x)
    if(xCompare != 0) xCompare else this.y.compare(b.y)
  }
}