package org.numerone.altervista.jbriscola;

import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CartaAltaDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7451608071295177173L;
	private boolean primaUtente;
	private JTextField cartaUtente;
	private ElaboratoreCarteBriscola el;
	private Mazzo mazzo;
	private JButton Ok, Cancel;
	private Carta carta, carta1;
	private Random rand;
	private JPanel p, p1;
	public CartaAltaDialog(JFrame Parent, String nomeMazzo, Font f) {
		super(Parent, "Gioco della CartaAlta", Dialog.ModalityType.DOCUMENT_MODAL);
		el=new ElaboratoreCarteBriscola(true);
		try {
			Carta.Inizializza(40, new CartaHelperBriscola(el), nomeMazzo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mazzo=new Mazzo(el);
		rand=new Random();
		p=new JPanel();
		p.setLayout(new GridBagLayout());
		GridBagConstraints c=new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 10);
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth=2;
		cartaUtente=new JTextField(2);
		cartaUtente.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				OnOK();
			}});
		p.add(new JLabel("Il gioco della carta alta permette di stabilire chi gioca per primo."), c);
		c.gridy=1;
		p.add(new JLabel("Scrivere un numero da 1 a 40 che identifica la carta del mazzo da scegliere."), c);
		c.gridy=2;
		p.add(new JLabel("Il computer ne sceglierà un'altra e chi avrà il valore maggiore comincerà."), c);
		c.gridwidth=1;
		c.gridy=3;
		p.add(new JLabel("Numero della carta da prendere: "), c);
		c.gridx=1;
		p.add(cartaUtente, c);
		c.gridy=4;
		c.gridx=0;
		Ok=new JButton("Ok");
		Ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				OnOK();
			}});
		p.add(Ok, c);
		c.gridx=1;
		Cancel=new JButton("Annulla");
		Cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}});
		p.add(Cancel, c);
		getContentPane().add(p);
		setSize(400,200);
		pack();
		setVisible(true);
	}
	
	private void OnOK() {
		int i=-1;
		try {
			i=Integer.parseInt(cartaUtente.getText());
			if (i<=0 || i>40)
				throw new IndexOutOfBoundsException("");
			carta=Carta.GetCarta(mazzo.GetCarta(i));
			i=rand.nextInt(40);
			carta1=Carta.GetCarta(mazzo.GetCarta(i));
			primaUtente=carta.GetValore()>=carta1.GetValore();
			p.setVisible(false);
			p1=new JPanel(new GridBagLayout());
			GridBagConstraints c=new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth=2;
			c.insets = new Insets(5, 0, 0, 10);
			c.anchor = GridBagConstraints.CENTER;
			String s;
			if (primaUtente)
				s="Cominci prima tu";
			else
				s="Comincia prima il pc";
			p1.add(new JLabel (s), c);
			c.gridwidth=1;
			c.gridy=1;
			p1.add(new JLabel ("Carta tua"), c);
			c.gridx=1;
			p1.add(new JLabel("Carta del pc"), c);
			c.gridy=2;
			c.gridx=0;
			JLabel l=new JLabel(), l1=new JLabel();
			ImageIcon icon=new ImageIcon(carta.GetImmagine()),
					icon1=new ImageIcon(carta1.GetImmagine());
			l.setIcon(icon);
			l1.setIcon(icon1);
			p1.add(l, c);
			c.gridx=1;
			p1.add(l1, c);
			c.gridy=3;
			c.gridx=0;
			JButton ok=new JButton("Ok");
			ok.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					Carta.dealloca();
					setVisible(false);
					
				}});
			p1.add(ok, c);
			getContentPane().add(p1);
			pack();
			setVisible(true);
			getRootPane().setDefaultButton(ok);
			ok.requestFocus();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Il valore inserito non è un numero intero.", "Errore", JOptionPane.ERROR_MESSAGE);
		} catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(this, "Il valore inserito non è valido.", "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	boolean giocaPrimaUtente() {return primaUtente;}
}
