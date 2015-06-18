package client.business_logic;

import client.proxy.PostoProxy;

public class PostoBusinessLogic {
	
	public int occupaPosto(String codicePosto, int numeroTavolo , String codiceAssegnazione){
		PostoProxy proxy = new PostoProxy();
		return proxy.occupaPosto(codicePosto, numeroTavolo, codiceAssegnazione);
		
	}

}
