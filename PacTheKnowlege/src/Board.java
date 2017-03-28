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
    private boolean inGame = false;
    private boolean dying = false;
    public static final int BLOCK_SIZE = 48;
    public static final int NUMBER_OF_BLOCKS = 15;
    private final int SCREEN_SIZE = NUMBER_OF_BLOCKS * BLOCK_SIZE;
    public static int studentsLeft, score;
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
    private Image professor;
    private Image coffeeImage;
    private Image hello;

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
    }

    private void playGame(Graphics2D g2d) {

        if (dying) {
            death();
        } else {
            student.moveStudent();
            drawstudent(g2d);
            moveprofessors(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {
        g2d.drawImage(hello, 48, 240,this);
    }

    private void drawScore(Graphics2D g) {

        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (i = 0; i < studentsLeft; i++) {
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

        studentsLeft--;

        if (studentsLeft == 0) {
            inGame = false;
        }

        continueLevel(true);
    }

    private void moveprofessors(Graphics2D g2d) {

        short professorNumber;

        for (professorNumber = 0; professorNumber < Professor.NUMBER_PROFS; professorNumber++) {
        	if(professorArray[professorNumber].getProfInGame()){
        		professorArray[professorNumber].move();
            	drawprofessor(g2d, professorArray[professorNumber].getXPos() + 1, professorArray[professorNumber].getYPos() + 1);
                
                int student_x=student.getXPosition(),
                		student_y=student.getYPosition();

                if (student_x > (professorArray[professorNumber].getXPos() - 12) && student_x < (professorArray[professorNumber].getXPos() + 12)
                        && student_y > (professorArray[professorNumber].getYPos() - 12) && 
                        student_y < (professorArray[professorNumber].getYPos() + 12)
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

    private void drawprofessor(Graphics2D g2d, int x, int y) {
        g2d.drawImage(professor, x, y, this);
    }

    private void drawstudent(Graphics2D g2d) {
    	
    	int studentXPosition=student.getXPosition(),
    			studentYPosition=student.getYPosition();

    	int view_dx=student.getViewX(),
    			view_dy=student.getViewY();
        if (view_dx == -1) {
            drawstudentnanLeft(g2d, studentXPosition, studentYPosition);
        } else if (view_dx == 1) {
            drawstudentRight(g2d, studentXPosition, studentYPosition);
        } else if (view_dy == -1) {
            drawstudentUp(g2d, studentXPosition, studentYPosition);
        } else if (view_dy == 1) {
            drawstudentDown(g2d, studentXPosition, studentYPosition);
        } else {
            drawstudent(g2d, studentXPosition, studentYPosition);
        }
    }

    private void drawstudentUp(Graphics2D g2d, int student_x, int student_y) {

    	if(!student.getSuperMode()){
                g2d.drawImage(studentUp, student_x + 1, student_y + 1, this);
    	}else{
    		g2d.drawImage(superStudentUp, student_x + 1, student_y + 1, this);
       	}
    }

    private void drawstudentDown(Graphics2D g2d, int student_x, int student_y) {

    	if(!student.getSuperMode()){
            g2d.drawImage(studentDown, student_x + 1, student_y + 1, this);
    	}else{
    		g2d.drawImage(superStudentDown, student_x + 1, student_y + 1, this);
       	}
    }

    private void drawstudentnanLeft(Graphics2D g2d, int student_x, int student_y) {

         if(!student.getSuperMode()){
                g2d.drawImage(studentLeft, student_x + 1, student_y + 1, this);
    	}else{
                g2d.drawImage(superStudentLeft, student_x + 1, student_y + 1, this);
    	}
    }

    private void drawstudentRight(Graphics2D g2d, int student_x, int student_y) {

    	if(!student.getSuperMode()){
                g2d.drawImage(studentRight, student_x + 1, student_y + 1, this);
    	}else{
                g2d.drawImage(superStudentRight, student_x + 1, student_y + 1, this);
    	} 
    }
    
    private void drawstudent(Graphics2D g2d, int student_x, int student_y) {

    	if(!student.getSuperMode()){
                g2d.drawImage(studentStill, student_x + 1, student_y + 1, this);
    	}else{
                g2d.drawImage(superStudentStill, student_x + 1, student_y + 1, this);
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

        studentsLeft = 3;
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
    	hello = new ImageIcon(getClass().getResource("images/Begrüßung.png")).getImage();
        professor = new ImageIcon(getClass().getResource("images/Professor.png")).getImage();
        boardImage = new ImageIcon(getClass().getResource("images/Spielfeld.png")).getImage();
        sheetImage = new ImageIcon(getClass().getResource("images/Zettel.png")).getImage();
        coffeeImage=new ImageIcon(getClass().getResource("images/Kaffee.png")).getImage();   
        studentStill = new ImageIcon(getClass().getResource("images/Student-vorne.png")).getImage();
		studentUp = new ImageIcon(getClass().getResource("images/Student-oben.png")).getImage();
		studentDown = new ImageIcon(getClass().getResource("images/Student-unten.png")).getImage();
		studentLeft = new ImageIcon(getClass().getResource("images/Student-links.png")).getImage();
		studentRight = new ImageIcon(getClass().getResource("images/Student-rechts.png")).getImage();
        superStudentStill = new ImageIcon(getClass().getResource("images/Super-Student-vorne.png")).getImage();
        superStudentUp = new ImageIcon(getClass().getResource("images/Super-Student-oben.png")).getImage();
        superStudentDown = new ImageIcon(getClass().getResource("images/Super-Student-unten.png")).getImage();
        superStudentLeft = new ImageIcon(getClass().getResource("images/Super-Student-links.png")).getImage();
        superStudentRight = new ImageIcon(getClass().getResource("images/Super-Student-rechts.png")).getImage();
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