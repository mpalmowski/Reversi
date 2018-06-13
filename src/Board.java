import java.util.LinkedList;

public class Board {
    private int size;
    private Pawn pawns[][];

    Board(int size) {
        this.size = size;
        pawns = new Pawn[size][size];
    }

    void copy(Board other) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                pawns[i][j] = other.get(i, j);
            }
        }
    }

    Pawn get(int verse, int column) {
        return pawns[verse][column];
    }

    void addPawn(int verse, int column, Pawn pawn) {
        pawns[verse][column] = pawn;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0)
                    continue;

                searchForSequence(verse, column, i, j);
            }
        }
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
        LinkedList<Move> possibleMoves = findPossibleMoves();

        int v, c;
        LinkedList<Move> opponentMoves;
        int max;
        for (Move move : possibleMoves) {
            v = move.getVerse();
            c = move.getColumn();

            pawns[v][c] = new Pawn(color);

            opponentMoves = findPossibleMoves();

            max = Integer.MIN_VALUE;
            for(Move opponentmove : opponentMoves){
                opponentmove.setPoints(valueMove(opponentmove));
                if(opponentmove.getPoints() > max)
                    max = opponentmove.getPoints();
            }

            move.setPoints(max);
            pawns[v][c] = null;
        }

        int min = Integer.MAX_VALUE;
        Move bestMove = new Move(-1, -1);
        for(Move move : possibleMoves){
            if(move.getPoints() < min) {
                min = move.getPoints();
                bestMove = move;
            }
        }

        return bestMove;
    }

    private LinkedList<Move> findPossibleMoves() {
        LinkedList<Move> possibleMoves = new LinkedList<>();

        for (int v = 0; v < size; ++v) {
            for (int c = 0; c < size; ++c) {
                if (pawns[v][c] == null) {
                    possibleMoves.add(new Move(v, c));
                }
            }
        }

        return possibleMoves;
    }

    private int valueMove(Move move){
        int points = 0;

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0)
                    continue;

                points += searchForSequence(move.getVerse(), move.getColumn(), i, j, false);
            }
        }

        return points;
    }
}
