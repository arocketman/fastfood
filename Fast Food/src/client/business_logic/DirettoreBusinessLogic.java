package client.business_logic;

import client.proxy.DirettoreProxy;
import server.entity.Tavolo;

import java.util.ArrayList;

/**
 * Created by Andrew on 18/06/2015.
 */
public class DirettoreBusinessLogic {
    public ArrayList<Tavolo> visualizzaStatoFastFood(){
        DirettoreProxy direttoreProxy = new DirettoreProxy();
        return direttoreProxy.visualizzaStatoFastFood();
    }
}
