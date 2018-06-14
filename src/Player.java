public class Player {
    private PawnColor color;
    private int score = 0;

    public Player(PawnColor color) {
        this.color = color;
    }

    public PawnColor getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
