package server.coordinator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import server.entity.Assegnazione;
import server.entity.Posto;

public class GestoreAssegnazioni {
	
	ArrayList<Assegnazione> Assegnazioni;
	ArrayList<TimerCinqueMinuti> timers;
	
	public GestoreAssegnazioni(){
		this.Assegnazioni = new ArrayList<Assegnazione>();
		this.timers = new ArrayList<TimerCinqueMinuti>();
	}

	public Assegnazione assegnaPosti(ArrayList<Posto> posti) {
		SecureRandom random = new SecureRandom();
		String codiceAlfaNumerico = new BigInteger(130, random).toString(32).substring(0,5);
		Assegnazione assegnazione = new Assegnazione(codiceAlfaNumerico,posti);
		Assegnazioni.add(assegnazione);
		System.out.println("Assegnato nuovo tavolo con posti: " + assegnazione.getCodiceAssegnazionePosti());
		
		//Avvio timer all'assegnazione.
		for(Posto p : posti){
			TimerCinqueMinuti timer = new TimerCinqueMinuti(p.getCodice());
			timer.schedule(new TimerCinqueMinutiTask(p), 200000);
			timers.add(timer);
		}
		
		
		return assegnazione;
	}
	
	class TimerCinqueMinuti extends Timer{
		String id; 
		
		public TimerCinqueMinuti(String id){
			this.id = id;
		}
	}
	
	class TimerCinqueMinutiTask extends TimerTask{

		Posto postoAssegnato;
		
		public TimerCinqueMinutiTask(Posto posto){
			this.postoAssegnato = posto;
		}
		
		@Override
		public void run() {
			postoAssegnato.rilasciaPosto();
			postoAssegnato.setAssegnazione(null);
			postoAssegnato.update();
			System.out.println("Rilascio il posto : " + postoAssegnato.getCodice() + " (timer scaduto).");
		}
		
	}

	public int verificaAssegnazione(String codicePosto, int numeroTavolo,String codiceAssegnazione) {
		Assegnazione assegnazione = getAssegnazione(codiceAssegnazione);
		if(assegnazione == null)
			return Controller.ERRORE;
		else{
			//Cerco se l'assegnazione datomi corrisponde con il posto
			if(postoAndAssegnazioneCorrispondono(codicePosto,assegnazione)){
				//A questo punto esiste
				if(assegnazione.getPrenotazione() == null){
						fermaTimer(codicePosto);
						return Controller.OKAY;
				}
				else
						return Controller.PRENOTAZIONE;
			}
			return Controller.ERRORE;
		}
		
	}

	private void fermaTimer(String codicePosto) {
		for(TimerCinqueMinuti timerCinqueMinuti : timers){
			if(timerCinqueMinuti.id.equalsIgnoreCase(codicePosto)){
				System.out.println("Timer cinque minuti per posto assegnato : " + codicePosto + " viene cancellato per occupazione posto");
				timerCinqueMinuti.cancel();
			}
		}
		
	}

	private Assegnazione getAssegnazione(String codiceAssegnazione) {
		for(Assegnazione assegnazione : Assegnazioni){
			if(assegnazione.getCodiceAssegnazionePosti().equalsIgnoreCase(codiceAssegnazione))
				return assegnazione;
		}
		return null;
	}

	/**
	 * Controlla se all'interno dell'assegnazione è presente un posto avente codice == codicePosto
	 * @param codicePosto
	 * @param assegnazione
	 * @return
	 */
	private boolean postoAndAssegnazioneCorrispondono(String codicePosto , Assegnazione assegnazione){
		for(Posto p : assegnazione.getPosti()){
			if(codicePosto.equalsIgnoreCase(p.getCodice()))
				return true;
		}
		return false;
	}
}
