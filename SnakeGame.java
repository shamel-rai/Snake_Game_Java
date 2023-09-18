import javax.swing.*;

// using this dont need do declare JFrame frame
public class SnakeGame extends JFrame {
    SnakeGame() {
//   if you want to add heading
    super("Nagin");
//    Calling and adding the Board constructor
    add(new Board());
//    to render the items (reolad). Pack will refresh the Frames if there is any changes even if the frame is open
    pack();

    setSize(500,500);
//    sets the frame in the center of the screen automatically
    setLocationRelativeTo(null);
    setVisible(true);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
//        to call the class constructor
        new SnakeGame();
    }

}