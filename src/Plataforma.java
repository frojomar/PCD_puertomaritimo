import java.util.concurrent.Exchanger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Plataforma {
	
	private Exchanger<Integer> exchangerAzucar;
	private Exchanger<Integer> exchangerSal;
	private Exchanger<Integer> exchangerHarina;
	
/*	final Lock mutex = new ReentrantLock();      // E.M. para actualizar vbles.
	final Condition lleno= mutex.newCondition(); //condicion de que no se puede poner por parte del barco al haber ya un contenedor.
	final Condition sinContenedor[]={mutex.newCondition(),mutex.newCondition(), mutex.newCondition()}; //condicion de que las gruas no pueden coger porque no hay contenedor en la plataforma.
	
	boolean ocupado; //indica si hay contenedor puesto o no en la plataforma.
	int tipoContenedor; //indica que tipo de contenedor se ha almacenado.
	
	public Plataforma(){
		ocupado=false;
	}
	
	public void cogerHarina() throws InterruptedException{
		mutex.lock();
		try{
			
			while(!ocupado || (ocupado && tipoContenedor!=2)){
				sinContenedor[1].await();
			}
			//cogemos...
			System.out.println("													La grua 2 coge la Harina");
			System.out.println("														La grua 2 coge la Harina");
			System.out.println("															La grua 2 coge la Harina");
			
			ocupado=false; //indicamos que ya no hay contenedor en la plataforma.
			lleno.signal(); //despertamos al barco para que ponga
			
		}finally{mutex.unlock();}
	}

	public void cogerAzucar() throws InterruptedException{
		mutex.lock();
		try{
			
			while(!ocupado || (ocupado && tipoContenedor!=1)){
				sinContenedor[0].await();
			}
			//cogemos...
			System.out.println("													La grua 1 coge la Azucar");
			System.out.println("														La grua 1 coge la Azucar");
			System.out.println("															La grua 1 coge la Azucar");
				
			ocupado=false; //indicamos que ya no hay contenedor en la plataforma.
			lleno.signal(); //despertamos al barco para que ponga

			
		}finally{mutex.unlock();}
	}
	
	public void cogerSal() throws InterruptedException{
		mutex.lock();
		try{
			
			while(!ocupado || (ocupado && tipoContenedor!=3)){
				sinContenedor[2].await();
			}
			//cogemos...
			System.out.println("													La grua 3 coge la Sal");
			System.out.println("														La grua 3 coge la Sal");
			System.out.println("															La grua 3 coge la Sal");
			
			ocupado=false; //indicamos que ya no hay contenedor en la plataforma.
			lleno.signal(); //despertamos al barco para que ponga
			
		}finally{mutex.unlock();}
	}
	
	public void poner(int tipo) throws InterruptedException{
		mutex.lock();
		try{
			while(ocupado){
				lleno.await();
			}
			ocupado=true; //indicamos que ya hay contenedor
			tipoContenedor=tipo; //actualizamos el tipo
			sinContenedor[tipo-1].signal(); //despertamos a la grua apropiada.
			
		}finally{mutex.unlock();}
	}
	
*/
	
	Plataforma(){
		exchangerAzucar= new Exchanger<Integer>();
		exchangerSal= new Exchanger<Integer>();
		exchangerHarina= new Exchanger<Integer>();
	}

	public void cogerHarina() throws InterruptedException{
		System.out.println("					La grua de Harina esta esperando");
		exchangerHarina.exchange(0);
		System.out.println("					La grua de Harina ha cogido un contenedor");
	}

	public void cogerAzucar() throws InterruptedException{
		System.out.println("					La grua de Azucar esta esperando");
		exchangerAzucar.exchange(0);
		System.out.println("					La grua de Azucar ha cogido un contenedor");
	}
	
	public void cogerSal() throws InterruptedException{
		System.out.println("					La grua de Sal esta esperando");
		exchangerSal.exchange(0);
		System.out.println("					La grua de Sal ha cogido un contenedor");

	}
	
	public void poner(int tipo, int id) throws InterruptedException{
		
		switch(tipo){
		case 1:
			System.out.println("					El barco "+id+" espera para dar un contenedor Azucar");
			exchangerAzucar.exchange(1);
			System.out.println("					El barco "+id+" ha dado un contenedor Azucar");
			break;
		case 2:
			System.out.println("					El barco "+id+" espera para dar un contenedor Harina");
			exchangerHarina.exchange(1);
			System.out.println("					El barco "+id+" ha dado un contenedor Harina");
			break;
		case 3:
			System.out.println("					El barco "+id+" espera para dar un contenedor Sal");
			exchangerSal.exchange(1);
			System.out.println("					El barco "+id+" ha dado un contenedor Sal");
			break;
		}
	}
	
}
