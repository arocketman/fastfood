package server.entity;

import server.Server;

public class PostoAssegnato extends State{

	public PostoAssegnato(Posto p) {
		super(p);
	}
	
	public PostoAssegnato(){
		
	}

	@Override
	public void assegnaPosto(Assegnazione assegnazione) {
		System.out.println("Posto gia' assegnato");
		
	}

	@Override
	public void rilasciaPosto() {
		Server.log("Rilascio il posto: " + this.posto.getCodice());
		posto.setStato(posto.polib);
		
	}

	@Override
	public void confermaOccupazione() {
		Server.log("Occupazione confermata.");
		posto.setStato(posto.poocc);
		
	}

}
