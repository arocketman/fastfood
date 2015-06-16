package server.entity;

import java.io.Serializable;

public class PostoLibero extends State implements Serializable{

	public PostoLibero(Posto p) {
		super(p);
	}
	
	public PostoLibero(){
		
	}

	@Override
	public void assegnaPosto(Assegnazione assegnazione) {
		posto.setAssegnazione(assegnazione);
		posto.setStato(posto.poass);
		
	}

	@Override
	public void rilasciaPosto() {
		System.out.println("Posto gia' libero!");
	}

	@Override
	public void confermaOccupazione() {
		System.out.println("Posto non ancora assegnato!");
		
	}

}
