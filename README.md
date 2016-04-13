# trenchrun
Trench Run for CSC 180 Spring 2016

Download and compile to play against the AI (you can run the "run game.bat" which is located under the src folder).

THE GAME

"Trench Run" is a chess-like game in which each player takes turns moving one
of his/her pieces, and tries to capture the opponent's Death Star.  It is
played on an 7x7 board, and each side starts with a set of pieces consisting
of 1 Death Star, 2 walls, 4 tie fighters, and 4 x-wings.  The different pieces
have rules about how they can move, which is described later.

Players alternate moves, moving one of their own pieces each turn.
When it is your turn, you MUST make a move -- you cannot pass.
If it is your turn and you have no legal moves, you lose.

A player wins either when he/she captures the opponents' Death Star,
or if it is his/her opponent's turn but the opponent has no legal moves.

The initial position is:

     - T T - T T -  (COMPUTER)
     - - ~ * ~ - -
     X X - - - X X
     - - - - - - -
     x x - - - x x
     - - + @ + - -
     - t t - t t -  (HUMAN)

   T,X,~,* are the computer's tie fighters, x-wings, walls, and death star
      (moving DOWN the board).

   t,x,+,@ are the human's tie fighters, x-wings, walls, and death star 
      (moving UP the board).

The pieces move as follows:

X-WING
- moves roughly the same as the "bishop" in regular chess. That is, diagonally in one direction any number of squares.
- may move either onto an empty square, or onto a square occupied by another piece (thus capturing it).
- CANNOT jump over other pieces.  Once it bumps into a piece or captures a piece, that is as far as it can go.
- an x-wing can move or capture in the forward direction. It can only move in the backwards direction if that move is a capture.

TIE-FIGHTER
- moves roughly the same as a "rook" in regular chess. That is, horizontally or vertically in one direction any number of squares.
- may move either onto an empty square, or onto a square occupied by another piece (thus capturing it).
- CANNOT jump over other pieces.  Once it bumps into a piece or captures a piece, that is as far as it can go.
- a tie-fighter can move or capture in the forward or sideways direction. It can only move in the backwards direction if that move is a capture.
- players are not allowed to make a sideways tie-fighter move on two successive moves.  That is, if you move one of your tie-fighters sideways, you are not allowed to move ANY of your tie-fighters sideways on the next move.

DEATH STAR
- cannot move.
- if it is captured, you lose!
- can only be captured by a piece moving backwards.

WALLS
- cannot move.
- cannot be captured.
- since the death star can only be captured by a piece moving backwards, capturing the death star requires maneuvering a piece through the "trench" against the back row, behind the walls.
    

OTHER DETAILS

- moving is compulsory.  That is, a player cannot "pass".
- you are never allowed to capture your own piece.  That is an illegal move.
- however, it is not illegal to leave your death star in danger.  If the opponent doesn't see that he/she can capture the death star, you play on!
- unlike chess, there is no such thing as "check" or "checkmate".  Winning is by actually capturing the death star (or the opponent having no move).
- unlike chess, there are no pawns or knights or queens.
- unlike chess, inability to move isn't a stalemate draw - it is a LOSS.
- unlike checkers, capturing isn't compulsary.
- unlike Go (Weiqi), players don't place pieces on the board. The pieces are on the board at the beginning, and are moved or captured.
