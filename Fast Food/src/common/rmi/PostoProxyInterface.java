package common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PostoProxyInterface extends Remote {

	public int occupaPosto(String codicePosto, int numeroTavolo , String codiceAssegnazione) throws RemoteException;
	public boolean liberaPosto(String codicePosto, int numeroTavolo) throws RemoteException;


	}
