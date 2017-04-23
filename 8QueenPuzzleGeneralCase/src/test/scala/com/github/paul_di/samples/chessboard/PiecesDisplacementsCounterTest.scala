package com.github.paul_di.samples.chessboard

import org.scalatest.FunSuite

class PiecesDisplacementsCounterTest extends FunSuite {
  test("8QueensAt8x8Board") {
    assert(PiecesDisplacementsCounter.count(8,8, List.fill(8)(Queen)) == BigDecimal(92))
  }

  test("ImpossibleDisplacement") {
    //can't put 9 queens to 8x8 board
    assert(PiecesDisplacementsCounter.count(4,4, List.fill(9)(Queen)) == BigDecimal(0))
  }

  test("AllPiecesTest") {
    val pieces = Array("queen", "castle", "king", "king", "knight", "bishop").map { Piece.fromString }
    assert(PiecesDisplacementsCounter.count(6,9, pieces) == BigDecimal(20136752))
  }
}
