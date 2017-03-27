package PacTheKnowledge;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
        
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720, 720);
        setLocationRelativeTo(null);
        setVisible(true);        
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }
}