import java.awt.Image;

import javax.swing.ImageIcon;

public class Pacman {

	private final int PACMAN_ANIM_COUNT = 4;
	private final int PAC_ANIM_DELAY = 2;
	private final int PACMAN_SPEED = 6;
	private int pacAnimCount = PACMAN_ANIM_COUNT;
	private int pacAnimDir = 1;
	private int pacmanAnimPos = 0;
	private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
	private Image pacman3up, pacman3down, pacman3left, pacman3right;
	private Image pacman4up, pacman4down, pacman4left, pacman4right;

	Pacman() {
		this.loadImages();
	}

	public void doAnim() {

		pacAnimCount--;

		if (pacAnimCount <= 0) {
			pacAnimCount = PAC_ANIM_DELAY;
			pacmanAnimPos = pacmanAnimPos + pacAnimDir;

			if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
				pacAnimDir = -pacAnimDir;
			}
		}
	}

	private void loadImages() {

		pacman1 = new ImageIcon("images/pacman.png").getImage();
		pacman2up = new ImageIcon("images/up1.png").getImage();
		pacman3up = new ImageIcon("images/up2.png").getImage();
		pacman4up = new ImageIcon("images/up3.png").getImage();
		pacman2down = new ImageIcon("images/down1.png").getImage();
		pacman3down = new ImageIcon("images/down2.png").getImage();
		pacman4down = new ImageIcon("images/down3.png").getImage();
		pacman2left = new ImageIcon("images/left1.png").getImage();
		pacman3left = new ImageIcon("images/left2.png").getImage();
		pacman4left = new ImageIcon("images/left3.png").getImage();
		pacman2right = new ImageIcon("images/right1.png").getImage();
		pacman3right = new ImageIcon("images/right2.png").getImage();
		pacman4right = new ImageIcon("images/right3.png").getImage();

	}

	/**
	 * @return the pacman1
	 */
	public Image getPacman1() {
		return pacman1;
	}

	/**
	 * @param pacman1
	 *            the pacman1 to set
	 */
	public void setPacman1(Image pacman1) {
		this.pacman1 = pacman1;
	}

	/**
	 * @return the pacman2up
	 */
	public Image getPacman2up() {
		return pacman2up;
	}

	/**
	 * @param pacman2up
	 *            the pacman2up to set
	 */
	public void setPacman2up(Image pacman2up) {
		this.pacman2up = pacman2up;
	}

	/**
	 * @return the pacman2left
	 */
	public Image getPacman2left() {
		return pacman2left;
	}

	/**
	 * @param pacman2left
	 *            the pacman2left to set
	 */
	public void setPacman2left(Image pacman2left) {
		this.pacman2left = pacman2left;
	}

	/**
	 * @return the pacman2right
	 */
	public Image getPacman2right() {
		return pacman2right;
	}

	/**
	 * @param pacman2right
	 *            the pacman2right to set
	 */
	public void setPacman2right(Image pacman2right) {
		this.pacman2right = pacman2right;
	}

	/**
	 * @return the pacman2down
	 */
	public Image getPacman2down() {
		return pacman2down;
	}

	/**
	 * @param pacman2down
	 *            the pacman2down to set
	 */
	public void setPacman2down(Image pacman2down) {
		this.pacman2down = pacman2down;
	}

	/**
	 * @return the pacman3up
	 */
	public Image getPacman3up() {
		return pacman3up;
	}

	/**
	 * @param pacman3up
	 *            the pacman3up to set
	 */
	public void setPacman3up(Image pacman3up) {
		this.pacman3up = pacman3up;
	}

	/**
	 * @return the pacman3down
	 */
	public Image getPacman3down() {
		return pacman3down;
	}

	/**
	 * @param pacman3down
	 *            the pacman3down to set
	 */
	public void setPacman3down(Image pacman3down) {
		this.pacman3down = pacman3down;
	}

	/**
	 * @return the pacman3left
	 */
	public Image getPacman3left() {
		return pacman3left;
	}

	/**
	 * @param pacman3left
	 *            the pacman3left to set
	 */
	public void setPacman3left(Image pacman3left) {
		this.pacman3left = pacman3left;
	}

	/**
	 * @return the pacman3right
	 */
	public Image getPacman3right() {
		return pacman3right;
	}

	/**
	 * @param pacman3right
	 *            the pacman3right to set
	 */
	public void setPacman3right(Image pacman3right) {
		this.pacman3right = pacman3right;
	}

	/**
	 * @return the pacman4up
	 */
	public Image getPacman4up() {
		return pacman4up;
	}

	/**
	 * @param pacman4up
	 *            the pacman4up to set
	 */
	public void setPacman4up(Image pacman4up) {
		this.pacman4up = pacman4up;
	}

	/**
	 * @return the pacman4down
	 */
	public Image getPacman4down() {
		return pacman4down;
	}

	/**
	 * @param pacman4down
	 *            the pacman4down to set
	 */
	public void setPacman4down(Image pacman4down) {
		this.pacman4down = pacman4down;
	}

	/**
	 * @return the pacman4left
	 */
	public Image getPacman4left() {
		return pacman4left;
	}

	/**
	 * @param pacman4left
	 *            the pacman4left to set
	 */
	public void setPacman4left(Image pacman4left) {
		this.pacman4left = pacman4left;
	}

	/**
	 * @return the pacman4right
	 */
	public Image getPacman4right() {
		return pacman4right;
	}

	/**
	 * @param pacman4right
	 *            the pacman4right to set
	 */
	public void setPacman4right(Image pacman4right) {
		this.pacman4right = pacman4right;
	}

	/**
	 * @return the pacAnimCount
	 */
	public int getPacAnimCount() {
		return pacAnimCount;
	}

	/**
	 * @param pacAnimCount
	 *            the pacAnimCount to set
	 */
	public void setPacAnimCount(int pacAnimCount) {
		this.pacAnimCount = pacAnimCount;
	}

	/**
	 * @return the pacAnimDir
	 */
	public int getPacAnimDir() {
		return pacAnimDir;
	}

	/**
	 * @param pacAnimDir
	 *            the pacAnimDir to set
	 */
	public void setPacAnimDir(int pacAnimDir) {
		this.pacAnimDir = pacAnimDir;
	}

	/**
	 * @return the pacmanAnimPos
	 */
	public int getPacmanAnimPos() {
		return pacmanAnimPos;
	}

	/**
	 * @param pacmanAnimPos
	 *            the pacmanAnimPos to set
	 */
	public void setPacmanAnimPos(int pacmanAnimPos) {
		this.pacmanAnimPos = pacmanAnimPos;
	}

	/**
	 * @return the pAC_ANIM_DELAY
	 */
	public int getPAC_ANIM_DELAY() {
		return PAC_ANIM_DELAY;
	}

	/**
	 * @return the pACMAN_SPEED
	 */
	public int getPACMAN_SPEED() {
		return PACMAN_SPEED;
	}

	/**
	 * @return the pACMAN_ANIM_COUNT
	 */
	public int getPACMAN_ANIM_COUNT() {
		return PACMAN_ANIM_COUNT;
	}

}
