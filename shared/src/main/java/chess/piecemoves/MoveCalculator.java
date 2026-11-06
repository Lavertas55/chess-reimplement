package chess.piecemoves;

import java.util.ArrayList;
import java.util.Collection;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

public class MoveCalculator {
    public static Collection<ChessMove> calculateMoves(ChessPosition position, ChessBoard board) {   
        Collection<ChessMove> moves = new ArrayList<>();
        
        ChessPiece piece = board.getPiece(position);
        ChessGame.TeamColor pieceColor = piece.getTeamColor();
        ChessPiece.PieceType type = piece.getPieceType();

        int[][] pieceOffsets = getPieceOffset(type).getOffsets();
        
        for (int[] offset : pieceOffsets) {
            boolean canLoop = canLoop(type);            
            int newRow = position.getRow();
            int newCol = position.getColumn();
            do {
                newRow += offset[0];
                newCol += offset[1];

                ChessPosition newPosition = new ChessPosition(newRow, newCol);

                if (board.inBounds(newPosition.getRow(), newPosition.getColumn())) {
                    ChessPiece oppPiece = board.getPiece(newPosition);
                    ChessGame.TeamColor oppColor = oppPiece != null ? oppPiece.getTeamColor() : null;
                    if (oppColor != pieceColor) {
                        ChessMove newMove = new ChessMove(position, newPosition, null);
                        moves.add(newMove);
                    } 
                    
                    if (oppColor != null) {
                        canLoop = false;
                    }
                } else {
                    canLoop = false;
                }


            } while(canLoop);
        }

        return moves;
    }

    private static PieceOffsets getPieceOffset(ChessPiece.PieceType type) {
        return switch (type) {
            case KNIGHT -> PieceOffsets.KNIGHT;
            case BISHOP -> PieceOffsets.BISHOP;
            case ROOK   -> PieceOffsets.ROOK;
            case QUEEN  -> PieceOffsets.QUEEN;
            case KING   -> PieceOffsets.KING;
            case PAWN   -> throw new RuntimeException("not implemented");
        };
    }

    private static boolean canLoop(ChessPiece.PieceType type) {
        return switch (type) {
            case KNIGHT, KING, PAWN -> false;
            default -> true;
        };
    }
}
