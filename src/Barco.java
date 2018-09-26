import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

public class Barco implements Runnable {
	static Plataforma plataforma;
	static Puerta puerta = null;
	private Control control= null;
	private ZonaCarga carga= null;
	
	private int direccion;	// 0 = Entrada | 1 = Salida
	private int id;	// Identificador
	boolean petrolero;
	int depositoP;
	int depositoA;
	int numDep;
	boolean mercante;
	
	public Barco(Puerta p, int direccion, int id, Control c, ZonaCarga carga, int petrolero, int numDep, boolean mercante, Plataforma plat){
		puerta=p;
		this.direccion=direccion;
		this.id=id;
		this.control=c;
		this.carga=carga;
		this.numDep=numDep;
		
		this.petrolero=false;
		if(petrolero==0){
			this.petrolero=true;
		}
		
		this.mercante=mercante;
		this.plataforma=plat;
		
		this.depositoP=0;
		this.depositoA=0;
	}
	
	public int getID(){
		return id;
	}
	
	public int getNumDep(){
		return numDep;
	}
	
	public void cargarPetr(int cantidad){
		depositoP=depositoP+cantidad;
	}
	
	public int getCantidad(){
		return depositoP;
	}
	
	public void cargarAgua(int cantidad){
		depositoA=depositoA+cantidad;
	}
	
	public void poner(){
		try{
			System.out.println("			El barco "+id+" entra de la Plataforma de Descarga.");
			for(int i=0; i<5; i++){
				plataforma.poner(3,id); //5 de sal
			}
			for(int i=0; i<5; i++){
				plataforma.poner(1,id); //5 de azucar
			}
			for(int i=0; i<5; i++){
				plataforma.poner(2,id); //5 de harina
			}
			for(int i=0; i<5; i++){
				plataforma.poner(3,id); //5 de sal
			}
			for(int i=0; i<5; i++){
				plataforma.poner(1,id); //5 de azucar
			}
			for(int i=0; i<5; i++){
				plataforma.poner(3,id); //5 de sal
			}
			for(int i=0; i<2; i++){
				plataforma.poner(1,id); //2 de azucar
			}
			for(int i=0; i<5; i++){
				plataforma.poner(3,id); //5 de sal
			}
			System.out.println("			El barco "+id+" sale de la Plataforma de Descarga.");
		}catch(InterruptedException e){e.printStackTrace();}
	}
	
	public void run(){
		if(direccion==0){
			control.permisoEntrada(this);
			puerta.entrar(this);
			control.finEntrada(this);
			if(petrolero){
				carga.llego(this);
				control.permisoSalida(this);
				puerta.salir(this);
				control.finSalida(this);
			}
			if(mercante){
				this.poner();
				control.permisoSalida(this);
				puerta.salir(this);
				control.finSalida(this);
				
			}
		}
		if(direccion==1){
			control.permisoSalida(this);
			puerta.salir(this);
			control.finSalida(this);
		}
	}
	
	/**
     * Comparando dos objetos.
     * @param obj El objeto con el que comparar
     * @return true, si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj){ //Todas las clases en java heredan de la clase Objeto
    	// Para optimizar, comparamos si las referencias de los dos objetos son iguales.
    	//En este caso, los objetos son iguales siempre
    	if (this == obj) 
    		return true;
    	// Siempre debemos comparar si el objeto pasado por parametro es del mismo tipo.
    	if (!(obj instanceof Barco))
    		return false;
    	// Hacemos un casting... 
    	Barco pAux = (Barco) obj;
    	Integer c= id;
    	Integer c2=pAux.getID();
    	return (c.equals(c2)); 	//Una vez sabemos que son de la misma clase,
    													//comparamos segun los parametros que nosotros
    													//queremos comparar y devolveremos true si los 
    													//valores de todas las parejas de parametros son
    													//iguales y false si no lo son.
    }
}
