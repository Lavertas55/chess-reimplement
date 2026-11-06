package chess.piecemoves;

import java.util.ArrayList;
import java.util.Collection;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPiece.PieceType;
import chess.ChessPosition;

public class PawnMoveCalculator {
    public static Collection<ChessMove> calculateMoves(ChessPosition position, ChessBoard board) {
        Collection<ChessMove> moves = new ArrayList<>();
        
        ChessGame.TeamColor color = board.getPiece(position).getTeamColor();

        int row = position.getRow();
        int col = position.getColumn();

        int startRow = color == ChessGame.TeamColor.WHITE ? 2 : 7;
        int promoteRow = color == ChessGame.TeamColor.WHITE ? 8 : 1;
        int offset = color == ChessGame.TeamColor.WHITE ? 1 : -1;

        int newRow = row + 2 * offset;
        if (
            row == startRow &&
            board.getPiece(new ChessPosition(newRow, col)) == null &&
            board.getPiece(new ChessPosition(newRow - offset, col)) == null
        ) {

            moves.add(new ChessMove(
                position,
                new ChessPosition(newRow, col),
                null)
            );
        }
        
        newRow = row + offset;
        for (int i = -1; i <= 1; i++) {
            int newCol = col + i;

            if (board.inBounds(newRow, newCol)) {
                ChessPiece piece = board.getPiece(new ChessPosition(newRow, newCol));

                if ((piece == null && i == 0) || (piece != null && piece.getTeamColor() != color && i != 0)) {
                    ChessPosition newPosition = new ChessPosition(newRow, newCol);
                    if (promoteRow == newRow) {
                        for (PieceType type : PieceType.values()) {
                            if (type != PieceType.KING && type != PieceType.PAWN) {
                                moves.add(new ChessMove(
                                    position,
                                    newPosition,
                                    type
                                ));
                            }
                        }
                    } else {
                        moves.add(new ChessMove(position, newPosition, null));
                    }
                }
            }
        }

        return moves;
    }
}
