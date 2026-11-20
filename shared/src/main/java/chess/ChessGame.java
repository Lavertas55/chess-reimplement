package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame implements Cloneable {

    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    private Collection<ChessMove> candidateMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        return piece.pieceMoves(board, startPosition);
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        
        Collection<ChessMove> possibleMoves = candidateMoves(startPosition);
        Collection<ChessMove> result = new ArrayList<>();

        TeamColor currentColor = board.getPiece(startPosition).getTeamColor();

        for (ChessMove move : possibleMoves) {
            ChessGame testGame = clone();
            testGame.executeMove(move);

            if (!testGame.isInCheck(currentColor)) {
                result.add(move);        
            }
        }

        return result;
    }

    private void executeMove(ChessMove move) {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();

        ChessPiece piece;
        ChessPiece.PieceType promotionType = move.getPromotionPiece();
        if (promotionType != null) {
            piece = new ChessPiece(teamTurn, promotionType);
        } else {
            piece = board.getPiece(startPosition);
        }

        board.addPiece(startPosition, null);
        board.addPiece(endPosition, piece);
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece startPiece = board.getPiece(startPosition);

        if (startPiece == null) {
            throw new InvalidMoveException(
                String.format("There is no piece at %s", startPosition)
            );
        }

        if (startPiece.getTeamColor() != teamTurn) {
            throw new InvalidMoveException(
                String.format("It is %s's turn", teamTurn)
            );
        }

        if (!validMoves(startPosition).contains(move)) {
            throw new InvalidMoveException(
                String.format("%s to %s is not a valid move", startPosition, endPosition)
            );
        }

        executeMove(move);
        teamTurn = teamTurn == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = null;
        Collection<ChessMove> enemyMoves = new ArrayList<>();

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPiece currentPiece = board.getPiece(new ChessPosition(row, col));
                ChessPosition currentPosition = new ChessPosition(row, col);

                if (currentPiece == null) continue;

                if (currentPiece.getTeamColor().equals(teamColor) && currentPiece.getPieceType().equals(ChessPiece.PieceType.KING)) {
                    kingPosition = new ChessPosition(row, col);
                } else if (!currentPiece.getTeamColor().equals(teamColor)) {
                    enemyMoves.addAll(currentPiece.pieceMoves(board, currentPosition));
                }
            }
        }

        for (ChessMove move : enemyMoves) {
            if (move.getEndPosition().equals(kingPosition)) return true;
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    protected ChessGame clone() {
        try {
            ChessGame clone = (ChessGame) super.clone();
            clone.board = board.clone();
            
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override 
    public boolean equals(Object o) {
        if (!(o instanceof ChessGame that)) {
            return false;
        }

        return board.equals(that.board) && teamTurn == that.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn);
    }
}
