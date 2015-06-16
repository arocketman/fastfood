package server.proxy;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import common.rmi.IngressoProxyInterface;

public class ServerProxyManager {
	
	public void initProxy(){
		try {
			Registry registry = LocateRegistry.getRegistry("localhost");
			IngressoProxy ingressoProxy = new IngressoProxy();
			IngressoProxyInterface ingressoProxyI;
			
			ingressoProxyI = (IngressoProxyInterface) UnicastRemoteObject.exportObject(ingressoProxy, 0);
			registry.rebind("ingresso_proxy", ingressoProxyI);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
