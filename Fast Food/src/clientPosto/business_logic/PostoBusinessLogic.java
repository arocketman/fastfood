package clientPosto.business_logic;

import clientPosto.proxy.PostoProxy;

public class PostoBusinessLogic {
	
	public String occupaPosto(String codicePosto, int numeroTavolo , String codiceAssegnazione){
		PostoProxy proxy = new PostoProxy();
		return proxy.occupaPosto(codicePosto, numeroTavolo, codiceAssegnazione);
		
	}

	public boolean liberaPosto(String codicePosto, int numeroTavolo) {
		PostoProxy proxy = new PostoProxy();
		return proxy.liberaPosto(codicePosto,numeroTavolo);
	}

	public boolean occupaPostoCodPrenotazione(String codicePrenotazione, String codiceAssegnazione, String codicePosto){
		PostoProxy proxy = new PostoProxy();
		return proxy.occupaPostoCodPrenotazione(codicePrenotazione,codiceAssegnazione,codicePosto);
	}

}
