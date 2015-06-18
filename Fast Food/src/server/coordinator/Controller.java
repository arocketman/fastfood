package server.coordinator;

import server.entity.Assegnazione;
import server.entity.Tavolo;

public class Controller {

	public GestoreTavoli gestoreTavoli;
	public GestoreAssegnazioni gestoreAssegnazioni;
	public GestorePrenotazioni gestorePrenotazioni;
	
	public static final int OKAY = 1;
	public static final int ERRORE = -1;
	public static final int PRENOTAZIONE = 2;
	
	public Controller() {
		this.gestoreTavoli = new GestoreTavoli();
		this.gestoreAssegnazioni = new GestoreAssegnazioni();
		this.gestorePrenotazioni = new GestorePrenotazioni();
	}

	public synchronized Assegnazione richiediTavolo(int numPosti) {
		Tavolo tavolo = gestoreTavoli.richiediTavolo(numPosti);
		if(tavolo == null) {
			System.out.println("Non ci sono tavoli disponibili. (Avvia la prenoatzione)");
			return null;
		}
		return gestoreAssegnazioni.assegnaPosti(tavolo.getPosti());
	}

	public boolean inserisciPrenotazione(String cognome, String telefono,int numPosti) {
		return gestorePrenotazioni.inserisciPrenotazione(cognome,telefono,numPosti);
	}
	
	public int occupaPosto(String codicePosto, int numeroTavolo,String codiceAssegnazione){
		int risultato = gestoreAssegnazioni.verificaAssegnazione(codicePosto,numeroTavolo,codiceAssegnazione);
		if(risultato == OKAY)
			gestoreTavoli.occupaPosto(codicePosto,numeroTavolo);
		
		return risultato;
		
	}

	public boolean liberaPosto(String codicePosto, int numeroTavolo) {
		return gestoreTavoli.liberaPosto(codicePosto,numeroTavolo);
	}
}
