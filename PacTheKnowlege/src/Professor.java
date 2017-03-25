import java.awt.Image;

import javax.swing.ImageIcon;

public class Gohst {


	private Image ghost;
	
	
		Gohst(){
		this.loadImages();
	}

	
	
	
	/**
	 * @return the ghost
	 */
	public Image getGhost() {
		return ghost;
	}




	/**
	 * @param ghost the ghost to set
	 */
	public void setGhost(Image ghost) {
		this.ghost = ghost;
	}





	
	
	
	private void loadImages() {
		ghost = new ImageIcon("images/ghost.png").getImage();
    }
	
	
	
}
