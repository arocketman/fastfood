package server.proxy;

import common.rmi.PostoProxyInterface;
import server.Server;
import server.coordinator.Controller;

import java.rmi.RemoteException;

public class PostoProxy implements PostoProxyInterface {

	@Override
	public String occupaPosto(String codicePosto, int numeroTavolo,String codiceAssegnazione) throws RemoteException {
		Controller controller = Server.ic;
		return controller.occupaPosto(codicePosto, numeroTavolo, codiceAssegnazione);
	}

	@Override
	public boolean liberaPosto(String codicePosto, int numeroTavolo) throws RemoteException {
		Controller controller = Server.ic;
		return controller.liberaPosto(codicePosto,numeroTavolo);
	}

}
