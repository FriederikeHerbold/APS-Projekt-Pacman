import java.util.LinkedList;

public class Coffee {
	
	private int posX, posY;
	private boolean isHot=false;
	private static CoffeeMachine machine;
	public LinkedList<Integer> emptyFields=new LinkedList<Integer>();
	
	public Coffee(){
		machine=new CoffeeMachine(this);
	}
	
	public void coffeeBecomesCold() {
		isHot=false;
	}

	public void coffeeIsReady(int feldIndex) {
		isHot=true;
		posX=(feldIndex%Board.NUMBER_OF_BLOCKS)*Board.BLOCK_SIZE;
		posY=(feldIndex/Board.NUMBER_OF_BLOCKS)*Board.BLOCK_SIZE;
	}
	
	public boolean getIsHot(){
		return isHot;
	}
	
	public int getXPos(){
		return posX;
	}

	public int getYPos(){
		return posY;
	}
	
	public synchronized void resetOfDeathOrDrinkOrEndOfSuper(){
		machine.stop();
		this.coffeeBecomesCold();
		machine=new CoffeeMachine(this);
		machine.start();
	}
	
	public synchronized void resetOfSuccess(){
		machine.stop();
		emptyFields=new LinkedList<Integer>();
		this.coffeeBecomesCold();
		machine=new CoffeeMachine(this);
		machine.start();
	}
	
	public void studentIsOnField(int feldNummer){
		if(emptyFields.contains(new Integer(feldNummer))){
			emptyFields.remove(new Integer(feldNummer));
			emptyFields.addLast(new Integer(feldNummer));
		}else{
			emptyFields.addLast(new Integer(feldNummer));
		}
	}
	
}
