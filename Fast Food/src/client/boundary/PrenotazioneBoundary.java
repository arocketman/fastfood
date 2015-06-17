package client.boundary;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import client.business_logic.IngressoBusinessLogic;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PrenotazioneBoundary {

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
		
		btnInserisciPrenotazione = new JButton("Inserisci prenotazione");
		btnInserisciPrenotazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IngressoBusinessLogic ingressoBusinessLogic = new IngressoBusinessLogic();
				if(ingressoBusinessLogic.inserisciPrenotazione(textFieldCognome.getText(), textFieldTelefono.getText(), numPosti)){
					JLabel lblOkayPrenotazione = new JLabel("Prenotazione effettuata ");
					lblOkayPrenotazione.setAlignmentX(Component.CENTER_ALIGNMENT);
					frame.getContentPane().add(lblOkayPrenotazione);
					
					frame.revalidate();
					frame.repaint();
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
