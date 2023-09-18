import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Making ths a Swing component so that it can be added in Snake class Constructor
public class Board extends JPanel implements ActionListener {
//    globalizing the images so that it can be used for other methods as well
    private Image apple;
    private Image dot;
    private Image head;

//    Area of the Frame
    private final int ALL_DOTS = 2500;
    private final int DOT_SIZE =10;

//    Random generator
    private final int  Random_position = 29;

//    apple coordinate variable
    private  int apple_x;
    private  int apple_y;

    private final int x[] = new int[ALL_DOTS];
    private final int y[]= new int[ALL_DOTS];

    private boolean leftDirection =false;
    private boolean rightDirection = true;
    private  boolean upDirection =false;
    private boolean downDirection = false;

//    for game reference
    private boolean inGame = true;

    private int dots;
    private Timer timer;

    Board(){
//        creating object and adding event of the adapter
        addKeyListener(new Adapter());
        setBackground(Color.BLACK);
        setFocusable(true);

//        image needs to be loaded before initializing the game
        loadImage();
//        initializing the game
        initGame();
    }


    public void loadImage(){
        ImageIcon img1 = new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
        apple = img1.getImage();
        ImageIcon img2 = new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot = img2.getImage();
        ImageIcon img3 = new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        head = img3.getImage();
    }

    public void initGame(){
        dots =3;
        for(int i=0; i<dots;i++){
//           starting vertical height
            y[i]=50;
//            starting horizontal height
            x[i]=50 -i*DOT_SIZE;
        }
//        calling the locateApple method;
        locateApple();

//      for increasing the snake
        timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple(){
        int r =(int)(Math.random()*Random_position);
        apple_x =r * DOT_SIZE;
        r =(int)(Math.random()*Random_position);
        apple_y =r*DOT_SIZE;

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

//    image Draw garna lai
    public  void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
            for(int i =0 ; i<dots;i++){
                if(i==0){
//                if zero head should appear first
                    g.drawImage(head, x[i],y[i],this);
                }
//            else dots should connect
                else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }

    }
//    gameOVer
    public void gameOver(Graphics g){
        String msg = "GAME OVER";
        Font font = new Font("SANS_SERIF", Font.BOLD, 35);
        FontMetrics metrics = getFontMetrics(font);

        g.setFont(font);
        g.setColor(Color.RED);

        int x = (500 - metrics.stringWidth(msg)) / 2;
        int y = 500 / 2;

        g.drawString(msg, x, y);
    }


    public void move(){
        for(int i =dots; i>0;i--){
//            moving the body part (ek step agadi leuxa)
            x[i] =x[i-1];
            y[i] = y[i-1];
        }
//        checking the movement for the left side
        if(leftDirection){
            x[0]= x[0] -DOT_SIZE;

        }
        if(rightDirection){
            x[0]= x[0]+DOT_SIZE;
        }
        if(upDirection){
            y[0] = y[0] -DOT_SIZE;
        }
        if(downDirection){
            y[0]= y[0]+ DOT_SIZE;
        }
//         to check if it is moving the head part
//        x[0] += DOT_SIZE;
//        y[0] += DOT_SIZE;
    }

//    to check if the head got the apple or not
    public  void checkapple(){
        if((x[0]== apple_x) &&(y[0] ==apple_y)){
            dots++;
            locateApple();
        }
    }

//    to check for collision
    public void checkCollision(){
//        checking if head is touches the body
        for(int i =dots; i>0; i--){
//            x[0] means head and x[i] means rest of the body
            if((i>4) && (x[0]==x[i]) && (y[0] == y[i])){
                inGame = false;
            }
        }
//            if it touches the boudnary
            if(y[0]>=500){
                inGame =false;
            }
            if(x[0]>=500){
                inGame =false;
            }
            if(x[0]<0){
                inGame =false;
            }
            if(y[0]<0){
                inGame =false;
            }

//      if inGame false xa vane snake move hunu vayena
        if(!inGame){
            timer.stop();
        }

    }
    public void actionPerformed(ActionEvent e){
        if(inGame) {
            checkapple();
            checkCollision();
            move();
        }

        repaint();

    }

//    making a inner class
    public class Adapter extends  KeyAdapter{
        @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

//        this is to check if the left arrow key is pressed or not
        if(key == KeyEvent.VK_LEFT && (!rightDirection)){
            leftDirection =true;
            upDirection =false;
            downDirection =false;
        }
        if(key ==KeyEvent.VK_RIGHT &&(!leftDirection)){
            rightDirection =true;
            upDirection =false;
            downDirection =false;
        }
        if(key ==KeyEvent.VK_UP &&(!downDirection )){
            rightDirection =false;
            upDirection =true;
            leftDirection =false;
        }
        if(key ==KeyEvent.VK_DOWN &&(!upDirection)){
            rightDirection =false;
            leftDirection =false;
            downDirection =true;
        }

    }

}

}
