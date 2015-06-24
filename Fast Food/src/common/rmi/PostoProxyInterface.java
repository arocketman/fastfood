package common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PostoProxyInterface extends Remote {

	String occupaPosto(String codicePosto, int numeroTavolo , String codiceAssegnazione) throws RemoteException;
	boolean liberaPosto(String codicePosto, int numeroTavolo) throws RemoteException;


	}
