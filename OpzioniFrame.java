package org.numerone.altervista.jbriscola;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OpzioniFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7298259472496196089L;
	private GridBagConstraints c;
	private JTextField nomeUtente;
	private JTextField nomeCpu;
	private JTextField secondi;
	private JCheckBox punti, ordina, avvisa, cartaAlta, upgrades;
	private JButton Ok, Cancel;
	JBriscolaOpzioni opzioni;
	
	public OpzioniFrame(JFrame parent, JBriscolaOpzioni o) {
		super(parent, "Opzioni", Dialog.ModalityType.DOCUMENT_MODAL);
		opzioni=o;
		JPanel p=new JPanel();
		p.setLayout(new GridBagLayout());
		c=new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 0, 10);
		c.anchor = GridBagConstraints.LINE_END;
		p.add(new JLabel("Nome Utente: "), c);
		c.gridx=1;
		nomeUtente=new JTextField(20);
		nomeUtente.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				OnOK();
			}
		});
		nomeUtente.setText(opzioni.nomeUtente);
		p.add(nomeUtente, c);
		c.gridy=1;
		c.gridx=0;
		p.add(new JLabel("Nome CPU: "), c);
		nomeCpu=new JTextField(20);
		nomeCpu.setText(opzioni.nomeCpu);
		c.gridx=1;
		nomeCpu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				OnOK();
			}
		});
		p.add(nomeCpu, c);
		c.gridy=2;
		c.gridx=0;
		p.add(new JLabel("Secondi in cui mostrare le giocate: "), c);
		c.gridx=1;
		secondi=new JTextField(20);
		secondi.setText(""+opzioni.secondi);
		secondi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				OnOK();
			}
		});
		p.add(secondi, c);
		punti=new JCheckBox("La carta che designa la briscola può dar punti");
		punti.setSelected(opzioni.punti);
		c.gridy=3;
		c.gridx=0;
		c.gridwidth=2;
		c.fill=GridBagConstraints.HORIZONTAL;
		p.add(punti, c);
		ordina=new JCheckBox("Ordina le carte che mi capitano");
		ordina.setSelected(opzioni.ordina);
		c.gridy=4;
		p.add(ordina, c);
		avvisa=new JCheckBox("Avvisa quando il tallone finisce");
		avvisa.setSelected(opzioni.avvisa);
		c.gridy=5;
		p.add(avvisa, c);
		cartaAlta=new JCheckBox("Fai il gioco della carta più alta all'avvio");
		cartaAlta.setSelected(opzioni.cartaAlta);
		c.gridy=6;
		p.add(cartaAlta, c);
		upgrades=new JCheckBox("Notifica nuove versioni all'avvio");
		upgrades.setSelected(opzioni.upgrades);
		upgrades.setEnabled(false);
		c.gridy=7;
		p.add(upgrades, c);
		Ok=new JButton("Ok");
		Ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				OnOK();
			}
		});
		c.gridy=8;
		c.gridwidth=1;
		p.add(Ok, c);
		Cancel=new JButton("Anulla");
		c.gridx=1;
		Cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dispose();
			}});
		p.add(Cancel, c);
		getContentPane().add(p);
		setTitle("Opzioni");
		setLocationRelativeTo(null);  
		setSize(300,300);
		pack();
	}
	
	public JBriscolaOpzioni getOpzioni() {
		return opzioni;
	}
	
	private void OnOK() {
		// TODO Auto-generated method stub
		opzioni.nomeUtente=nomeUtente.getText();
		opzioni.nomeCpu=nomeCpu.getText();
		opzioni.punti=punti.isSelected();
		opzioni.ordina=ordina.isSelected();
		opzioni.avvisa=avvisa.isSelected();
		opzioni.cartaAlta=cartaAlta.isSelected();
		opzioni.upgrades=upgrades.isSelected();
		try {
			opzioni.secondi=Integer.parseInt(secondi.getText());
			setVisible(false);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Il numero di secondi non è intero.", "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}
