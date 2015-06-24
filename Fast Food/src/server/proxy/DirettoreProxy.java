package server.proxy;

import com.google.gson.JsonObject;
import common.rmi.DirettoreProxyInterface;
import server.Server;
import server.coordinator.Controller;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Andrew on 18/06/2015.
 */
public class DirettoreProxy implements DirettoreProxyInterface {

    @Override
    public ArrayList<String> visualizzaStatoFastFood() throws RemoteException {
        Controller controller = Server.ic;
        ArrayList<String> tavoliString = new ArrayList<>();
        for(JsonObject tavolo : controller.visualizzaStatoFastFood()){
            tavoliString.add(tavolo.toString());
        }
        return tavoliString;
    }

}
