package Spiel;

import java.util.LinkedList;

public class KaffeeKocher extends Thread {
	
	private LinkedList<Integer> leereFelder=new LinkedList<Integer>();
	private Spielfeld feld;
	
	public KaffeeKocher(Spielfeld feld){
		this.feld=feld;
	}
	
	public void resetOfDeath(){
		this.interrupt();
		feld.kaffeeWirdKalt();
		this.start();
	}
	
	public void resetOfSuccess(){
		this.interrupt();
		leereFelder=new LinkedList<Integer>();
		feld.kaffeeWirdKalt();
		this.start();
	}
	
	public void run(){
		try{
			while(true){
				wait(5000);
				feld.kaffeeWirdKalt();
				wait(25000);
				if(!leereFelder.isEmpty()){
					synchronized(leereFelder){
						feld.kaffeeIstFertig((int)leereFelder.get((int) (Math.random()*(leereFelder.size()-1))));
					}
				}
			}
		}catch(InterruptedException e){
		}
	}
	
	public void feldWurdeLeer(int feldNummer){
		leereFelder.add(feldNummer);
	}
	
	/*ToDo:
	 * Das Spielfeld muss den KaffeeKocher erstellen, starten, ihn bei jedem Tod und jedem geschafften Level das Ereignis
	 * mit den Methoden resetOfDeath bzw. resetOfSuccess mitteilen, genauso wie jedes einzelnes freigewordernes Feld, wobei 
	 * ein Feld erst frei wird, sobald Pacman es verlässt. Es kann also kein Kaffee unter oder auf Pacman entstehen.
	 * 
	 * Das Spielfeld muss die Methode kaffeeWirdKalt(), die den Kaffee auf dem Spielfeld verschwinden lässt, wenn einer da ist, implementieren,
	 * sowie eine Methode kaffeeIstFertig(int feldIndex), die auf das Feld mit diesem Index einen Kaffee erscheinen lässt. Auch der Super-Student 
	 * muss impementiert werden.*/
	
	/*Ideen:
	 * Das Spielfeld bekommt ein Feld vom Typ int, welches den Index des aktuellen Feldes mit Kaffee markiert. Der default-Wert ist -1, was 
	 * bedeutet, dass momentan kein Kaffee auf dem Feld ist. In jedem Frame wird mit diesem int abgefragt, ob und wo Kaffee auf dem Feld ist, um
	 * ihn gegebenenfalls zu animieren. Kommt der Student auf ein Feld, wird damit ebenfalls abgefragt, ob Kaffee auf dem Feld ist. Wenn ja, 
	 * verwandelt sich der Student und der int wird auf -1 gesetzt.
	 * 
	 * Um den Super-Studenten zu verwirklichen, verwenden wir einen superZustand boolean im Studenten mit dem default-Wert false, der true wird, 
	 * wenn der Student auf ein Kaffee-Feld kommt. Dieser wird bei der drawStudent-Methode abgefragt, damit der Super-Student andere Bilder 
	 * bekommt, als der normale. Ebenso wird dieser abgefragt, wenn eine Kollision des Studenten mit einem Professor festgestellt ist. Um diesen 
	 * nach 10s zurückzusetzen , wird eine eigene kleine Klasse nach dem Vorbild des KaffeeKochers verwendet.
	 * 
	 * Um das Wegfegen eines Professors zu realisieren, können wir diesen entweder löschen, was aber offenbar probleme bereitet hat, oder wir 
	 * setzten einen weggefegt boolean auf true (default:false), der bei Kollisionen und Animationen abgefragt wird und nach einem Erfolg 
	 * zurückgesetzt werden muss.*/
	

}
