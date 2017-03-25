import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Spielfeld());
        
        setTitle("Pacman");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(780, 850);
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