public class SuperTimer extends Thread {

	Student student;
	
	public SuperTimer(Student student){
		this.student=student;
	}
	
	public void run(){
		try {
			sleep(10000);
			student.normalize();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}