import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

//================Game Panel =====================================
public class Game_Panel extends JPanel implements ActionListener, KeyListener {
    int PanelWidth;
    int PanelHeight;
    final int unitSize = 20;
    public int bodySizeInit = 4;
    public static int Score = 0;
    char Direction = 'R' ;
    boolean RL_Flag = false;
    boolean UD_Flag = true;
    int x;
    int y;
    int xApple;
    int yApple;
    Timer timer;
    Graphics2D G2D;
    Random randomNumber = new Random();
    ArrayList<Rectangle> snakeBody = new ArrayList<>();
    //--------------------------------------------------
    JPanel pnl_Game_Over = new JPanel();
    JLabel lbl_Game_Status = new JLabel("Game Over !");
    JLabel lbl_Game_Score = new JLabel("Score :  ");
    JButton btn_Replay = new JButton("Replay");
    //--------------------------------------------------

    public Game_Panel(int panelWidth, int panelHeight) {
        this.PanelWidth = panelWidth;
        this.PanelHeight = panelHeight;
        this.setSize(PanelWidth + 17, PanelHeight + 40);
        this.setBackground(Color.BLACK);
        this.addKeyListener(this);
        this.setFocusable(true);        // enable the panel to receive key events.
        this.setLayout(null);

        timer = new Timer(100,this);
        timer.start();
        xApple = randomNumber.nextInt(PanelWidth/unitSize)*unitSize;
        yApple = randomNumber.nextInt(PanelHeight/unitSize)*unitSize;

        InitSnake();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        G2D = (Graphics2D) g;
        DrawGrid(G2D);
        drawSnake();
        drawApple();
        snakeCrash();

    }

    public void DrawGrid(Graphics2D G2D) {
        G2D.setPaint(Color.RED);
        for (int i = 0; i <= PanelHeight; i += unitSize) {
            G2D.drawLine(0, i, PanelWidth, i);
        }
        for (int j = 0; j <= PanelWidth; j += unitSize) {
            G2D.drawLine(j, 0, j, PanelHeight);
        }
    }

    public void InitSnake(){
        x = PanelWidth/2;
        y = PanelHeight/2;
        snakeBody.clear();
        for(int i=0; i<bodySizeInit*unitSize; i+=unitSize){
            snakeBody.add(new Rectangle(x+i , y, unitSize,unitSize));
        }
        x = snakeBody.get(snakeBody.size()-1).x;
    }

    public void drawSnake(){
        for(int i = 0; i<snakeBody.size(); i++){
            G2D.setPaint(Color.GREEN);
            G2D.fillRect( snakeBody.get(i).x, snakeBody.get(i).y, unitSize, unitSize);
            G2D.setPaint(Color.GRAY);
            G2D.drawRect( snakeBody.get(i).x, snakeBody.get(i).y, unitSize, unitSize);
        }
    }

    public void drawApple(){
        G2D.setPaint(Color.GREEN);
        G2D.fillOval(xApple, yApple, unitSize, unitSize);
    }

    public boolean snakeEatApple(){
        if(snakeBody.get(snakeBody.size()-1).x == xApple && snakeBody.get(snakeBody.size()-1).y == yApple){
            xApple = randomNumber.nextInt(PanelWidth/unitSize)*unitSize;
            yApple = randomNumber.nextInt(PanelHeight/unitSize)*unitSize;
            Score += 10;
            return true;
        }
        else return false;
    }

    public void snakeCrash(){
        for(int i=0; i<snakeBody.size()-1; i++){
            if(snakeBody.get(i).x == snakeBody.get(snakeBody.size()-1).x &&
                    snakeBody.get(i).y == snakeBody.get(snakeBody.size()-1).y){
                Direction = 'S';
                timer.stop();
                drawGameOver(true);
            }
        }
    }

    public void drawGameOver(boolean visible){
        pnl_Game_Over.setSize(PanelWidth + 17, PanelHeight + 40);
        pnl_Game_Over.setBackground(Color.BLACK);
        pnl_Game_Over.setLayout(null);
        pnl_Game_Over.setVisible(visible);

        lbl_Game_Status.setForeground(Color.decode("#C5C6C7"));
        lbl_Game_Status.setFont(new Font("Ink Free", Font.BOLD, 30));
        lbl_Game_Status.setBounds(170, 100, 200,50);
        //lbl_Game_Status.setVisible(visible);

        lbl_Game_Score.setForeground(Color.decode("#C5C6C7"));
        lbl_Game_Score.setFont(new Font("Ink Free", Font.BOLD, 20));
        lbl_Game_Score.setBounds(170, 170,200,50);
        lbl_Game_Score.setText("Score :     " + Score);
        //lbl_Game_Score.setVisible(visible);

        btn_Replay.setFocusable(false);
        btn_Replay.setBackground(Color.decode("#1F2833"));
        btn_Replay.setForeground(Color.decode("#C5C6C7"));
        btn_Replay.setFont(new Font("Ink Free", Font.BOLD, 20));
        btn_Replay.setBounds(185,250,150,50);
        btn_Replay.addActionListener(this);
        //btn_Replay.setVisible(visible);

        pnl_Game_Over.add(lbl_Game_Status);
        pnl_Game_Over.add(lbl_Game_Score);
        pnl_Game_Over.add(btn_Replay);

        this.add(pnl_Game_Over);


    }



    @Override
    public void actionPerformed(ActionEvent e) {
        switch(Direction){
            case 'L': {
                x -= unitSize;
                if(x < 0) x = PanelWidth-unitSize;
                if(!snakeEatApple()) snakeBody.remove(0);
                snakeBody.add(new Rectangle(x,y,unitSize,unitSize));

            }
            break;
            case 'U': {
                y -= unitSize;
                if(y < 0) y = PanelHeight-unitSize;
                if(!snakeEatApple()) snakeBody.remove(0);
                snakeBody.add(new Rectangle(x,y,unitSize,unitSize));
            }
            break;
            case 'R': {
                x += unitSize;
                if(x >= PanelWidth ) x =0;
                if(!snakeEatApple()) snakeBody.remove(0);
                snakeBody.add(new Rectangle(x,y,unitSize,unitSize));
            }
            break;
            case 'D': {
                y += unitSize;
                if(y >= PanelHeight ) y =0;
                if(!snakeEatApple()) snakeBody.remove(0);
                snakeBody.add(new Rectangle(x,y,unitSize,unitSize));
            }
            break;
        }

        repaint();
        //----------------------------------------
        if(e.getSource() == btn_Replay){
            InitSnake();
            DrawGrid(G2D);
            timer.start();
            Direction = 'R';
            RL_Flag = false;
            UD_Flag = true;
            Score = 0;
            drawGameOver(false);
        }
        //-----------------------------------------

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case 37: {
                if(RL_Flag) {
                    Direction = 'L';
                    RL_Flag = false;
                    UD_Flag = true;
                }
            }
            break;
            case 38: {
                if(UD_Flag) {
                    Direction = 'U';
                    RL_Flag = true;
                    UD_Flag = false;
                }
            }
            break;
            case 39: {
                if(RL_Flag) {
                    Direction = 'R';
                    RL_Flag = false;
                    UD_Flag = true;
                }
            }
            break;
            case 40: {
                if(UD_Flag) {
                    Direction = 'D';
                    RL_Flag = true;
                    UD_Flag = false;
                }
            }
            break;
            default:
                System.out.println("Please press a valid Key !!");
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }


}
