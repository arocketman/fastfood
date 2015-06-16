package server.coordinator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import server.entity.Prenotazione;

public class GestorePrenotazioni {

	ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
	
	public boolean inserisciPrenotazione(String cognome, String telefono,int numPosti) {
		SecureRandom random = new SecureRandom();
		String codiceAlfaNumerico = new BigInteger(130, random).toString(32).substring(0,5);
		Prenotazione prenotazione = new Prenotazione(codiceAlfaNumerico,cognome,telefono,numPosti);
		prenotazioni.add(prenotazione);
		//return prenotazione.salve()
		return true;
		
	}

	
}
