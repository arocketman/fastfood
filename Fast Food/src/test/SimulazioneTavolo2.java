package test;

import clientPosto.boundary.PostoBoundary;

public class SimulazioneTavolo2 {

	public static void main(String[] args) {
		Thread posto1 = new Thread(new ThreadTavolo2("EEEE",2));
		Thread posto2 = new Thread(new ThreadTavolo2("FFFF",2));
		Thread posto3 = new Thread(new ThreadTavolo2("GGGG",2));
		Thread posto4 = new Thread(new ThreadTavolo2("HHHH",2));
		posto1.start();
		posto2.start();
		posto3.start();
		posto4.start();
	}
	
}

class ThreadTavolo2 implements Runnable{
	
	String codicePosto;
	int numeroTavolo;
	
	public ThreadTavolo2(String codicePosto , int numeroTavolo){
		this.codicePosto = codicePosto;
		this.numeroTavolo = numeroTavolo;
	}
	
	@Override
	public void run() {
		PostoBoundary.main(new String[]{codicePosto,String.valueOf(numeroTavolo)});
	}
	
}
