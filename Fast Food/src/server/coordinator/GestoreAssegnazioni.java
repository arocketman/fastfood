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
		TimerCinqueMinuti timer = new TimerCinqueMinuti(assegnazione.getCodiceAssegnazionePosti());
		timer.schedule(new TimerCinqueMinutiTask(assegnazione), 20000);
		
		return assegnazione;
	}
	
	class TimerCinqueMinuti extends Timer{
		String id; 
		
		public TimerCinqueMinuti(String id){
			this.id = id;
		}
	}
	
	class TimerCinqueMinutiTask extends TimerTask{

		Assegnazione assegnazione;
		
		public TimerCinqueMinutiTask(Assegnazione assegnazione){
			this.assegnazione = assegnazione;
		}
		
		@Override
		public void run() {
			for(Posto p : assegnazione.getPosti()){
				p.rilasciaPosto();
				p.setAssegnazione(null);
				p.update();
			}
			System.out.println("cock");
		}
		
	}

}
