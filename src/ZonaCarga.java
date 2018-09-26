import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class ZonaCarga {
	
	Reponedor reponedor;
	final CyclicBarrier barrier;
	
	DepositoGasoleo depPetr[];
	DepositoAgua depAgua;
	
	
	
	public ZonaCarga(){
		//semaforos de llamar y bloquear al reponedor
		CountDownLatch SemReponedor= new CountDownLatch(5);
		CountDownLatch ReponedorFin= new CountDownLatch(1);
		//creacion y lanzamiento del Reponedor
		reponedor= new Reponedor(SemReponedor, ReponedorFin);
		Thread t= new Thread(reponedor);
		t.start();
		
		//creacion de los Depositos de Gasoleo
		depPetr= new DepositoGasoleo[5];
		for(int i=0; i<5; i++){
			depPetr[i]=new DepositoGasoleo(SemReponedor, ReponedorFin);
			reponedor.addDeposito(depPetr[i]); //metemos este Deposito en la lista del Reponedor.
		}
		
		//creacion del deposito de Agua
		depAgua= new DepositoAgua();
		
		//creacion de CyclicBarrier para esperar a que esten los cinco barcos en ZonaCarga
		barrier=new CyclicBarrier(5);
		
	}
	
	
	


	public void llego(Barco b){
		Thread t;
		System.out.println("--Soy el barco"+ b.getID()+ "y llego a la zona de carga.");
		
		try {
			barrier.await(); //esperamos a que lleguen los 5...
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		//a)carga los 3.000 litros de gasoleo
			depPetr[b.getNumDep()].setBarco(b);
			t=new Thread(depPetr[b.getNumDep()]);
			t.start();
		
		//b)carga 5.000 litros de agua		
			depAgua.cogerAgua(b);
		
		System.out.println("--Soy el barco"+ b.getID()+ "y salgo de la zona de carga.");

		
		
		
		
		
	}
	

}
