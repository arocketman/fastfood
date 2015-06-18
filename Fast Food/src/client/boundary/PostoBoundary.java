package client.boundary;

import client.business_logic.PostoBusinessLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostoBoundary {

	private JFrame framePostoAssegnazione;
	private JTextField textFieldCodiceAssegnazione;
	
	public static final int OKAY = 1;
	public static final int ERRORE = -1;
	public static final int PRENOTAZIONE = 2;
	
	String codicePosto;
	int numeroTavolo;
	private JLabel lblPosto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PostoBoundary window;
					if(args.length > 0)
						window = new PostoBoundary(args[0],Integer.valueOf(args[1]));
					else
						window = new PostoBoundary("AAAA",1);

					window.framePostoAssegnazione.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PostoBoundary(String codicePosto , int numeroTavolo) {
		this.codicePosto = codicePosto;
		this.numeroTavolo = numeroTavolo;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		framePostoAssegnazione = new JFrame();
		framePostoAssegnazione.setBounds(100, 100, 450, 300);
		framePostoAssegnazione.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		framePostoAssegnazione.getContentPane().setLayout(new BoxLayout(framePostoAssegnazione.getContentPane(), BoxLayout.Y_AXIS));
		
		lblPosto = new JLabel("<html><h1>POSTO: <font color='red'>" + codicePosto + "</font> TAVOLO: " + numeroTavolo + "</h1></html>");
		lblPosto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPosto.setAlignmentX(Component.CENTER_ALIGNMENT);
		framePostoAssegnazione.getContentPane().add(lblPosto);
		
		JLabel lblCodiceAssegnazione = new JLabel("Codice assegnazione:");

		lblCodiceAssegnazione.setAlignmentX(Component.CENTER_ALIGNMENT);
		framePostoAssegnazione.getContentPane().add(lblCodiceAssegnazione);
		
		textFieldCodiceAssegnazione = new JTextField();
		textFieldCodiceAssegnazione.setMaximumSize(new Dimension(450,25));
		textFieldCodiceAssegnazione.setAlignmentX(Component.CENTER_ALIGNMENT);
		framePostoAssegnazione.getContentPane().add(textFieldCodiceAssegnazione);
		textFieldCodiceAssegnazione.setColumns(10);
		
		JButton btnOccupaPosto = new JButton("Occupa Posto");
		btnOccupaPosto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PostoBusinessLogic pbl = new PostoBusinessLogic();
				int risultato = pbl.occupaPosto(codicePosto, numeroTavolo, textFieldCodiceAssegnazione.getText());
				if(risultato == OKAY) {
					System.out.println("Mostro il menu, il resto scompare.");
					framePostoAssegnazione.getContentPane().removeAll();
					framePostoAssegnazione.getContentPane().revalidate();
					framePostoAssegnazione.getContentPane().repaint();
					JButton btnLiberaTavolo = new JButton("liberaTavolo");
					btnLiberaTavolo.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							PostoBusinessLogic postoBusinessLogic = new PostoBusinessLogic();
							postoBusinessLogic.liberaPosto(codicePosto,numeroTavolo);
						}
					});
					framePostoAssegnazione.add(btnLiberaTavolo);
				}
				else if(risultato == PRENOTAZIONE){
					System.out.println("Inserisci anche la prenotazione..");
					lblCodiceAssegnazione.setText("Il posto e' stato prenotato! Inserisci codice prenotazione:");
					textFieldCodiceAssegnazione.setText("");
					btnOccupaPosto.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("gh");
						}
					});
				}
				else
					System.out.println("Errore nel codice di assegnazione inserito");
			}
		});

		btnOccupaPosto.setAlignmentX(Component.CENTER_ALIGNMENT);
		framePostoAssegnazione.getContentPane().add(btnOccupaPosto);
	}
	
}
