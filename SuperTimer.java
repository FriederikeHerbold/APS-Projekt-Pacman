package Spiel;

public class SuperTimer extends Thread {

	Student student;
	
	public SuperTimer(Student student){
		this.student=student;
	}
	
	public void run(){
		wait(10000);
		student.normalize();
	}
	
}
