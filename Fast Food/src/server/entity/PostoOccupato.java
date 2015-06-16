package server.entity;

import java.io.Serializable;

public class PostoOccupato extends State implements Serializable{

	public PostoOccupato(Posto p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	public PostoOccupato(){
		
	}

	@Override
	public void assegnaPosto(Assegnazione assegnazione) {
		System.out.println("Non posso assegnare un posto occupato.");
		
	}

	@Override
	public void rilasciaPosto() {
		System.out.println("Rilascio il posto.");
		posto.setStato(posto.polib);
		posto.rimuoviAssegnazione();
		
	}

	@Override
	public void confermaOccupazione() {
		System.out.println("Posto gia' occupato.");
		
	}

}
