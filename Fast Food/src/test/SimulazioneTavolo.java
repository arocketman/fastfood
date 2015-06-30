package test;

import clientPosto.boundary.PostoBoundary;

public class SimulazioneTavolo {

	public static void main(String[] args) {
		Thread posto1 = new Thread(new ThreadPosto("AAAA",1));
		Thread posto2 = new Thread(new ThreadPosto("BBBB",1));
		Thread posto3 = new Thread(new ThreadPosto("CCCC",1));
		Thread posto4 = new Thread(new ThreadPosto("DDDD",1));
		posto1.start();
		posto2.start();
		posto3.start();
		posto4.start();
	}
	
}

class ThreadPosto implements Runnable{
	
	String codicePosto;
	int numeroTavolo;
	
	public ThreadPosto(String codicePosto , int numeroTavolo){
		this.codicePosto = codicePosto;
		this.numeroTavolo = numeroTavolo;
	}
	
	@Override
	public void run() {
		PostoBoundary.main(new String[]{codicePosto,String.valueOf(numeroTavolo)});
	}
	
}
