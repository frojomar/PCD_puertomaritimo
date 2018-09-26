import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Reponedor implements Runnable{
	
	private CountDownLatch  bloqueo;
	private CountDownLatch  desbloqueoDep;
	private List<DepositoGasoleo> depositos= new ArrayList<DepositoGasoleo>();
	
	public Reponedor(CountDownLatch  bloqueo, CountDownLatch  desbloqueoDep){
		this.bloqueo=bloqueo;
		this.desbloqueoDep= desbloqueoDep;
	}
	
	public void addDeposito(DepositoGasoleo dep){
		depositos.add(dep);
	}
	
	public void run(){
		for(;;){
			
			try {
				bloqueo.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} //se bloquea al reponedor a la espera de que se le llame
			
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			
			
			for(int i=0; i<depositos.size(); i++){
				depositos.get(i).rellenar();
			}
			
			System.out.println("###################################################################");
			//desbloqueamos al barco ultimo en terminar de rellenar
			desbloqueoDep.countDown();
			
			bloqueo= new CountDownLatch(5);
			desbloqueoDep= new CountDownLatch(1);
			
			for(int i=0; i<depositos.size(); i++){
				depositos.get(i).resetearCountDown(bloqueo, desbloqueoDep);
			}

		}
		
		
		
	}
}
