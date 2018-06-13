public class Controller implements Runnable{
    private View view;
    private Model model;
    private static final int width = 300, height = 300;

    Controller(){

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
        long timer = System.currentTimeMillis();
        int framesPerSecond = 0;

        while (true) {
            view.render(model);
            model.tick();
            framesPerSecond++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
                System.out.print("FPS: " + framesPerSecond + System.lineSeparator());
                framesPerSecond = 0;
            }
        }
    }


    public void handleMousePressed(int x, int y) {
        model.FieldChosen(x, y);
    }
}
