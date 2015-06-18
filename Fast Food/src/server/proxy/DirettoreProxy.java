package server.proxy;

import common.rmi.DirettoreProxyInterface;
import server.Server;
import server.coordinator.Controller;
import server.entity.Tavolo;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Andrew on 18/06/2015.
 */
public class DirettoreProxy implements DirettoreProxyInterface {

    @Override
    public ArrayList<Tavolo> visualizzaStatoFastFood() throws RemoteException {
        Controller controller = Server.ic;
        return controller.visualizzaStatoFastFood();
    }

}
