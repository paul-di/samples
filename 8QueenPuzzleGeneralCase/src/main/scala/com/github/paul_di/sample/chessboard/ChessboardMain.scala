package com.github.paul_di.sample.chessboard

object ChessboardMain {
  def main(args: Array[String]) {
    val (xSize, ySize) = (args(0).toInt, args(1).toInt)

    val pieces = args.drop(2).map {
      Piece.fromString
    }

    val timestamp = System.currentTimeMillis()
    val count = PiecesDisplacementsCounter.count(xSize, ySize, pieces)
    println(s"count of displacements: $count")
    println(s"computation time: ${System.currentTimeMillis - timestamp} ms")
  }
}

object PiecesDisplacementsCounter {
  def count(boardXSize: Int, boardYSize: Int, pieces: Traversable[Piece]): BigDecimal = {
    loopOnPiecesList(pieces.toList.sorted, Chessboard(boardXSize, boardYSize), null, Square(-1, -1))
  }

  private def loopOnPiecesList(pieces: List[Piece], board: Chessboard, previousPiece: Piece, previousPiecePos: Square): BigDecimal = {
    if(pieces.isEmpty) {
      return BigDecimal(1)
    }

    val curPiece = pieces.head
    board.freeSquares.map { curPos =>
      //second condition prevents position duplication. squares of board have ordering. pieces list also ordered.
      if (!attackingOtherPieces(board, curPiece, curPos) && (curPiece != previousPiece || curPos > previousPiecePos)) {
        loopOnPiecesList(pieces.tail, board.putPiece(curPiece, curPos), curPiece, curPos)
      } else
        BigDecimal(0)
    }.sum
  }

  private def attackingOtherPieces(board: Chessboard, curPiece: Piece, curPos: Square): Boolean = {
    board.pieces.map(_._2).exists(otherPiecePos => curPiece.isSquareUnderAttack(curPos, otherPiecePos))
  }
}