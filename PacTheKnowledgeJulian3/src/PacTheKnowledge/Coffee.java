package PacTheKnowledge;

import java.util.LinkedList;

public class Coffee {
	
	private int posX, posY;
	private boolean isHot=false;
	private static CoffeeMachine machine;
	public LinkedList<Integer> leereFelder=new LinkedList<Integer>();
	
	public Coffee(){
		machine=new CoffeeMachine(this);
	}
	
	public void CoffeeGetsCold() {
		isHot=false;
		System.out.println("gotCold");
	}

	public void CoffeeIsReady(int feldIndex) {
		isHot=true;
		posX=(feldIndex%Board.NUMBER_OF_BLOCKS)*Board.BLOCK_SIZE;
		posY=(feldIndex/Board.NUMBER_OF_BLOCKS)*Board.BLOCK_SIZE;
		System.out.println("Coffee X:" + posX/Board.BLOCK_SIZE);
		System.out.println("Coffee Y:" + posY/Board.BLOCK_SIZE);
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
		this.CoffeeGetsCold();
		machine=new CoffeeMachine(this);
		machine.start();
	}
	
	public synchronized void resetOfSuccess(){
		machine.stop();
		leereFelder=new LinkedList<Integer>();
		this.CoffeeGetsCold();
		machine=new CoffeeMachine(this);
		machine.start();
	}
	
	public void studentIsOnField(int feldNummer){
		if(leereFelder.contains(new Integer(feldNummer))){
			leereFelder.remove(new Integer(feldNummer));
			leereFelder.addLast(new Integer(feldNummer));
		}else{
			leereFelder.addLast(new Integer(feldNummer));
		}
	}
	
}
