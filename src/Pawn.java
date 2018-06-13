import java.awt.*;

class Pawn {

    private int x = 0, y = 0;
    private int size = 0;
    private Model model = null;
    private PawnColor color;

    public Pawn(int x, int y, int size, Model model, PawnColor color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.model = model;
        this.color = color;
    }

    public Pawn(PawnColor color){
        this.color = color;
    }

    void render(Graphics graphics){
        if(model == null)
            return;

        switch(color){
            case black:
                graphics.drawImage(model.getBlackPawn().getBufferedImage(), x, y, size, size, null);
                break;
            case white:
                graphics.drawImage(model.getWhitePawn().getBufferedImage(), x, y, size, size, null);
                break;
        }
    }

    public PawnColor getColor() {
        return color;
    }

    public void setColor(PawnColor color) {
        this.color = color;
    }
}
