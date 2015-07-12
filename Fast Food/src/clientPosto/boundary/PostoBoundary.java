package clientPosto.boundary;

import clientPosto.business_logic.PostoBusinessLogic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Client del posto . si inizializza con args[0] = codice , args[1] = tavolo
 */
public class PostoBoundary {

	private JFrame framePostoAssegnazione;
	private JTextField textFieldCodiceAssegnazione;
	
	public static final String OKAY = "1";
	public static final String ERRORE = "-1";
	
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
						window = new PostoBoundary("CCCC",1);

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
		framePostoAssegnazione.setBounds(100, 100, 650, 450);
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
				String codiceAssegnazione = textFieldCodiceAssegnazione.getText();
				String risultato = pbl.occupaPosto(codicePosto, numeroTavolo, codiceAssegnazione);
				if (risultato.equalsIgnoreCase(OKAY)) {
					initializeMenu();
				} else if (risultato.equalsIgnoreCase(ERRORE)) {
					JOptionPane.showMessageDialog(framePostoAssegnazione, "Codice di assegnazione errato per questo posto.");
				} else {
					lblCodiceAssegnazione.setText("Il posto e' stato prenotato! Inserisci codice prenotazione:");
					textFieldCodiceAssegnazione.setText("");
					btnOccupaPosto.removeActionListener(this);
					btnOccupaPosto.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if((pbl.occupaPostoCodPrenotazione(textFieldCodiceAssegnazione.getText(),codiceAssegnazione,codicePosto))){
								initializeMenu();
							}
							else{
								JOptionPane.showMessageDialog(framePostoAssegnazione,"Codice prenotazione non corrispondente");
							}
						}
					});
				}
			}
		});

		btnOccupaPosto.setAlignmentX(Component.CENTER_ALIGNMENT);
		framePostoAssegnazione.getContentPane().add(btnOccupaPosto);
	}

	public void initializeMenu(){
		framePostoAssegnazione.getContentPane().removeAll();
		framePostoAssegnazione.getContentPane().revalidate();
		framePostoAssegnazione.getContentPane().repaint();
		URL imageUrl = getClass().getResource("menu.jpg");
		try {
			BufferedImage image = ImageIO.read(imageUrl);
			JLabel picLabel = new JLabel(new ImageIcon(image));
			framePostoAssegnazione.getContentPane().add(picLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JButton btnLiberaTavolo = new JButton("liberaTavolo");
		btnLiberaTavolo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PostoBusinessLogic postoBusinessLogic = new PostoBusinessLogic();
				if (postoBusinessLogic.liberaPosto(codicePosto, numeroTavolo))
					//framePostoAssegnazione.dispose();
					System.exit(0);
			}
		});
		framePostoAssegnazione.add(btnLiberaTavolo);
	}
	
}
