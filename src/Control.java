import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Control {
	
	int entrando;
	int saliendo;
	boolean preferencia;
	LinkedList<Barco> bEntrada;
	LinkedList<Barco> bSalida;

	
	Control(){
		entrando=0;
		saliendo=0;
		preferencia=false;
		bEntrada= new LinkedList<Barco>();
		bSalida= new LinkedList<Barco>();
	}
	
	public synchronized void permisoEntrada(Barco b){
		bEntrada.addLast(b);
		System.out.println("..El Barco"+b.getID()+" pide entrada");
		while(saliendo>0 || preferencia || !b.equals(bEntrada.getFirst())){
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		entrando++;
		bEntrada.removeFirst();
		System.out.println("..El Barco"+b.getID()+" obtiene entrada");

	}
	
	public synchronized void permisoSalida(Barco b){
		preferencia=true;
		bSalida.addLast(b);
		System.out.println("..El Barco"+b.getID()+" pide salida");
		while(entrando>0 || !b.equals(bSalida.getFirst())){
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		saliendo++;	
		bSalida.removeFirst();
		System.out.println("..El Barco"+b.getID()+" obtiene salida");

	}
	
	public synchronized void finEntrada(Barco b){
		entrando--;
		if(entrando==0)
			notifyAll();
	}
	
	public synchronized void finSalida(Barco b){
		saliendo--;
		if (saliendo==0){
			notifyAll();
			preferencia=false;
		}
		
	}
	
}
