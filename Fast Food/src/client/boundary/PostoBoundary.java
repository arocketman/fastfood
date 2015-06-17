package client.boundary;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PostoBoundary {

	private JFrame frame;
	private JTextField textFieldCodiceAssegnazione;
	
	public static final int OKAY = 1;
	public static final int ERRORE = -1;
	public static final int PRENOTAZIONE = 2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PostoBoundary window = new PostoBoundary();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PostoBoundary() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel lblCodiceAssegnazione = new JLabel("Codice assegnazione:");

		lblCodiceAssegnazione.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(lblCodiceAssegnazione);
		
		textFieldCodiceAssegnazione = new JTextField();
		textFieldCodiceAssegnazione.setMaximumSize(new Dimension(450,25));
		textFieldCodiceAssegnazione.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(textFieldCodiceAssegnazione);
		textFieldCodiceAssegnazione.setColumns(10);
		
		JButton btnOccupaPosto = new JButton("Occupa Posto");
		btnOccupaPosto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});

		btnOccupaPosto.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(btnOccupaPosto);
	}

}
