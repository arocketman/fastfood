package server.proxy;

import common.rmi.IngressoProxyInterface;
import common.rmi.PostoProxyInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerProxyManager {
	
	public void initProxy(){
		try {
			Registry registry = LocateRegistry.getRegistry("localhost");
			IngressoProxy ingressoProxy = new IngressoProxy();
			IngressoProxyInterface ingressoProxyI;
			
			ingressoProxyI = (IngressoProxyInterface) UnicastRemoteObject.exportObject(ingressoProxy, 1100);
			registry.rebind("ingresso_proxy", ingressoProxyI);
			
			PostoProxy postoProxy = new PostoProxy();
			PostoProxyInterface postoProxyI;
			postoProxyI = (PostoProxyInterface) UnicastRemoteObject.exportObject(postoProxy, 1100);
			registry.rebind("posto_proxy",postoProxyI);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
