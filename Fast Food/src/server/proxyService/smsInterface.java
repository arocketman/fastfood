package server.proxyService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class smsInterface {
	private static String uname="xxxx@gmail.com";
	private static String pword="psssFastFood10";
	private static String from="FastFood";
	private static String serviceUrl="https://api.txtlocal.com/send/?";
	
	private URLConnection connect(){
		URL url = null;
		URLConnection conn = null;
		try {
			url = new URL(serviceUrl);
			conn = url.openConnection();//Apro la connessione verso l'url
		} catch (IOException e ) {
			System.out.println("Errore nella fase di connessione al servizio per gli SMS");
			e.printStackTrace();
			return null;
		}
		conn.setDoOutput(true);											//indico che intendo utilizzare la connessione per ricevere dell'output							
		return conn;
	}
		//TelephoneNumber: dovrà essere inserito con il +39 come prefisso
	/*
	public String sendSms(String TelephoneNumber,String Msg)  {
		try {
		    // Construct data
		    String data = "uname=" + URLEncoder.encode(uname, "UTF-8");
		    data += "&pword=" + URLEncoder.encode(pword, "UTF-8");
		    data += "&message=" + URLEncoder.encode(Msg, "UTF-8");
		    data += "&from=" + URLEncoder.encode(from, "UTF-8");
		    data += "&selectednums=" + URLEncoder.encode(TelephoneNumber, "UTF-8");
		    data += "&info=" + URLEncoder.encode("1", "UTF-8");
		    // Send data
		    URLConnection conn=connect();
		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());		//creo un output stream che sia collegato all'output stream della connessione 
		    wr.write(data);				//scrivo i dati nello stream
		    wr.flush();					//invio i dati nello stream
		    // Get the response
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String line;
		    String sResult="";
		    while ((line = rd.readLine()) != null) {
		        sResult=sResult+line+" ";		// Process line...
		    }
		    wr.close();
		    rd.close();
		    return sResult;
		} catch (Exception e) {
		    System.out.println("Error SMS "+e);
		    return "Error "+e;
		}
	}
	*/
	
	
	public Boolean sendSms(String TelephoneNumber,String Msg,int test) {
		//test=1 indica che il messaggio non viene inviato ma viene effettuato solo un test 
		//test=0	il messaggio viene inviato davvero
		try {
			Gson gson = new Gson();
			// Construct data
			String user = "username=" + uname;
			String hash = "&hash=" + pword;
			String message = "&message=" + Msg;
			String sender = "&sender=" + from;
			String numbers = "&numbers=" + TelephoneNumber;
			String testMode = "&test=" + test;    
			 
			// Send data
			URLConnection urlConnection=connect();
			if(urlConnection!=null){
				HttpURLConnection conn = (HttpURLConnection) urlConnection;
				String data = user + hash + numbers + message + sender + testMode;
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
				conn.getOutputStream().write(data.getBytes("UTF-8"));
				final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				final StringBuffer stringBuffer = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					//System.out.println("line" + line);
					stringBuffer.append(line);
				}
				rd.close();	 
				String response = stringBuffer.toString();
				//Parse Response
			    JsonParser jsonParser = new JsonParser();
			    JsonObject jsonResponse=(JsonObject)jsonParser.parse(response);
			    String status=jsonResponse.get("status").getAsString();
			    if(status.equalsIgnoreCase("success")){
			    	float balance=jsonResponse.get("balance").getAsFloat();
			    	float cost=jsonResponse.get("cost").getAsFloat();
			    	if(cost<balance){
			    		return true;
			    	}else{
			    		return false;
			    	}
			    	//return true;
			    }else{
			    	return false;
			    }
			}else{
				return false;
			}
		} catch (Exception e) {
			System.out.println("Errore nell'invio dell'SMS ");
			e.printStackTrace();
			return false;
		}
	}
}
