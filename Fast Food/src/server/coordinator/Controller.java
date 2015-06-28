package server.coordinator;

import com.google.gson.JsonObject;
import server.Server;
import server.entity.Assegnazione;
import server.entity.Prenotazione;
import server.entity.Tavolo;

import java.util.ArrayList;

public class Controller {

	public GestoreTavoli gestoreTavoli;
	public GestoreAssegnazioni gestoreAssegnazioni;
	public GestorePrenotazioni gestorePrenotazioni;

	public static final String OKAY = "1";
	public static final String ERRORE = "-1";
	public static final String PRENOTAZIONE = "2";
	
	public Controller() {
		this.gestoreTavoli = new GestoreTavoli();
		this.gestoreAssegnazioni = new GestoreAssegnazioni();
		this.gestorePrenotazioni = new GestorePrenotazioni();
	}

	public synchronized Assegnazione richiediTavolo(int numPosti) {
		Tavolo tavolo = gestoreTavoli.richiediTavolo(numPosti);
		if(tavolo == null) {
			Server.log("Non ci sono tavoli disponibili. (Avvia la prenoatzione)");
			return null;
		}
		Assegnazione assegnazione =  gestoreAssegnazioni.assegnaPosti(tavolo.getPosti(),this.gestoreTavoli,this);
		return assegnazione;
	}

	public boolean inserisciPrenotazione(String cognome, String telefono,int numPosti) {
		return gestorePrenotazioni.inserisciPrenotazione(cognome,telefono,numPosti);
	}
	
	public String occupaPosto(String codicePosto, int numeroTavolo,String codiceAssegnazione){
		String risultato = gestoreAssegnazioni.verificaAssegnazione(codicePosto,numeroTavolo,codiceAssegnazione);
		if(risultato.equalsIgnoreCase(OKAY))
			gestoreTavoli.occupaPosto(codicePosto,numeroTavolo);
		
		return risultato;
		
	}

	public boolean liberaPosto(String codicePosto, int numeroTavolo) {
		gestoreTavoli.liberaPosto(codicePosto, numeroTavolo);

		verificaPrenotazioni();

		//Ritorno vero perchè il posto è stato effettivamente liberato.
		return true;
	}

	public synchronized void verificaPrenotazioni(){
		//Verifico se sono presenti prenotazioni e le gestisco.
		ArrayList<Prenotazione> prenotazioni = new ArrayList<>(gestorePrenotazioni.prenotazioni);
		for(Prenotazione p : prenotazioni) {
				Assegnazione assegnazione = richiediTavolo(p.getNumeroPosti());
				if (assegnazione != null) {
					assegnazione.setPrenotazione(p);
					assegnazione.update();
					//Tavolo trovato.
					String messaggio = "Gentile signore/a " + p.getCognome() +
							" , le e' appena stato assegnato il tavolo : " + assegnazione.getNumeroTavolo() +
							" Codice prenotazione : " + p.getCodice() +
							" , Codice assegnazione " + assegnazione.getCodiceAssegnazionePosti() +
							" , Posti : " + assegnazione.getCodiciPosti();
					inviaSMS(p.getTelefono(), messaggio);
					//Eliminare prenotazione da db.
					gestorePrenotazioni.assegnaPrenotazione(p.getCodice(), assegnazione.getCodiceAssegnazionePosti());
					gestorePrenotazioni.refreshDB();
				}
		}
	}

	private void inviaSMS(String telefono, String messaggio) {
		Server.log("invio sms a : " + telefono);
		Server.log(messaggio);
	}

	public ArrayList<JsonObject> visualizzaStatoFastFood() {
		ArrayList<JsonObject> tavoliJson = new ArrayList<>();
		for(Tavolo t : gestoreTavoli.visualizzaStatoFastFood()){
			tavoliJson.add(t.impacchettaPerClient());
		}
		return tavoliJson;
	}
}
