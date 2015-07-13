package server.entity;

import server.Server;

public class PostoLibero extends State{

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
		Server.log("Posto gia' libero!");
	}

	@Override
	public void confermaOccupazione() {
		Server.log("Posto non ancora assegnato!");
		
	}

}
