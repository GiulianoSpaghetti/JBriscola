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
import java.util.ResourceBundle;

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
	private ResourceBundle bundle;
	
	public CartaAltaDialog(JFrame Parent, String nomeMazzo, Font f, ResourceBundle b) {
		super(Parent, b.getString("mostValuableCardGame"), Dialog.ModalityType.DOCUMENT_MODAL);
		int i;
		bundle=b;
		el=new ElaboratoreCarteBriscola(true);
		try {
			Carta.Inizializza(40, new CartaHelperBriscola(el), nomeMazzo, bundle);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), b.getString("Error"), JOptionPane.OK_OPTION);
			System.exit(0);
		}
		mazzo=new Mazzo(el);
		rand=new Random();
		i=rand.nextInt(100);
		if (i%3==3)
			primaUtente=false;
		else
			primaUtente=true;
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
		p.add(new JLabel(bundle.getString("mostValuableCardGameHead")), c);
		c.gridy=1;
		p.add(new JLabel(bundle.getString("mostValuableCardGameHeada")), c);
		c.gridy=2;
		p.add(new JLabel(bundle.getString("mostValuableCardGameHeadb")), c);
		c.gridwidth=1;
		c.gridy=3;
		p.add(new JLabel(bundle.getString("numberOfCardPrompt")), c);
		c.gridx=1;
		p.add(cartaUtente, c);
		c.gridy=4;
		c.gridx=0;
		Ok=new JButton(bundle.getString("Ok"));
		Ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				OnOK();
			}});
		p.add(Ok, c);
		c.gridx=1;
		Cancel=new JButton(bundle.getString("Cancel"));
		Cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}});
		p.add(Cancel, c);
		getContentPane().add(p);
		setSize(600,200);
		setVisible(true);
		pack();
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
				s=bundle.getString("YouStart");
			else
				s=bundle.getString("PcStarts");
			p1.add(new JLabel (s), c);
			c.gridwidth=1;
			c.gridy=1;
			p1.add(new JLabel (bundle.getString("YourCard")), c);
			c.gridx=1;
			p1.add(new JLabel(bundle.getString("PcCard")), c);
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
			JButton ok=new JButton(bundle.getString("Ok"));
			ok.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					Carta.dealloca();
					setVisible(false);
					
				}});
			p1.add(ok, c);
			getContentPane().add(p1);
			setVisible(true);
			pack();
			getRootPane().setDefaultButton(ok);
			ok.requestFocus();
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, bundle.getString("notIntegerError"), bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
		} catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(this, bundle.getString("valueError"), bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
		}
		
	}
	boolean giocaPrimaUtente() {return primaUtente;}
}
