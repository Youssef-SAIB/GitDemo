import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Game_Frame extends JFrame {
    static final int FrameWidth = 520;
    static final int FrameHeight = 520;
    Game_Panel game_panel = new Game_Panel(FrameWidth,FrameHeight);

    public Game_Frame(){
        this.setSize(FrameWidth + 17, FrameHeight + 40);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Snake Game");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        this.add(game_panel);

    }


    public static void main(String[] args) {
        new Game_Frame().setVisible(true);
    }
}
