import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

	public Main(){
	
	}
	

	public static void main (String args[]){
		ThreadPoolExecutor e= (ThreadPoolExecutor)Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		Puerta p = new Puerta();
		Control c = new Control();
		ZonaCarga z= new ZonaCarga();
		Plataforma plat= new Plataforma();
		
		
		for(int i=0; i<3; i++){
			Thread t= new Thread(new Grua(i+1, plat));
			t.start();
		}
		for(int i=0; i<20; i++){ //entran
			if(i==6){
				e.execute(new Barco(p, i%2, i, c, z, i%4, i/4, true, plat));
			}
			else{
				e.execute(new Barco(p, i%2, i, c, z, i%4, i/4, false, plat));
			}
		}
		e.shutdown();
	}
}

//Restricciones:
/*
	1.Esperar a que esten los cinco barcos para coger petroleo (cogen 250).
	2.Cuando hemos vaciado el repositorio despertamos al reponedor.
	3.Cuando esos 5 barcos cogen petroleo, tambien salen.
*/