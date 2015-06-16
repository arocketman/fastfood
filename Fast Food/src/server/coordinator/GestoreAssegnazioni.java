package server.coordinator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import server.entity.Assegnazione;
import server.entity.Posto;

public class GestoreAssegnazioni {
	
	ArrayList<Assegnazione> Assegnazioni;
	
	public GestoreAssegnazioni(){
		this.Assegnazioni = new ArrayList<Assegnazione>();
	}

	public Assegnazione assegnaPosti(ArrayList<Posto> posti) {
		SecureRandom random = new SecureRandom();
		String codiceAlfaNumerico = new BigInteger(130, random).toString(32).substring(0,5);
		Assegnazione assegnazione = new Assegnazione(codiceAlfaNumerico,posti);
		Assegnazioni.add(assegnazione);
		System.out.println("Assegnato nuovo tavolo con posti: " + assegnazione.getCodiceAssegnazionePosti());
		return assegnazione;
	}

}
