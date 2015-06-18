package client.proxy;

import common.rmi.DirettoreProxyInterface;
import server.entity.Tavolo;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Created by Andrew on 18/06/2015.
 */
public class DirettoreProxy {

    public ArrayList<Tavolo> visualizzaStatoFastFood() {
            try{

                Registry registry = LocateRegistry.getRegistry("localhost");
                DirettoreProxyInterface proxy = (DirettoreProxyInterface) registry.lookup("direttore_proxy");
                return proxy.visualizzaStatoFastFood();

            }catch(RemoteException e){
                e.printStackTrace();
            } catch (NotBoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
    }

}
