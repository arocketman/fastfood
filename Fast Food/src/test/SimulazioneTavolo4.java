package test;

import clientPosto.boundary.PostoBoundary;

public class SimulazioneTavolo4 {

	public static void main(String[] args) {
		Thread posto1 = new Thread(new ThreadTavolo4("OOOO",4));
		Thread posto2 = new Thread(new ThreadTavolo4("PPPP",4));

		posto1.start();
		posto2.start();

	}
	
}

class ThreadTavolo4 implements Runnable{
	
	String codicePosto;
	int numeroTavolo;
	
	public ThreadTavolo4(String codicePosto , int numeroTavolo){
		this.codicePosto = codicePosto;
		this.numeroTavolo = numeroTavolo;
	}
	
	@Override
	public void run() {
		PostoBoundary.main(new String[]{codicePosto,String.valueOf(numeroTavolo)});
	}
	
}
