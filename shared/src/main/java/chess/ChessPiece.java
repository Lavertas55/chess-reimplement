package chess;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import chess.piecemoves.MoveCalculator;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (pieceType != PieceType.PAWN) {
            return MoveCalculator.calculateMoves(myPosition, board);
        }

        throw new RuntimeException("pawn not implemented yet");
    }

    @Override
    public String toString() {
        String pieceTypeString = pieceTypeToString.get(pieceType);
        if (pieceColor == ChessGame.TeamColor.BLACK) {
            pieceTypeString = pieceTypeString.toLowerCase();
        }

        return pieceTypeString;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChessPiece that)) {
            return false;
        }
        return this.pieceColor == that.pieceColor && this.pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }

    final private ChessGame.TeamColor pieceColor;
    final private PieceType pieceType;
    final static private HashMap<PieceType, String> pieceTypeToString = new HashMap<>() {
        {
            put(PieceType.KING, "K");
            put(PieceType.QUEEN, "Q");
            put(PieceType.BISHOP, "B");
            put(PieceType.KNIGHT, "N");
            put(PieceType.ROOK, "R");
            put(PieceType.PAWN, "P");
        }
    };
}
