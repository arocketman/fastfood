package server.proxy;

import java.rmi.RemoteException;

import server.Server;
import server.coordinator.Controller;
import common.rmi.PostoProxyInterface;

public class PostoProxy implements PostoProxyInterface {

	@Override
	public int occupaPosto(String codicePosto, int numeroTavolo,String codiceAssegnazione) throws RemoteException {
		Controller controller = Server.ic;
		return controller.occupaPosto(codicePosto, numeroTavolo, codiceAssegnazione);
	}

}
