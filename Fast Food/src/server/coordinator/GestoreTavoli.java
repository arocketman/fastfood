package server.coordinator;

import java.util.ArrayList;

import server.entity.Posto;
import server.entity.PostoLibero;
import server.entity.Tavolo;

public class GestoreTavoli {

	ArrayList<Tavolo> tavoli;
	
	public GestoreTavoli(){
		tavoli = new ArrayList<Tavolo>();
	}
	
	/*
	public Tavolo richiediTavolo(int numPosti) {
		for(Tavolo tavolo : tavoli){
			int postiLiberi = 0;
			
			for(Posto posto : tavolo.getPosti()){
				if(posto.isLibero())
					postiLiberi++;
			}
			
			if(postiLiberi >= numPosti)
				return tavolo;
		}
		
		//Tavolo non trovato
		return null;
		
	} */
	
	public Tavolo richiediTavolo(int numPosti) {
		for(Tavolo tavolo : tavoli){
			int postiLiberi = 0;
			
			for(Posto posto : tavolo.getPosti()){
				if(posto.isLibero())
					postiLiberi++;
			}
			
			if(postiLiberi >= numPosti){
				ArrayList<Posto> posti = getPostiLiberi(tavolo,numPosti);
				return new Tavolo(tavolo.getNumero(),posti);
			}
		}
		
		//Tavolo non trovato
		return null;
		
	}
	
	/**
	 * Cerca i primi numPosti liberi di un tavolo.
	 * @param tavolo
	 * @param numPosti
	 * @return
	 */
	private ArrayList<Posto> getPostiLiberi(Tavolo tavolo,int numPosti) {
		ArrayList<Posto> postiLiberi = new ArrayList<Posto>();
		
		for(Posto posto : tavolo.getPosti()){
			if(posto.isLibero()){
				postiLiberi.add(posto);
				if(postiLiberi.size() == numPosti)
					return postiLiberi;
			}
		}
		
		return null;
	}

	public void aggiungiTavolo(Tavolo tavolo){
		tavoli.add(tavolo);
	}

}
