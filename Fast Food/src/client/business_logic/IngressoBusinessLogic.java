package client.business_logic;

import server.entity.Assegnazione;
import client.proxy.IngressoProxy;

public class IngressoBusinessLogic {
	public Assegnazione richiediTavolo(int numTavoli){
		IngressoProxy proxy = new IngressoProxy();
		return proxy.richiediTavolo(numTavoli);
	}
	
	public boolean inserisciPrenotazione(String cognome, String telefono, int numPosti){
		IngressoProxy proxy = new IngressoProxy();
		return proxy.inserisciPrenotazione(cognome, telefono, numPosti);
	}
}
