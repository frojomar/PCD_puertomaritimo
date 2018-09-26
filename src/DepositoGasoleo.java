import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class DepositoGasoleo implements Runnable{
	
	static Semaphore terminados= new Semaphore(0, true);
	static int numTerminados;
	static Semaphore  mutex= new Semaphore(1, true);
	static CountDownLatch  SemReponedor;
	static CountDownLatch  ReponedorFin;
	
	private Barco barco=null;
	private int capacidad;
	
	public DepositoGasoleo(CountDownLatch  SemReponedor, CountDownLatch  ReponedorFin){
		capacidad=1000;
		numTerminados=0;
		this.SemReponedor=SemReponedor;
		this.ReponedorFin=ReponedorFin;
	}
	
	public void setBarco(Barco b){
		barco=b;
	}
	
	public void rellenar(){
		capacidad=1000;
	}
	
	public void resetearCountDown(CountDownLatch  SemReponedor, CountDownLatch  ReponedorFin){
		this.SemReponedor=SemReponedor;
		this.ReponedorFin=ReponedorFin;
	}
	
	/*public void finReponedor(){
		ReponedorFin.release();
	}*/
	
	public void run(){
		System.out.println("--Soy el barco"+ barco.getID()+ "y LLEGO a coger petroleo.");

		while(barco.getCantidad()<3000){
			if(capacidad>=250){
				System.out.println("--Soy el barco"+ barco.getID()+ "y cojo petroleo.");
				barco.cargarPetr(250);
				capacidad=capacidad-250;
			}
			else{/*
				try {
					mutex.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				numTerminados++;
				mutex.release();
				if(numTerminados!=5){// no han terminado todos
					System.out.println("--Soy el barco"+ barco.getID()+ "y se ha acabado el petroleo.");
					try {
						terminados.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{//el ultimo en terminar..
					System.out.println("--Soy el barco"+ barco.getID()+ "y relleno el petroleo.");
					SemReponedor.release();//depierta al reponedor y..
					try {
						ReponedorFin.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//este ultimo se bloquea a esperar a que se repongan los depositos.
					System.out.println("--Soy el barco"+ barco.getID()+ "y reseteo el numero de Terminados.");
					try {
						mutex.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					numTerminados=0;
					mutex.release();
					System.out.println("--Soy el barco"+ barco.getID()+ "y llamo a los otros.");
					for(int j=0; j<4; j++)//despierta a los otros 4.
						terminados.release();

				}*/
				System.out.println("--Soy el barco"+ barco.getID()+ "y se ha acabado el petroleo.");

				SemReponedor.countDown(); //decrementa para llamar al Reponedor (cuando han decrementado los 5)..
				try {
					ReponedorFin.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} //y se bloquea a la espera de que el reponedor rellene y lo desbloquea a el y a los otros barcos.
				
			}
			
		}
		
		System.out.println("--Soy el barco"+ barco.getID()+ "y SALGO de coger petroleo.");

	}
}
