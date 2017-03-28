
public class LevelFinishedTimer extends Thread {
	
	private Board board;

	public LevelFinishedTimer(Board board){
		this.board=board;
	}
	
	public void run(){
		try {
			sleep(2000);
			board.setVictory(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
