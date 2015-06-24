package server.entity;

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
		System.out.println("Rilascio il posto.");
		posto.setStato(posto.polib);
		
	}

	@Override
	public void confermaOccupazione() {
		System.out.println("Occupazione confermata.");
		posto.setStato(posto.poocc);
		
	}

}
