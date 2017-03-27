import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	private Student student=new Student();
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);
    private Image ii;
    private boolean inGame = false;
    private boolean dying = false;
    public static final int BLOCK_SIZE = 48;
    public static final int NUMBER_OF_BLOCKS = 15;
    private final int SCREEN_SIZE = NUMBER_OF_BLOCKS * BLOCK_SIZE;
    public static int pacsLeft, score;
    private Image boardImage;
    private Image sheetImage;
    private final short levelData[] = {
    		3,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  6, 
    		1,  0, 19, 26, 26, 22,  0,  0,  0, 19, 26, 26, 26, 22,  4, 
    		1,  0, 21,  0,  0, 25, 22,  0,  0, 21,  0,  0,  0, 21,  4, 
    		1, 19, 24, 22,  0,  0, 25, 18, 26, 16, 26, 26, 26, 20,  4, 
    		1, 21,  0, 17, 26, 22,  0, 21,  0, 21,  0,  0,  0, 21,  4, 
    		1, 25, 26, 20,  0, 21,  0, 21,  0, 21,  0, 19, 26, 20,  4, 
    		1,  0,  0, 21,  0, 17, 26, 24, 26, 16, 26, 20,  0, 21,  4,
    		1, 19, 26, 20,  0, 21,  0,  0,  0, 21,  0, 21,  0, 21,  4, 
    		1, 21,  0, 17, 26, 24, 18, 26, 26, 20,  0, 25, 18, 28,  4,
    		1, 21,  0, 21,  0,  0, 21,  0,  0, 21,  0,  0, 21,  0,  4, 
    		1, 21,  0, 17, 26, 18, 28,  0, 19, 24, 26, 26, 24, 22,  4, 
    		1, 17, 26, 28,  0, 21,  0,  0, 21,  0,  0,  0,  0, 21,  4,
    		1, 21,  0,  0,  0, 17, 18, 26, 24, 26, 26, 22,  0, 21,  4, 
    		1, 25, 26, 26, 26, 24, 28,  0,  0,  0,  0, 25, 26, 28,  4, 
    		9,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  8,  12
    };
    private final int maxSpeed = 6;
    public static int currentSpeed = 3;
    public static short[] screenData;
    private Timer timer;
    private Image studentStill, studentUp, studentLeft, studentRight, studentDown;
    private Image superStudentStill, superStudentUp, superStudentLeft, superStudentRight, superStudentDown;
    private Professor[] professorArray;
    private Image ghost;
    private Image coffeeImage;

    public Board() {
        loadImages();
        initVariables();
        initBoard();
    }
    
    private void initBoard() {
        
        addKeyListener(new TastaturEingabeRegler());

        setFocusable(true);

        setBackground(Color.black);
        setDoubleBuffered(true);        
    }

    private void initVariables() {

        screenData = new short[NUMBER_OF_BLOCKS * NUMBER_OF_BLOCKS];
        professorArray=new Professor[Professor.MAX_PROFS];
        for(int profIndex=0;profIndex<Professor.MAX_PROFS;profIndex++){
        	professorArray[profIndex]=new Professor();
        }
        
        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

//        initGame();
    }

    private void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else {

            student.moveStudent();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String s = "Press s to start.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }

    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (i = 0; i < pacsLeft; i++) {
            g.drawImage(studentStill, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    private void checkMaze() {

        short i = 0;
        boolean finished = true;

        while (i < NUMBER_OF_BLOCKS * NUMBER_OF_BLOCKS && finished) {

            if ((screenData[i] & 48) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (Professor.NUMBER_PROFS < Professor.MAX_PROFS) {
                Professor.NUMBER_PROFS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel(false);
        }
    }

    private void death() {

        pacsLeft--;

        if (pacsLeft == 0) {
            inGame = false;
        }

        continueLevel(true);
    }

    private void moveGhosts(Graphics2D g2d) {

        short professorNumber;

        for (professorNumber = 0; professorNumber < Professor.NUMBER_PROFS; professorNumber++) {
        	if(professorArray[professorNumber].getProfInGame()){
        		professorArray[professorNumber].move();
            	drawGhost(g2d, professorArray[professorNumber].getXPos() + 1, professorArray[professorNumber].getYPos() + 1);
                
                int pacman_x=student.getXPosition(),
                		pacman_y=student.getYPosition();

                if (pacman_x > (professorArray[professorNumber].getXPos() - 12) && pacman_x < (professorArray[professorNumber].getXPos() + 12)
                        && pacman_y > (professorArray[professorNumber].getYPos() - 12) && 
                        pacman_y < (professorArray[professorNumber].getYPos() + 12)
                        && inGame && professorArray[professorNumber].getProfInGame()) {
                	if(student.getSuperMode()){
                		professorArray[professorNumber].kickProfAway();
                	}else{
                        dying = true;
                	}
                }
        	}     	
        }
    }

    private void drawGhost(Graphics2D g2d, int x, int y) {
        g2d.drawImage(ghost, x, y, this);
    }

    private void drawPacman(Graphics2D g2d) {
    	
    	int studentXPosition=student.getXPosition(),
    			studentYPosition=student.getYPosition();

    	int view_dx=student.getViewX(),
    			view_dy=student.getViewY();
        if (view_dx == -1) {
            drawPacnanLeft(g2d, studentXPosition, studentYPosition);
        } else if (view_dx == 1) {
            drawPacmanRight(g2d, studentXPosition, studentYPosition);
        } else if (view_dy == -1) {
            drawPacmanUp(g2d, studentXPosition, studentYPosition);
        } else if (view_dy == 1) {
            drawPacmanDown(g2d, studentXPosition, studentYPosition);
        } else {
            drawPacman(g2d, studentXPosition, studentYPosition);
        }
    }

    private void drawPacmanUp(Graphics2D g2d, int pacman_x, int pacman_y) {

    	if(!student.getSuperMode()){
                g2d.drawImage(studentUp, pacman_x + 1, pacman_y + 1, this);
    	}else{
    		g2d.drawImage(superStudentUp, pacman_x + 1, pacman_y + 1, this);
       	}
    }

    private void drawPacmanDown(Graphics2D g2d, int pacman_x, int pacman_y) {

    	if(!student.getSuperMode()){
            g2d.drawImage(studentDown, pacman_x + 1, pacman_y + 1, this);
    	}else{
    		g2d.drawImage(superStudentDown, pacman_x + 1, pacman_y + 1, this);
       	}
    }

    private void drawPacnanLeft(Graphics2D g2d, int pacman_x, int pacman_y) {

         if(!student.getSuperMode()){
                g2d.drawImage(studentLeft, pacman_x + 1, pacman_y + 1, this);
    	}else{
                g2d.drawImage(superStudentLeft, pacman_x + 1, pacman_y + 1, this);
    	}
    }

    private void drawPacmanRight(Graphics2D g2d, int pacman_x, int pacman_y) {

    	if(!student.getSuperMode()){
                g2d.drawImage(studentRight, pacman_x + 1, pacman_y + 1, this);
    	}else{
                g2d.drawImage(superStudentRight, pacman_x + 1, pacman_y + 1, this);
    	} 
    }
    
    private void drawPacman(Graphics2D g2d, int pacman_x, int pacman_y) {

    	if(!student.getSuperMode()){
                g2d.drawImage(studentStill, pacman_x + 1, pacman_y + 1, this);
    	}else{
                g2d.drawImage(superStudentStill, pacman_x + 1, pacman_y + 1, this);
    	} 
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                if ((screenData[i] & 16) != 0) { 
                    g2d.drawImage(sheetImage, x + 11, y + 11, 20, 20, this);

                }

                i++;
            }
        }
    }

    private void initGame() {

        pacsLeft = 3;
        score = 0;
        initLevel(false);
        Professor.NUMBER_PROFS = 6;
        currentSpeed = 3;
    }

    private void initLevel(boolean death) {

        int i;
        for (i = 0; i < NUMBER_OF_BLOCKS * NUMBER_OF_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel(death);
    }

    private void continueLevel(boolean death) {

        short profIndex;
        int dx = 1;

        for (profIndex = 0; profIndex < Professor.NUMBER_PROFS; profIndex++) {
        	
        	if((!death) || professorArray[profIndex].getProfInGame()){
            	professorArray[profIndex].reset(dx);
                dx = -dx;
        	}

        }

        student.reset(death);
        dying = false;
    }

    private void loadImages() {
        ghost = new ImageIcon("images/professor.png").getImage();   
        studentStill = new ImageIcon("images/Student-vorne.png").getImage();
		studentUp = new ImageIcon("images/Student-oben.png").getImage();
		studentDown = new ImageIcon("images/Student-unten.png").getImage();
		studentLeft = new ImageIcon("images/Student-links.png").getImage();
		studentRight = new ImageIcon("images/Student-rechts.png").getImage();
        boardImage = new ImageIcon("images/Spielfeld.png").getImage();
        sheetImage = new ImageIcon("images/zettel.png").getImage();
        
        //PFADE ÄNDERN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        coffeeImage=new ImageIcon("images/kaffee2.png").getImage();
        superStudentStill = new ImageIcon("images/Super-Student-vorne.png").getImage();
        superStudentUp = new ImageIcon("images/Super-Student-oben.png").getImage();
        superStudentDown = new ImageIcon("images/Super-Student-unten.png").getImage();
        superStudentLeft = new ImageIcon("images/Super-Student-links.png").getImage();
        superStudentRight = new ImageIcon("images/Super-Student-rechts.png").getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(boardImage, 0, 0, 720, 720, this);

        drawMaze(g2d);
        drawScore(g2d);
        student.doAnim();
        drawCoffee(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    private void drawCoffee(Graphics2D g2d) {
    	if(student.isCoffeeHot()){
        	g2d.drawImage(coffeeImage, student.getCoffeeXPos(), student.getCoffeeYPos(), this);	
    	}
	}

	class TastaturEingabeRegler extends KeyAdapter {

        private boolean leftPressed=false;
        private boolean upPressed=false;
        private boolean downPressed=false;
        private boolean rightPressed=false;

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                	student.moveLeft();
                    leftPressed=true;
                } else if (key == KeyEvent.VK_RIGHT) {
                	student.moveRight();
                    rightPressed=true;
                } else if (key == KeyEvent.VK_UP) {
                	student.moveUp();
                    upPressed=true;
                } else if (key == KeyEvent.VK_DOWN) {
                	student.moveDown();
                    downPressed=true;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
//                } else if (key == KeyEvent.VK_PAUSE) {
//                    if (timer.isRunning()) {
//                        timer.stop();
//                    } else {
//                        timer.start();
//                    }
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT
                    || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
            	student.setMovementToZero();
            }
            
            if(key==KeyEvent.VK_LEFT){
            	leftPressed=false;
            }else if(key==KeyEvent.VK_RIGHT){
            	rightPressed=false;
            }else if(key==KeyEvent.VK_UP){
            	upPressed=false;
            }else if(key==KeyEvent.VK_DOWN){
            	downPressed=false;
            }
            
            //Wenn kein oder ein Button gedrückt bleibt...
            if(!((leftPressed&&rightPressed)||(leftPressed&&upPressed)||
            		(leftPressed&&downPressed)||(rightPressed&&upPressed)||
            		(rightPressed&&downPressed)||(upPressed&&downPressed))){
            	//...Und ein Button gedrückt bleibt, geht das Momentum in die Richtung der gedrückten Taste
            	if (leftPressed) {
            		student.moveLeft();
                } else if (rightPressed) {
                	student.moveRight();
                } else if (upPressed) {
                	student.moveUp();
                } else if (downPressed) {
                	student.moveDown();
                }
            }
            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}