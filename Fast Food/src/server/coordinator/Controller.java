package server.coordinator;

import server.entity.Assegnazione;
import server.entity.Prenotazione;
import server.entity.Tavolo;

import java.util.ArrayList;

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
		return gestoreAssegnazioni.assegnaPosti(tavolo.getPosti(),this.gestoreTavoli);
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
		gestoreTavoli.liberaPosto(codicePosto,numeroTavolo);

		//Verifico se sono presenti prenotazioni e le gestisco.
		for(Prenotazione p : gestorePrenotazioni.prenotazioni){
			Assegnazione assegnazione = richiediTavolo(p.getNumeroPosti());
			if(assegnazione != null){
				assegnazione.setPrenotazione(p);
				assegnazione.update();
				//Tavolo trovato.
				String messaggio = "Gentile signore/a " + p.getCognome() +
						" , le e' appena stato assegnato il tavolo : " + assegnazione.getNumeroTavolo() +
						" Codice prenotazione : " + p.getCodice() +
						" , Codice assegnazione " + assegnazione.getCodiceAssegnazionePosti() +
						" , Posti : " + assegnazione.getCodiciPosti();
				inviaSMS(p.getTelefono(),messaggio);
			}
		}

		//Ritorno vero perchè il posto è stato effettivamente liberato.
		return true;
	}

	private void inviaSMS(String telefono, String messaggio) {
		System.out.println("invio sms a : " + telefono);
		System.out.println(messaggio);
	}

	public ArrayList<Tavolo> visualizzaStatoFastFood() {
		return gestoreTavoli.visualizzaStatoFastFood();
	}
}
