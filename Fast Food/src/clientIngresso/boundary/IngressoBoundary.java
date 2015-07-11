package clientIngresso.boundary;

import clientIngresso.business_logic.IngressoBusinessLogic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IngressoBoundary {

	private static final int MAX_POSTI_TAVOLO = 4;
	
	/** La frame del clientPosto di ingresso **/
	private JFrame frame;
	
	/** Bottone di submit richiesta tavolo **/
	private JButton btnInviaRichiestaTavolo;
	
	/** Combobox per la scelta del numercxs qo di posti **/
	private JComboBox<Integer> comboBox;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IngressoBoundary window = new IngressoBoundary();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public IngressoBoundary() {
		initialize();
	}

	/**
	 * Inizializza i contenuti della jframe.
	 */
	private void initialize() {
		
		//Inizializzazione frame
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setMaximumSize(frame.getContentPane().getPreferredSize());
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		//Inizializzazione label
		JLabel inserisciLbl = new JLabel("Inserisci numero di posti:");
		inserisciLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(inserisciLbl);
		
		//Inizializzazione bottone.
		btnInviaRichiestaTavolo = new JButton("Invia richiesta tavolo");
		btnInviaRichiestaTavolo.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnInviaRichiestaTavolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IngressoBusinessLogic ingressBusinessLogic = new IngressoBusinessLogic();
				
				//TODO: validazione dell'input
				JsonParser parser = new JsonParser();
				String returnedJson = ingressBusinessLogic.richiediTavolo((int) (comboBox.getSelectedItem()));
				if(returnedJson != null){
					JsonObject assegnazione = (JsonObject) parser.parse(returnedJson);

					//Se ho ottenuto un'assegnazione vado a mostrare i suoi contenuti sul clientPosto.
					/*Anto 11/07/2015
					JLabel lblCodiceAssegnazione = new JLabel("Codice di assegnazione : " + assegnazione.get("assegnazione"));
					lblCodiceAssegnazione.setAlignmentX(Component.CENTER_ALIGNMENT);
					frame.getContentPane().add(lblCodiceAssegnazione);

					JLabel lblNumTavolo = new JLabel("Numero Tavolo : " + assegnazione.get("tavolo"));
					lblNumTavolo.setAlignmentX(Component.CENTER_ALIGNMENT);
					frame.getContentPane().add(lblNumTavolo);
					*/
					
					String posti = "";
					for(JsonElement posto : assegnazione.getAsJsonArray("posti")){
						posti += posto.getAsJsonObject().get("codice").toString() + ",";
					}
					posti=posti.substring(0, posti.length()-1);
					/*Anto 11/07/2015
					JLabel lblCodPosti = new JLabel("Posti: " + posti);
					lblCodPosti.setAlignmentX(Component.CENTER_ALIGNMENT);
					frame.getContentPane().add(lblCodPosti);		
					frame.revalidate();
					frame.repaint();
					*/
					
					//Anto 11/07/2015
					String responseMsg="";
					responseMsg+="Codice di assegnazione : " + assegnazione.get("assegnazione") + "<br>";
					responseMsg+="Numero Tavolo : " + assegnazione.get("tavolo")+ "<br>";
					responseMsg+="Posti: " + posti  + "<br>";
					//System.out.println(responseMsg);
					DialogShowResponse dialogShowResponse=new DialogShowResponse("<html>"+responseMsg+"</html>");
					dialogShowResponse.setVisible(true);

				}
				else{
					
					//Se non ho ottenuto un'assegnazione avvio il form di prenotazione
					
					//Anto 11/07/2015
					//JLabel lblPostiDisponibili = new JLabel("Non sono presenti posti al momento. Avvio prenotazione..");
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								PrenotazioneBoundary window = new PrenotazioneBoundary((int)comboBox.getSelectedItem());
								window.frame.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
					/*Anto 11/07/2015
					lblPostiDisponibili.setAlignmentX(Component.CENTER_ALIGNMENT);
					frame.getContentPane().add(lblPostiDisponibili);
					frame.revalidate();
					frame.repaint();
					*/
				}
				
			}
		});
		
		//Inizializzazione combobox
		comboBox = new JComboBox<Integer>();
		comboBox.setMaximumSize(new Dimension(300,45));
		for(int i = 1; i < MAX_POSTI_TAVOLO+1; i++){
			comboBox.addItem(i);
		}
		frame.getContentPane().add(comboBox);
		frame.getContentPane().add(btnInviaRichiestaTavolo);
	}

	/**
     * Ha i metodi e membri necessari per gestire la finestra di prenotazione.
     * NB: NON E' UN CLIENT AGGIUNTIVO. NON HA MAIN. E' Aperto dal clientPosto assegnazione.
     */
    public static class PrenotazioneBoundary {

        JFrame frame;
        private JTextField textFieldCognome;
        private JTextField textFieldTelefono;
        private JButton btnInserisciPrenotazione;

        int numPosti;

        public PrenotazioneBoundary(int numPosti) {
            initialize();
            this.numPosti = numPosti;
        }

        /**
         * Initialize the contents of the frame.
         */
        public void initialize() {
            frame = new JFrame();
            frame.setBounds(100, 100, 450, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

            //Anto 11/07/2015
            JLabel lblPostiDisponibili = new JLabel("<html>Al momento non c'è un tavolo con questo numero di posti liberi <br> "
            		+ "Se vuole inserisca i dati per effettuare una prenotazione.<br> "
            		+ "Verrà avvisato tramite SMS quando i posti saranno disponibili<html>");
            lblPostiDisponibili.setAlignmentX(Component.CENTER_ALIGNMENT);
            frame.getContentPane().add(lblPostiDisponibili);
            
            JLabel lblCognome = new JLabel("Cognome:");
            lblCognome.setAlignmentX(Component.CENTER_ALIGNMENT);

            frame.getContentPane().add(lblCognome);

            textFieldCognome = new JTextField();
            frame.getContentPane().add(textFieldCognome);

            textFieldCognome.setMaximumSize(new Dimension(300,20));
            textFieldCognome.setAlignmentX(Component.CENTER_ALIGNMENT);

            textFieldCognome.setColumns(10);

            JLabel lblTelefono = new JLabel("Numero di telefono:");
            lblTelefono.setAlignmentX(Component.CENTER_ALIGNMENT);

            frame.getContentPane().add(lblTelefono);

            textFieldTelefono = new JTextField();

            textFieldTelefono.setMaximumSize(new Dimension(300,20));
            textFieldTelefono.setAlignmentX(Component.CENTER_ALIGNMENT);

            frame.getContentPane().add(textFieldTelefono);
            textFieldTelefono.setColumns(10);

            //Bottone inserisci prenotazione, richiama la business logic.

            btnInserisciPrenotazione = new JButton("Inserisci prenotazione");
            btnInserisciPrenotazione.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                	//Anto 11/07/2015    - Devo controllare gli input inseriti
                	/*	Cognome: non deve contenere numeri
                	 * 	Numero di telefono non deve contenere lettere e deve essere precisamente di lunghezza 10
                	 */
                	
                	String Cognome=textFieldCognome.getText();
                	String TelNumber=textFieldTelefono.getText();
                	Boolean flagContainsNumber=Cognome.matches(".*\\d+.*");  // se il flag è true allora il cognome contiene dei numeri
                	Boolean flagContainsCharacter=TelNumber.matches(".*[a-zA-Z]+.*");   // se il flag è true allora il telefono contiene delle lettere
                	
                	if(flagContainsNumber || flagContainsCharacter || TelNumber.length()!=10){
                		//in questo caso i dati non sono stati inseriti correttamente
                		String msg="";
                		if(flagContainsNumber){
                			msg+="Il cognome non può contenere numeri<br>";
                		}
                		if(flagContainsCharacter){
                			msg+="Il numero di telefono non può contenere caratteri<br>";
                		}
                		if(TelNumber.length()!=10){
                			msg+="Il numero di telefono deve essere di 10 caratteri<br>";
                		}
    					DialogShowResponse dialogShowResponse=new DialogShowResponse("<html>"+msg+"</html>");
    					dialogShowResponse.setVisible(true);
    					return;
                	}
                	
                    IngressoBusinessLogic ingressoBusinessLogic = new IngressoBusinessLogic();
                    if(ingressoBusinessLogic.inserisciPrenotazione(textFieldCognome.getText(), textFieldTelefono.getText(), numPosti)){
                        
    					//Anto 11/07/2015
    					String responseMsg="Prenotazione effettuata ";
    					//System.out.println(responseMsg);
    					DialogShowResponse dialogShowResponse=new DialogShowResponse("<html>"+responseMsg+"</html>",frame);
    					dialogShowResponse.setVisible(true);
    					
    					/*Anto 11/07/2015
                    	JLabel lblOkayPrenotazione = new JLabel("Prenotazione effettuata ");
                        lblOkayPrenotazione.setAlignmentX(Component.CENTER_ALIGNMENT);
                        frame.getContentPane().add(lblOkayPrenotazione);
                        frame.revalidate();
                        frame.repaint();
                        frame.setVisible(false);
                        */
                        
                    }
                    else{
                        System.out.println("errore prenotazione");
                    }
                }
            });
            btnInserisciPrenotazione.setAlignmentX(Component.CENTER_ALIGNMENT);
            frame.getContentPane().add(btnInserisciPrenotazione);
        }
    }
}
