package common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Andrew on 18/06/2015.
 */
public interface DirettoreProxyInterface extends Remote {
    ArrayList<String> visualizzaStatoFastFood() throws RemoteException;
}
