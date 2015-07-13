package server.coordinator;

import server.Server;
import server.database.AssegnazioneDBWrapper;
import server.database.PrenotazioneDBWrapper;
import server.entity.Prenotazione;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.ListIterator;

public class GestorePrenotazioni {

	ArrayList<Prenotazione> prenotazioni = new ArrayList<>();
	
	public boolean inserisciPrenotazione(String cognome, String telefono,int numPosti) {
		SecureRandom random = new SecureRandom();
		String codiceAlfaNumerico = new BigInteger(130, random).toString(32).substring(0,5);
		Prenotazione prenotazione = new Prenotazione(codiceAlfaNumerico,cognome,telefono,numPosti);
		prenotazioni.add(prenotazione);
		ListIterator listIteratorPrenotazioni=prenotazioni.listIterator(prenotazioni.indexOf(prenotazione));
		Prenotazione prenotazionePrecedente=null;
		Server.log("Inserita Prenotazione: "+prenotazione.getCodice());
		if(listIteratorPrenotazioni.hasPrevious()){
			prenotazionePrecedente=(Prenotazione)listIteratorPrenotazioni.previous();
		}	
		
		prenotazione.setPrenotazionePrecedente(prenotazionePrecedente);
		prenotazione.setPrenotazioneSuccessiva(null);
		return prenotazione.salva() != null;
	}

	public synchronized void assegnaPrenotazione(String codicePrenotazione , String codiceAssegnazione){
		PrenotazioneDBWrapper prenotazioneDBWrapper = new PrenotazioneDBWrapper(codicePrenotazione);
		prenotazioneDBWrapper.setAssegnazione(new AssegnazioneDBWrapper(codiceAssegnazione));
		
		int index=-1;
		for(Prenotazione prenotazione:prenotazioni){
			if(prenotazione.getCodice()==codicePrenotazione){
				index=prenotazioni.indexOf(prenotazione);
			}
		}
		//System.out.println("AssegnazionePrenotazione:"+codicePrenotazione+" position:"+index);
		if(index!=-1){
			ListIterator listIteratorPrenotazioni=prenotazioni.listIterator(index);
			Prenotazione prenotazionePrecedente=null;
			if(listIteratorPrenotazioni.hasPrevious()){
				prenotazionePrecedente=(Prenotazione)listIteratorPrenotazioni.previous();
				listIteratorPrenotazioni.next();
			}
			Prenotazione prenotazioneSuccessiva=null;
			if(listIteratorPrenotazioni.hasNext()){
				listIteratorPrenotazioni.next();
				if(listIteratorPrenotazioni.hasNext()){
					prenotazioneSuccessiva=(Prenotazione)listIteratorPrenotazioni.next();
				}
			}
			if(prenotazionePrecedente!=null){
				prenotazioneDBWrapper.setPrenotazionePrecedente(new PrenotazioneDBWrapper(prenotazionePrecedente.getCodice()));
				//System.out.println("prenotazionePrecedente:"+prenotazionePrecedente.getCodice());
			}
			if(prenotazioneSuccessiva!=null){
				prenotazioneDBWrapper.setPrenotazioneSuccessiva(new PrenotazioneDBWrapper(prenotazioneSuccessiva.getCodice()));		
				//System.out.println("prenotazioneSuccessiva:"+prenotazioneSuccessiva.getCodice());
			}	
		}
		prenotazioneDBWrapper.update();
		prenotazioni.clear();
		
	}

	public synchronized void refreshDB(){
		ArrayList<Prenotazione> buffPrenotazioni = new ArrayList<>();
		//Aggiorno il db.
		for(PrenotazioneDBWrapper prenotazioneDBWrapper : PrenotazioneDBWrapper.findAll()){
			Prenotazione prenotazione = new Prenotazione(prenotazioneDBWrapper.getCodice(),prenotazioneDBWrapper.getCognome(),prenotazioneDBWrapper.getTelefono(),prenotazioneDBWrapper.getNumeroPosti());
			Server.log("Refresh della prenotazione : " + prenotazioneDBWrapper.getCodice());
			if(prenotazioneDBWrapper.getPrenotazionePrecedente()!=null){
				prenotazione.setPrenotazionePrecedente(new Prenotazione(prenotazioneDBWrapper.getPrenotazionePrecedente().getCodice()));
				Server.log("Refresh prenotazioni in corso, prenotazionePrecedente: " + prenotazione.getPrenotazionePrecedente().getCodice());
			}
			if(prenotazioneDBWrapper.getPrenotazioneSuccessiva()!=null){
				prenotazione.setPrenotazioneSuccessiva(new Prenotazione(prenotazioneDBWrapper.getPrenotazioneSuccessiva().getCodice()));
				Server.log("refresh prenotazioneSuccessiva: " + prenotazione.getPrenotazioneSuccessiva().getCodice());
			}
			
			//Prendo solo le prenotazioni non assegnate.
			if(prenotazioneDBWrapper.getAssegnazione() == null){
				buffPrenotazioni.add(prenotazione);
			}
				
		}
		if(buffPrenotazioni.size()>0){
			//A questo punto devo ordinare prenotazioni
			Prenotazione primaPrenotazione = null;
			Prenotazione prenot = null;
			for(Prenotazione pren : buffPrenotazioni){
				if(pren.getPrenotazionePrecedente()==null){
					primaPrenotazione=pren;
				}
			}
			prenotazioni.add(primaPrenotazione);
			Server.log("Riordino Aggiunto:"+primaPrenotazione.getCodice());
			prenot=primaPrenotazione;
			while(prenot.getPrenotazioneSuccessiva()!=null){
				Server.log("Riordino:" + prenot.getCodice());
				prenot=prenot.getPrenotazioneSuccessiva();
				prenotazioni.add(prenot);
				Server.log("Riordino Aggiunto:" + prenot.getCodice());
			}
		}
		
	}

	public boolean occupaPostoCodPrenotazione(String codicePrenotazione, String codiceAssegnazione) {
		PrenotazioneDBWrapper dbWrapper;
		try {
			dbWrapper = new PrenotazioneDBWrapper(codicePrenotazione);
		} catch (RuntimeException e) {
			return false;
		}
		return dbWrapper.getAssegnazione().getCodiceAssegnazionePosti().equalsIgnoreCase(codiceAssegnazione);
	}
}
