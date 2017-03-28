public class CoffeeMachine extends Thread {
	
	private Coffee coffee;
	
	
	public CoffeeMachine(Coffee coffee){
		this.coffee=coffee;
	}
	
	public void run(){
		while(true){
			try{
			while(true){
				sleep(5000);
				coffee.coffeeBecomesCold();
				sleep(25000);
				if(coffee.emptyFields.size()>1){
					synchronized(coffee.emptyFields){
						coffee.coffeeIsReady((int)coffee.emptyFields.get((int) (Math.random()*(coffee.emptyFields.size()-2))));
					}
				}
			}
		}catch(InterruptedException e){
			System.out.println("Interrupted");
		}
		}
	}
}
