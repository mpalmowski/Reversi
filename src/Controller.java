public class Controller implements Runnable {
    private View view;
    private Model model;
    private static final int width = 300, height = 300;
    private boolean running = true;
    private long timer;

    public static void main(String[] args) {
        new Controller();
    }

    Controller() {

        view = new View(width, height);
        view.addMouseListener(new MouseListener(this));
        model = new Model(this);
        start();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private synchronized void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        timer = System.currentTimeMillis();

        while (running) {
            view.render(model);

            if (System.currentTimeMillis() - timer > 300) {
                model.tick();
                timer = System.currentTimeMillis();
            }
        }
        int userScore = model.getUserScore(), compScore = model.getComputerScore();
        String msg = userScore > compScore ? "You win!" : "You lose!";
        view.showDialog(msg);
        System.exit(0);
    }

    public void handleMousePressed(int x, int y) {
        model.FieldChosen(x, y);
        timer = System.currentTimeMillis();
    }

    public void stop() {
        running = false;
    }
}
