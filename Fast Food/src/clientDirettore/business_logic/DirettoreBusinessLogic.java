package clientDirettore.business_logic;

import clientDirettore.proxy.DirettoreProxy;

import java.util.ArrayList;

public class DirettoreBusinessLogic {
    public ArrayList<String> visualizzaStatoFastFood(){
        DirettoreProxy direttoreProxy = new DirettoreProxy();
        return direttoreProxy.visualizzaStatoFastFood();
    }
}
