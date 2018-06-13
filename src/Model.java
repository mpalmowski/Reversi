import java.awt.*;

class Model {
    private static final int boardSize = 8;

    private static final String BG_PATH = "Board.png";
    private static final String PAWN_BLACK = "Black.png";
    private static final String PAWN_WHITE = "White.png";
    private Background background;
    private Controller controller;
    private int width, height;
    private int pawn_size;
    private Image whitePawn, blackPawn;
    private PawnColor turn = PawnColor.white;
    private static final PawnColor computerColor = PawnColor.black;
    private Board board;

    Model(Controller controller) {
        this.controller = controller;
        this.width = controller.getWidth();
        this.height = controller.getHeight();
        this.board = new Board(boardSize);
        pawn_size = Math.min(width, height) / boardSize;
        setBackground();
        whitePawn = new Image(PAWN_WHITE);
        blackPawn = new Image(PAWN_BLACK);
        addPawn(3, 3, PawnColor.white);
        addPawn(3, 4, PawnColor.black);
        addPawn(4, 3, PawnColor.black);
        addPawn(4, 4, PawnColor.white);
    }

    private void setBackground() {
        Image backgroundImage = new Image(BG_PATH);
        this.background = new Background(backgroundImage, width, height);
    }

    void render(Graphics graphics) {
        background.render(graphics);

        for (int v = 0; v < boardSize; ++v) {
            for (int c = 0; c < boardSize; ++c) {
                if (board.get(v, c) != null)
                    board.get(v, c).render(graphics);
            }
        }
    }

    public Image getWhitePawn() {
        return whitePawn;
    }

    public Image getBlackPawn() {
        return blackPawn;
    }

    void tick(){
        if(turn == computerColor){
            computersMove();
            switchTurn();
        }
    }

    void FieldChosen(int x, int y) {
        if(turn == computerColor)
            return;
        int verse = getVerse(y);
        int column = getColumn(x);
        if (board.get(verse,column) == null) {
            addPawn(verse, column, turn);
            switchTurn();
        }
    }

    private void addPawn(int verse, int column, PawnColor color) {
        Pawn pawn =  new Pawn(column * width / boardSize, verse * height / boardSize, pawn_size, this, color);
        board.addPawn(verse, column, pawn);
    }

    private int getColumn(int x) {
        return x / (width / boardSize);
    }

    private int getVerse(int y) {
        return y / (height / boardSize);
    }

    private void switchTurn() {
        if (turn == PawnColor.white)
            turn = PawnColor.black;
        else
            turn = PawnColor.white;
    }

    private void computersMove(){
        Board tempBoard = new Board(boardSize);
        tempBoard.copy(board);
        Move bestMove = tempBoard.minMax(turn);
        if(bestMove.getVerse() == -1)
            return;
        System.out.format("Move Chosen: %s, %s\n", bestMove.getVerse(), bestMove.getColumn());
        addPawn(bestMove.getVerse(), bestMove.getColumn(), turn);
    }
}
