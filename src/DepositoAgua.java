import java.util.concurrent.Semaphore;

public class DepositoAgua {
	
	private Semaphore mutexAgua= new Semaphore(1, true);
	
	public DepositoAgua(){
		
	}
	
	public void cogerAgua(Barco barco){
		System.out.println("$$$$$$$$ Soy el barco"+ barco.getID()+ "y LLEGO a coger agua.");
		try {
			mutexAgua.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(">>>>>>>>> Soy el barco"+ barco.getID()+ "y cojo agua.");
		System.out.println(">>>>>>>>> Soy el barco"+ barco.getID()+ "y cojo agua.");
		System.out.println(">>>>>>>>> Soy el barco"+ barco.getID()+ "y cojo agua.");
		barco.cargarAgua(5000);
		mutexAgua.release();
		System.out.println("$$$$$$$$ Soy el barco"+ barco.getID()+ "y SALGO de coger agua.");

	}
}
