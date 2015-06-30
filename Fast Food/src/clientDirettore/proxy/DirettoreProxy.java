package clientDirettore.proxy;

import common.rmi.DirettoreProxyInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Proxy con cui interagisce la business logic del clientPosto Direttore.
 */
public class DirettoreProxy {

    public ArrayList<String> visualizzaStatoFastFood() {
            try{

                Registry registry = LocateRegistry.getRegistry("localhost");
                DirettoreProxyInterface proxy = (DirettoreProxyInterface) registry.lookup("direttore_proxy");
                return proxy.visualizzaStatoFastFood();

            }catch(RemoteException e){
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }

            return null;
    }

}
