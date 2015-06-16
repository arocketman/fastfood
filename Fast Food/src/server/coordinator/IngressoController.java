package server.coordinator;

import server.Server;
import server.entity.Assegnazione;
import server.entity.Tavolo;

public class IngressoController {

	public GestoreTavoli gestoreTavoli;
	public GestoreAssegnazioni gestoreAssegnazioni;
	public GestorePrenotazioni gestorePrenotazioni;
	
	public IngressoController() {
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

}
