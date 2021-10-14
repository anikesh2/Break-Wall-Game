import javax.swing.JFrame;

public class Window {
    JFrame frame = new JFrame();
    Gameplay game = new Gameplay();

    public void showScreen() {
        frame.setBounds(10, 10, 917, 600);
        frame.setVisible(true);
        frame.setTitle("BreakOut Ball Game ");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void startGame() {
        frame.add(game);
    }
}