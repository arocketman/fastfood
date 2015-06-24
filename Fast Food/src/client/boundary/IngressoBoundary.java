package client.boundary;

import client.business_logic.IngressoBusinessLogic;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IngressoBoundary {

	private static final int MAX_POSTI_TAVOLO = 4;
	
	/** La frame del client di ingresso **/
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
				JsonObject assegnazione = (JsonObject) parser.parse(ingressBusinessLogic.richiediTavolo((int) (comboBox.getSelectedItem())));
				if(assegnazione != null){
					
					//Se ho ottenuto un'assegnazione vado a mostrare i suoi contenuti sul client.
										
					JLabel lblCodiceAssegnazione = new JLabel("Codice di assegnazione : " + assegnazione.get("assegnazione"));
					lblCodiceAssegnazione.setAlignmentX(Component.CENTER_ALIGNMENT);
					frame.getContentPane().add(lblCodiceAssegnazione);
					
					JLabel lblNumTavolo = new JLabel("Numero Tavolo : " + assegnazione.get("tavolo"));
					lblNumTavolo.setAlignmentX(Component.CENTER_ALIGNMENT);
					frame.getContentPane().add(lblNumTavolo);

					String posti = "";
					for(JsonElement posto : assegnazione.getAsJsonArray("posti")){
						posti += posto.getAsJsonObject().get("codice").toString() + ",";
					}

					JLabel lblCodPosti = new JLabel("Posti: " + posti);
					lblCodPosti.setAlignmentX(Component.CENTER_ALIGNMENT);
					frame.getContentPane().add(lblCodPosti);
					
					frame.revalidate();
					frame.repaint();

				}
				else{
					
					//Se non ho ottenuto un'assegnazione avvio il form di prenotazione
					
					JLabel lblPostiDisponibili = new JLabel("Non sono presenti posti al momento. Avvio prenotazione..");
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
					lblPostiDisponibili.setAlignmentX(Component.CENTER_ALIGNMENT);
					frame.getContentPane().add(lblPostiDisponibili);
					frame.revalidate();
					frame.repaint();
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
}
