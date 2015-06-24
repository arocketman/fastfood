package server.entity;

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
		System.out.println("Posto gia' libero!");
	}

	@Override
	public void confermaOccupazione() {
		System.out.println("Posto non ancora assegnato!");
		
	}

}
