package clientIngresso.boundary;
//Anto 11/07/2015
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogShowResponse extends JDialog {
	private String msg=null;
	private Frame frame=null;
	
	public DialogShowResponse(String msg){
		this.msg=msg;
		this.inizialize(msg,null);
	}
	
	public DialogShowResponse(String msg,Frame frame){
		this.msg=msg;
		this.frame=frame;
		this.inizialize(msg,frame);
	}
	
	public void inizialize(String msg,Frame frame) {
		setModal(true); //Rende la jDialog Bloccante
		setBounds(100, 100, 207, 109);
		getContentPane().setLayout(new BorderLayout(10, 10));
		{
			JLabel lblConsegneTerminate = new JLabel(this.msg);
			Font font=new Font("Tahoma", Font.PLAIN, 16);
			lblConsegneTerminate.setFont(font);
			lblConsegneTerminate.setForeground(Color.RED);
			lblConsegneTerminate.setAlignmentY(Component.TOP_ALIGNMENT);
			getContentPane().add(lblConsegneTerminate, BorderLayout.CENTER);
			setSize(new Dimension(400, 202));			
		}
		
		{
			JButton btnOK = new JButton("OK");
			btnOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(frame!=null){
						frame.dispose();
					}
					dispose();
				}
			});
			getContentPane().add(btnOK, BorderLayout.SOUTH);
		}
	}
}
