public class Move {
    private int verse, column;
    private int points = 0;

    Move(int verse, int column) {
        this.verse = verse;
        this.column = column;
    }

    int getVerse() {
        return verse;
    }

    int getColumn() {
        return column;
    }

    int getPoints() {
        return points;
    }

    void setPoints(int points) {
        this.points = points;
    }
}
