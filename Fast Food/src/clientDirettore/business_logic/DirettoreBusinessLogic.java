package clientDirettore.business_logic;

import clientDirettore.proxy.DirettoreProxy;

import java.util.ArrayList;

/**
 * Created by Andrew on 18/06/2015.
 */
public class DirettoreBusinessLogic {
    public ArrayList<String> visualizzaStatoFastFood(){
        DirettoreProxy direttoreProxy = new DirettoreProxy();
        return direttoreProxy.visualizzaStatoFastFood();
    }
}
