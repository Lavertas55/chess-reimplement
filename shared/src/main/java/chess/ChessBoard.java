package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow();
        int col = position.getColumn();

        board[row - 1][col - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        return board[row - 1][col - 1];
    }

    public boolean inBounds(int row, int col) {
        return board.length >= row && row > 0 && board[0].length >= col && col > 0;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int i = 0; i < BOARD_ROWS; i++) {
            ChessGame.TeamColor teamColor = (i == 0 || i == 1) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
            int pawnRow = (teamColor == ChessGame.TeamColor.WHITE) ? 1 : 6;

            for (int j = 0; j < BOARD_COLS; j++) {
                if (i == pawnRow) {
                    board[i][j] = new ChessPiece(teamColor, ChessPiece.PieceType.PAWN);
                } else if (i == 0 || i == 7) {
                    ChessPiece.PieceType pieceType;
                    switch (j) {
                        case 0, 7 -> pieceType = ChessPiece.PieceType.ROOK;
                        case 1, 6 -> pieceType = ChessPiece.PieceType.KNIGHT;
                        case 2, 5 -> pieceType = ChessPiece.PieceType.BISHOP;
                        case 3 -> pieceType = ChessPiece.PieceType.QUEEN;
                        case 4 -> pieceType = ChessPiece.PieceType.KING;
                        default -> throw new IllegalStateException("Unexpected value: " + j);
                    }
                    board[i][j] = new ChessPiece(teamColor, pieceType);
                } else {
                    board[i][j] = null;
                }
            }
        }
    }

    @Override 
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_ROWS; i++) {
            sb.append("|");
            for (int j = 0; j < BOARD_COLS; j++) {
                ChessPiece piece = board[i][j];
                if (piece == null) {
                    sb.append(" ");
                } else {
                    sb.append(piece.toString());
                }
                sb.append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChessBoard that)) {
            return false;
        }
        return Arrays.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    final private static int BOARD_ROWS = 8;
    final private static int BOARD_COLS = 8;

    final private ChessPiece[][] board = new ChessPiece[BOARD_ROWS][BOARD_COLS];
}
