package server.coordinator;

import server.database.AssegnazioneDBWrapper;
import server.database.PrenotazioneDBWrapper;
import server.entity.Prenotazione;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class GestorePrenotazioni {

	ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
	
	public boolean inserisciPrenotazione(String cognome, String telefono,int numPosti) {
		SecureRandom random = new SecureRandom();
		String codiceAlfaNumerico = new BigInteger(130, random).toString(32).substring(0,5);
		Prenotazione prenotazione = new Prenotazione(codiceAlfaNumerico,cognome,telefono,numPosti);
		prenotazioni.add(prenotazione);
		return prenotazione.salva() != null;
	}

	public synchronized void assegnaPrenotazione(String codicePrenotazione , String codiceAssegnazione){
		PrenotazioneDBWrapper prenotazioneDBWrapper = new PrenotazioneDBWrapper(codicePrenotazione);
		prenotazioneDBWrapper.setAssegnazione(new AssegnazioneDBWrapper(codiceAssegnazione));
		prenotazioneDBWrapper.update();
		prenotazioni.clear();
	}

	public synchronized void refreshDB(){
		//Aggiorno il db.
		for(PrenotazioneDBWrapper prenotazioneDBWrapper : PrenotazioneDBWrapper.findAll()){
			Prenotazione prenotazione = new Prenotazione(prenotazioneDBWrapper.getCodice(),prenotazioneDBWrapper.getCognome(),prenotazioneDBWrapper.getTelefono(),prenotazioneDBWrapper.getNumeroPosti());
			//Prendo solo le prenotazioni non assegnate.
			if(prenotazioneDBWrapper.getAssegnazione() == null)
				prenotazioni.add(prenotazione);
		}

	}

	
}
