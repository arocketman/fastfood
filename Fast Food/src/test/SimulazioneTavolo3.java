package test;

import clientPosto.boundary.PostoBoundary;

public class SimulazioneTavolo3 {

	public static void main(String[] args) {
		Thread posto1 = new Thread(new ThreadTavolo3("IIII",3));
		Thread posto2 = new Thread(new ThreadTavolo3("LLLL",3));
		Thread posto3 = new Thread(new ThreadTavolo3("MMMM",3));
		Thread posto4 = new Thread(new ThreadTavolo3("NNNN",3));
		posto1.start();
		posto2.start();
		posto3.start();
		posto4.start();
	}
	
}

class ThreadTavolo3 implements Runnable{
	
	String codicePosto;
	int numeroTavolo;
	
	public ThreadTavolo3(String codicePosto , int numeroTavolo){
		this.codicePosto = codicePosto;
		this.numeroTavolo = numeroTavolo;
	}
	
	@Override
	public void run() {
		PostoBoundary.main(new String[]{codicePosto,String.valueOf(numeroTavolo)});
	}
	
}
