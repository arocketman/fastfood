package clientIngresso.business_logic;

import clientIngresso.proxy.IngressoProxy;

public class IngressoBusinessLogic {
	public String richiediTavolo(int numTavoli){
		IngressoProxy proxy = new IngressoProxy();
		return proxy.richiediTavolo(numTavoli);
	}
	
	public boolean inserisciPrenotazione(String cognome, String telefono, int numPosti){
		IngressoProxy proxy = new IngressoProxy();
		return proxy.inserisciPrenotazione(cognome, telefono, numPosti);
	}
}
