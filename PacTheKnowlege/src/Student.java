import java.awt.Image;
import javax.swing.ImageIcon;

public class Student {

	private final int STUDENT_ANIM_COUNT = 4;
	private final int STUDENT_ANIM_DELAY = 2;
	private final int STUDENT_SPEED = 6;
	private int studentAnimCount = STUDENT_ANIM_COUNT;
	private int studentAnimDir = 1;
	private int studentAnimPos = 0;
	private Image student1, student2up, student2left, student2right, student2down;
	private Image student3up, student3down, student3left, student3right;
	private Image student4up, student4down, student4left, student4right;
	private int student_x, student_y, studentd_x, studentd_y;
	private int req_dx, req_dy, view_dx, view_dy;
	private Coffee coffee =new Coffee();
	private boolean superMode=false;
	private SuperTimer superTimer;

	Student() {
		this.loadImages();
	}

	public void doAnim() {

		studentAnimCount--;

		if (studentAnimCount <= 0) {
			studentAnimCount = STUDENT_ANIM_DELAY;
			studentAnimPos = studentAnimPos + studentAnimDir;

			if (studentAnimPos == (STUDENT_ANIM_COUNT - 1) || studentAnimPos == 0) {
				studentAnimDir = -studentAnimDir;
			}
		}
	}

	private void loadImages() {

		student1 = new ImageIcon("images/Student.png").getImage();
		student2up = new ImageIcon("images/Student-oben.png").getImage();
		student3up = new ImageIcon("images/Student-oben.png").getImage();
		student4up = new ImageIcon("images/Student-oben.png").getImage();
		student2down = new ImageIcon("images/Student-unten.png").getImage();
		student3down = new ImageIcon("images/Student-unten.png").getImage();
		student4down = new ImageIcon("images/Student-unten.png").getImage();
		student2left = new ImageIcon("images/Student-links.png").getImage();
		student3left = new ImageIcon("images/Student-links.png").getImage();
		student4left = new ImageIcon("images/Student-links.png").getImage();
		student2right = new ImageIcon("images/Student-rechts.png").getImage();
		student3right = new ImageIcon("images/Student-rechts.png").getImage();
		student4right = new ImageIcon("images/Student-rechts.png").getImage();

	}
	
	public void moveStudent() {

        int pos;
        short ch;

        if (req_dx == -studentd_x && req_dy == -studentd_y) {
            studentd_x = req_dx;
            studentd_y = req_dy;
            view_dx = studentd_x;
            view_dy = studentd_y;
        }

        if (student_x % Board.BLOCK_SIZE == 0 && student_y % Board.BLOCK_SIZE == 0) {
            pos = student_x / Board.BLOCK_SIZE + Board.NUMBER_OF_BLOCKS * (int) (student_y / Board.BLOCK_SIZE);
            ch = Board.screenData[pos];

            if ((ch & 16) != 0) {
            	Board.screenData[pos] = (short) (ch & 15);
                Board.score++;
            }
            
            coffee.studentIsOnField((int) (student_x / Board.BLOCK_SIZE+15*(student_y / Board.BLOCK_SIZE)));
            
            if(this.samePositionAsCoffee()){
            	drinkCoffee();
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    studentd_x = req_dx;
                    studentd_y = req_dy;
                    view_dx = studentd_x;
                    view_dy = studentd_y;
                }
            }

            // Check for standstill
            if ((studentd_x == -1 && studentd_y == 0 && (ch & 1) != 0)
                    || (studentd_x == 1 && studentd_y == 0 && (ch & 4) != 0)
                    || (studentd_x == 0 && studentd_y == -1 && (ch & 2) != 0)
                    || (studentd_x == 0 && studentd_y == 1 && (ch & 8) != 0)) {
                studentd_x = 0;
                studentd_y = 0;
            }
        }
        student_x = student_x + STUDENT_SPEED* studentd_x;
        student_y = student_y + STUDENT_SPEED * studentd_y;
    }

	private void drinkCoffee() {
		coffee.resetOfDeathOrDrinkOrEndOfSuper();
		this.superMode=true;
		superTimer=new SuperTimer(this);
		superTimer.start();
	}

	private boolean samePositionAsCoffee() {
		return coffee.getIsHot()&&student_x==coffee.getXPos()&&student_y==coffee.getYPos();
	}

	/**
	 * @return the studentAnimPos
	 */
	public int getstudentAnimPos() {
		return studentAnimPos;
	}

	/**
	 * @param studentAnimPos
	 *            the studentAnimPos to set
	 */
	public void setstudentAnimPos(int studentAnimPos) {
		this.studentAnimPos = studentAnimPos;
	}

	/**
	 * @return the STUDENT_ANIM_DELAY
	 */
	public int getSTUDENT_ANIM_DELAY() {
		return STUDENT_ANIM_DELAY;
	}

	/**
	 * @return the STUDENT_SPEED
	 */
	public int getSTUDENT_SPEED() {
		return STUDENT_SPEED;
	}

	/**
	 * @return the STUDENT_ANIM_COUNT
	 */
	public int getSTUDENT_ANIM_COUNT() {
		return STUDENT_ANIM_COUNT;
	}

	public int getYPosition() {
		return student_y;
	}

	public int getXPosition() {
		return student_x;
	}

	public int getViewX() {
		return view_dx;
	}
	
	public int getViewY() {
		return view_dy;
	}

	public synchronized void reset(boolean death) {
		student_x = 7 * Board.BLOCK_SIZE;
        student_y = 8 * Board.BLOCK_SIZE;
        studentd_x = 0;
        studentd_y = 0;
        req_dx = 0;
        req_dy = 0;
        view_dx = -1;
        view_dy = 0;
        if(death){
        	coffee.resetOfDeathOrDrinkOrEndOfSuper();
        }else{
        	coffee.resetOfSuccess();
        }
	}

	public void moveLeft() {
		req_dx = -1;
        req_dy = 0;
	}

	public void moveRight() {
		req_dx = 1;
        req_dy = 0;
	}

	public void moveUp() {
		req_dx = 0;
        req_dy = -1;
	}

	public void moveDown() {
        req_dx = 0;
        req_dy = 1;
	}

	public void setMovementToZero() {
        req_dx = 0;
        req_dy = 0;
	}

	public void normalize() {
		coffee.resetOfDeathOrDrinkOrEndOfSuper();
		superMode=false;
	}

	public boolean getSuperMode() {
		return superMode;
	}

	public int getCoffeeXPos() {
		return coffee.getXPos();
	}
	
	public int getCoffeeYPos() {
		return coffee.getYPos();
	}

	public boolean isCoffeeHot() {
		return coffee.getIsHot();
	}

}
