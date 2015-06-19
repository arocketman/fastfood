package server.coordinator;

import server.Server;
import server.entity.Posto;
import server.entity.Tavolo;

import java.util.ArrayList;
import java.util.Date;

public class GestoreTavoli {

	ArrayList<Tavolo> tavoli;
	
	public GestoreTavoli(){
		tavoli = new ArrayList<Tavolo>();
	}
		
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

	public void occupaPosto(String codicePosto, int numeroTavolo) {
		Posto posto = new Posto(codicePosto);
		posto.confermaOccupazione();
		posto.setOccupazione(new Date());
		posto.update();
		updateListaPosti();
	}

	public boolean liberaPosto(String codicePosto, int numeroTavolo) {
		Posto posto = new Posto(codicePosto);
		posto.rilasciaPosto();
		posto.setOccupazione(null);
		posto.setAssegnazione(null);
		posto.update();
		updateListaPosti();
		return true;
	}

	public synchronized void updateListaPosti(){
		this.tavoli.clear();
		for(Tavolo t : Server.loadTavoli())
			aggiungiTavolo(t);
	}

	public ArrayList<Tavolo> visualizzaStatoFastFood() {
		return tavoli;
	}
}
