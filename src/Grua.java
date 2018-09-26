
public class Grua implements Runnable {
	
	static Plataforma zonaDescarga= new Plataforma();
	
	private int id;//1-Azucar, 2-Harina, 3-Sal
	
	public Grua(int id, Plataforma plat){
		this.id=id;
		this.zonaDescarga= plat;
	}
	
	public void run(){
		for(;;){
			try{
				switch(id){
					case 1:
						zonaDescarga.cogerAzucar();
						break;
					case 2:
						zonaDescarga.cogerHarina();
						break;
					case 3:
						zonaDescarga.cogerSal();
			}
			}catch(InterruptedException e){e.printStackTrace();}
		}
	}
}
