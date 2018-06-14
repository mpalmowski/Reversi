import java.util.LinkedList;

public class Board {
    private int size;
    private Pawn pawns[][];
    private int nrOfPawns = 0;
    private static final int opponentMovesPredicted = 2;

    Board(int size) {
        this.size = size;
        pawns = new Pawn[size][size];
    }

    private void copy(Board other) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (other.get(i, j) == null)
                    pawns[i][j] = null;
                else
                    pawns[i][j] = new Pawn(other.get(i, j));
            }
        }
    }

    Pawn get(int verse, int column) {
        return pawns[verse][column];
    }

    boolean addPawn(int verse, int column, Pawn pawn) {
        pawns[verse][column] = pawn;
        int points = 0;

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0)
                    continue;

                points += searchForSequence(verse, column, i, j);
            }
        }

        if (nrOfPawns >= 4 && points == 0) {
            pawns[verse][column] = null;
            return false;
        }

        nrOfPawns++;
        return true;
    }

    private int searchForSequence(int start_v, int start_c, int delta_v, int delta_c) {
        return searchForSequence(start_v, start_c, delta_v, delta_c, true);
    }

    private int searchForSequence(int start_v, int start_c, int delta_v, int delta_c, boolean update) {
        if (pawns[start_v][start_c] == null)
            return 0;
        PawnColor color = pawns[start_v][start_c].getColor();
        int v = start_v;
        int c = start_c;
        v += delta_v;
        c += delta_c;
        int sequence = 0;
        boolean finished = false;
        while (v >= 0 && c >= 0 && v < size && c < size && pawns[v][c] != null) {
            if (pawns[v][c].getColor() != color) {
                sequence++;
                v += delta_v;
                c += delta_c;
            } else {
                finished = true;
                break;
            }
        }
        if (finished && sequence > 0) {
            if (update)
                changeSequenceColor(start_v, start_c, delta_v, delta_c, sequence);
            return sequence;
        }
        return 0;
    }

    private void changeSequenceColor(int start_v, int start_c, int delta_v, int delta_c, int sequence) {
        PawnColor color = pawns[start_v][start_c].getColor();
        int v = start_v;
        int c = start_c;
        v += delta_v;
        c += delta_c;
        for (int i = 0; i < sequence; ++i) {
            pawns[v][c].setColor(color);
            v += delta_v;
            c += delta_c;
        }
    }

    Move minMax(PawnColor color) {
        Board tempBoard = new Board(size);
        tempBoard.copy(this);
        LinkedList<Move> possibleMoves = findPossibleMoves(color);

        int v, c;
        int max;

        for (Move move : possibleMoves) {
            v = move.getVerse();
            c = move.getColumn();

            tempBoard.pawns[v][c] = new Pawn(color);
            tempBoard.valueMove(move, true);

            max = tempBoard.predictMoves(opponentMovesPredicted * 2 - 1, true, color == PawnColor.white ? PawnColor.black : PawnColor.white);

            move.setPoints(-max);
            move.setPoints(move.getPoints() + valueMove(move, false));

            tempBoard.copy(this);
        }

        max = Integer.MIN_VALUE;
        Move bestMove = new Move(-1, -1);
        for (Move move : possibleMoves) {
            if (move.getPoints() > max) {
                max = move.getPoints();
                bestMove = move;
            }
        }

        return bestMove;
    }

    private LinkedList<Move> findPossibleMoves(PawnColor color) {
        LinkedList<Move> possibleMoves = new LinkedList<>();
        int points;

        for (int v = 0; v < size; ++v) {
            for (int c = 0; c < size; ++c) {
                if (pawns[v][c] == null) {
                    pawns[v][c] = new Pawn(color);
                    points = valueMove(new Move(v, c), false);
                    pawns[v][c] = null;

                    if (points > 0)
                        possibleMoves.add(new Move(v, c));
                }
            }
        }

        return possibleMoves;
    }

    private int valueMove(Move move, boolean update) {
        int points = 0;

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0)
                    continue;

                points += searchForSequence(move.getVerse(), move.getColumn(), i, j, update);
            }
        }

        return points;
    }

    private int predictMoves(int steps, boolean max, PawnColor color) {
        int result;
        int v, c;
        int points;

        if (max)
            result = Integer.MIN_VALUE;
        else
            result = Integer.MAX_VALUE;

        Board tempBoard = new Board(size);
        tempBoard.copy(this);

        LinkedList<Move> possibleMoves = findPossibleMoves(color);
        for (Move move : possibleMoves) {
            v = move.getVerse();
            c = move.getColumn();

            tempBoard.pawns[v][c] = new Pawn(color);
            points = tempBoard.valueMove(move, true);

            if (steps == 0)
                move.setPoints(points);
            else
                move.setPoints(tempBoard.predictMoves(steps--, !max, color == PawnColor.white ? PawnColor.black : PawnColor.white));

            if (max && move.getPoints() > result)
                result = move.getPoints();
            else if (!max && move.getPoints() < result)
                result = move.getPoints();

            tempBoard.copy(this);
        }

        return result;
    }

    public boolean movePossible(PawnColor color) {
        return !findPossibleMoves(color).isEmpty();
    }


}
