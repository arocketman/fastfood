package server.entity;

import java.io.Serializable;

public class PostoAssegnato extends State implements Serializable{

	private static final long serialVersionUID = -7158390074287652470L;

	public PostoAssegnato(Posto p) {
		super(p);
		// TODO Auto-generated constructor stub
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
