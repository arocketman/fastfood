package client.business_logic;

import client.proxy.PostoProxy;

public class PostoBusinessLogic {
	
	public String occupaPosto(String codicePosto, int numeroTavolo , String codiceAssegnazione){
		PostoProxy proxy = new PostoProxy();
		return proxy.occupaPosto(codicePosto, numeroTavolo, codiceAssegnazione);
		
	}

	public boolean liberaPosto(String codicePosto, int numeroTavolo) {
		PostoProxy proxy = new PostoProxy();
		return proxy.liberaPosto(codicePosto,numeroTavolo);
	}
}
