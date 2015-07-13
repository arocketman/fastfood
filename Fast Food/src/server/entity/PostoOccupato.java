package server.entity;

import server.Server;

public class PostoOccupato extends State{

	public PostoOccupato(Posto p) {
		super(p);
	}
	
	public PostoOccupato(){
		
	}

	@Override
	public void assegnaPosto(Assegnazione assegnazione) {
		Server.log("Non posso assegnare un posto occupato.");
		
	}

	@Override
	public void rilasciaPosto() {
		Server.log("Rilascio il posto: " + this.posto.getCodice());
		posto.setStato(posto.polib);
		posto.rimuoviAssegnazione();
		
	}

	@Override
	public void confermaOccupazione() {
		Server.log("Posto gia' occupato.");
		
	}

}
