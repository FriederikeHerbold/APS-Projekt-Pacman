public class Professor {


	public static int NUMBER_PROFS;
	public static final int MAX_PROFS = 20;
	private int professor_x = 7 * Board.BLOCK_SIZE, professor_y = 3 * Board.BLOCK_SIZE, professor_dx, professor_dy;
	private static int validSpeeds[] = {1, 2, 3, 4, 6, 8};
	int professorSpeed=validSpeeds[(int) (Math.random() * (Board.currentSpeed + 1))];
	private boolean profInGame=true;
	
	
		Professor(){
	}

	public void move() {

        int pos;
        int count;
		if (professor_x % Board.BLOCK_SIZE == 0 && professor_y % Board.BLOCK_SIZE == 0) {
            pos = professor_x / Board.BLOCK_SIZE + Board.NUMBER_OF_BLOCKS * (int) (professor_y / Board.BLOCK_SIZE);

            count = 0;

            int[] dx=new int[4], dy=new int[4];
			if ((Board.screenData[pos] & 1) == 0 && professor_dx != 1) {
                dx[count] = -1;
                dy[count] = 0;
                count++;
            }

            if ((Board.screenData[pos] & 2) == 0 && professor_dy != 1) {
                dx[count] = 0;
                dy[count] = -1;
                count++;
            }

            if ((Board.screenData[pos] & 4) == 0 && professor_dx != -1) {
                dx[count] = 1;
                dy[count] = 0;
                count++;
            }

            if ((Board.screenData[pos] & 8) == 0 && professor_dy != -1) {
                dx[count] = 0;
                dy[count] = 1;
                count++;
            }

            if (count == 0) {

                if ((Board.screenData[pos] & 15) == 15) {
                    professor_dx = 0;
                    professor_dy = 0;
                } else {
                    professor_dx = -professor_dx;
                    professor_dy = -professor_dy;
                }

            } else {

                count = (int) (Math.random() * count);

                if (count > 3) {
                    count = 3;
                }

                professor_dx = dx[count];
                professor_dy = dy[count];
            }

        }

        professor_x = professor_x + (professor_dx * professorSpeed);
        professor_y = professor_y + (professor_dy * professorSpeed);
        }

	public void reset(int dx) {
		int random;
		professor_y = 3 * Board.BLOCK_SIZE;
        professor_x = 7 * Board.BLOCK_SIZE;
        professor_dy = 0;
        professor_dx = dx;
        profInGame=true;
        random = (int) (Math.random() * (Board.currentSpeed + 1));

        if (random > Board.currentSpeed) {
            random = Board.currentSpeed;
        }

        professorSpeed = validSpeeds[random];
	}

	public int getXPos() {
		return professor_x;
	}
	
	public int getYPos() {
		return professor_y;
	}

	public void kickProfAway() {
		profInGame=false;
	}

	public boolean getProfInGame() {
		return profInGame;
	}
	
	
	
}
