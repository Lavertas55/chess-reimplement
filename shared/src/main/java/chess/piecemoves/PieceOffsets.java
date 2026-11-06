package chess.piecemoves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PieceOffsets {
    KNIGHT(new int[][] {
        {-1, 2}, {1, 2},
        {-2, 1}, {2, 1},
        {-2, -1}, {2, -1},
        {-1, -2}, {1, -2}
    }),
    BISHOP(new int[][] {
        {-1, -1}, {-1, 1},
        {1, -1}, {1, 1}
    }),
    ROOK(new int[][] {
        {0, -1},
        {-1, 0}, {1, 0},
        {0, 1}
    }),
    QUEEN(BISHOP, ROOK),
    KING(QUEEN.getOffsets());

    private final int[][] offsets;

    private PieceOffsets(int[][] offsets) {
        this.offsets = offsets;
    }

    private PieceOffsets(PieceOffsets... sources) {
        List<int[]> offsetList = new ArrayList<>();
        for (PieceOffsets src : sources) {
            offsetList.addAll(Arrays.asList(src.offsets));
        }

        this.offsets = offsetList.toArray(int[][]::new);
    }

    public int[][] getOffsets() {
        return offsets;
    }
}
