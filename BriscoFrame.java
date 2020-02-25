package org.numerone.altervista.jbriscola;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import say.swing.JFontChooser;

public class BriscoFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3149121715539056995L;
	private String version="0.1", Autore="Giulio Sorrentino <gsorre84@gmail.com>";
	private BriscoPanel p;
	private Giocatore utente, cpu, primo, secondo, temp;
	private Mazzo mazzo;
	private Carta c, c1;
	private GiocatoreHelperCpu motoreCpu=null;
	private JMenuBar menu;
	private JMenu fileMenu, infoMenu, colorMenu;
	private JMenuItem esci, nuovaPartita, opzioni, about, coloreSfondo, coloreTesto, font;
	private boolean primaUtente;
	private JBriscolaOpzioni dataOpzioni;
	private static Gson gson = new Gson();
	private static String path="JBriscola"+File.separator+"jbriscola.json";

	ElaboratoreCarteBriscola e;
	CartaHelperBriscola br;
	Timer t;
	public BriscoFrame() throws FileNotFoundException, IOException {
		super("JBriscola");
		int i;
		if (System.getProperty("os.name").contains("Windows"))
			path=System.getenv("APPDATA")+File.separator+path;
		else
			path=System.getProperty("user.home")+File.separator+".config"+File.separator+path;
		try {
			dataOpzioni=leggiStato();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			dataOpzioni=new JBriscolaOpzioni();
			dataOpzioni.setAvvisa(true);
			dataOpzioni.setCartaAlta(false);
			dataOpzioni.setNomeCpu("Computer");
			dataOpzioni.setNomeUtente("Giulio");
			dataOpzioni.setOrdina(true);
			dataOpzioni.setPunti(true);
			dataOpzioni.setSecondi(1);
			dataOpzioni.setUpgrades(false);
			dataOpzioni.setMazzo("Napoletano");
			dataOpzioni.setColoreSfondo(Color.GREEN);
			dataOpzioni.setColoreTesto(Color.BLACK);
			dataOpzioni.setFont(new Font("Serif", Font.PLAIN, 20));
			salvaStato(gson.toJson(dataOpzioni));
		}
		if (dataOpzioni.cartaAlta) {
			CartaAltaDialog cartaAlta=new CartaAltaDialog(this, dataOpzioni.getMazzo(), null);
			primaUtente=cartaAlta.giocaPrimaUtente();
			cartaAlta.dispose();
		}
		setBackground(dataOpzioni.coloreSfondo);
		e=new ElaboratoreCarteBriscola(dataOpzioni.punti);
		br=new CartaHelperBriscola(e);
		Carta.Inizializza(40, br, dataOpzioni.getMazzo());
		motoreCpu=new GiocatoreHelperCpu(e.GetCartaBriscola());
		mazzo=new Mazzo(e);
		utente=new Giocatore(new GiocatoreHelperUtente(), dataOpzioni.nomeUtente, dataOpzioni.ordina, 3);
		cpu=new Giocatore(motoreCpu, dataOpzioni.nomeCpu, true, 3);
		primo=utente;
		secondo=cpu;
		for (i=0; i<3; i++) {
			primo.AddCarta(mazzo);
			secondo.AddCarta(mazzo);
		}
		t=new Timer();
		p=new BriscoPanel(utente, cpu, mazzo, e, primaUtente, dataOpzioni.font);
		p.setForeground(dataOpzioni.coloreTesto);
		getContentPane().add(p);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				int quale=-1;
				if (!utente.HasCartaGiocata()) {
					switch(arg0.getKeyCode()) {
						case KeyEvent.VK_1:
						case KeyEvent.VK_NUMPAD1: quale=0; break;
						case KeyEvent.VK_2:
						case KeyEvent.VK_NUMPAD2: quale=1; break;
						case KeyEvent.VK_3:
						case KeyEvent.VK_NUMPAD3: quale=2; break;
					}
					if (quale>-1)
						Gioca(quale);

				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}});
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (!utente.HasCartaGiocata()) {
					int i=p.controllaCoordinate(arg0.getX(), arg0.getY());
					if  (i>-1 && i<3)
						Gioca(i);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		menu=new JMenuBar();
		fileMenu=new JMenu("File");
		nuovaPartita=new JMenuItem("Nuova Partita");
		nuovaPartita.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					OnNuovaPartita(true, true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}});
		fileMenu.add(nuovaPartita);
		
		font=new JMenuItem("Font");
		font.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				OnSelezionaFont();
			}});
		fileMenu.add(font);
		opzioni=new JMenuItem("Opzioni");
		opzioni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				OnOpzioni();
			}});
		fileMenu.add(opzioni);
		esci=new JMenuItem("Esci");
		esci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}});
		fileMenu.add(esci);
		infoMenu=new JMenu("?");
		about=new JMenuItem("Informazioni");
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				OnAbout();
			}});
		infoMenu.add(about);
		colorMenu=new JMenu("Colori");
		coloreTesto=new JMenuItem("Colore testo");
		coloreTesto.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) 
			    { 
				  OnColoreTesto();
			    } 
		});
		colorMenu.add(coloreTesto);
		coloreSfondo=new JMenuItem("Colore sfondo");
		coloreSfondo.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) 
			    { 
				  OnColoreSfondo();
			    } 
		});
		colorMenu.add(coloreSfondo);
		menu.add(fileMenu);
		menu.add(colorMenu);
		menu.add(GetMenuMazzi());
		menu.add(infoMenu);
		setJMenuBar(menu);
		setSize(550,520);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void OnNuovaPartita(boolean avvisa, boolean inizializza) throws FileNotFoundException, IOException {
		int punteggioUtente, punteggioCpu, i;
		if (avvisa && JOptionPane.showConfirmDialog(null, "La partita correntemente in corso verrï¿½ interrota. Continuare?", "Richiesta Conferma", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.CANCEL_OPTION) {
			;	
		} else {
			if (inizializza) {
				punteggioUtente=0;
				punteggioCpu=0;
			} else {
				punteggioUtente=utente.GetPunteggio();
				punteggioCpu=cpu.GetPunteggio();
			}
			Carta.dealloca();
			e=new ElaboratoreCarteBriscola(dataOpzioni.punti);
			mazzo=new Mazzo(e);
			br=new CartaHelperBriscola(e);
			Carta.Inizializza(40, br, dataOpzioni.getMazzo());
			motoreCpu=new GiocatoreHelperCpu(e.GetCartaBriscola());
			utente=new Giocatore(new GiocatoreHelperUtente(), dataOpzioni.nomeUtente, dataOpzioni.ordina, 3);
			cpu=new Giocatore(motoreCpu, dataOpzioni.nomeCpu, true, 3);
			primo=utente;
			secondo=cpu;
			for (i=0; i<3; i++) {
				primo.AddCarta(mazzo);
				secondo.AddCarta(mazzo);
			}
			p.SetUtente(utente);
			p.SetComputer(cpu);
			p.SetMazzo(mazzo);
			p.SetElaboratoreCarteBriscola(e);
			p.CaricaImmagine();
			primaUtente=!primaUtente;
			if (primaUtente) {
				primo=utente;
				secondo=cpu;
			} else {
				primo=cpu;
				secondo=utente;
				primo.Gioca(0);
			}
			repaint();
		}
	}
	
	private void OnTimer() {
		int scarto;
		try {
			primo.AggiornaPunteggio(secondo);
			try {
				primo.AddCarta(mazzo);
				secondo.AddCarta(mazzo);
			} catch (IndexOutOfBoundsException e5) {
			}
		} catch (NullPointerException e6) {
			scarto=utente.GetPunteggio()-cpu.GetPunteggio();
			String s;
			if (scarto==0)
				s="La partita ï¿½ patta.";
			else {
				if (scarto>0)
					s="Hai vinto per ";
				else
					s="Hai perso per ";
				scarto=Math.abs(scarto);
				s=s+scarto + " punti.";
				
				JOptionPane.showMessageDialog(this, s, "Partita Finita", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
    	}
		if (primo==cpu)
			cpu.Gioca(0);	
		repaint();
		if (mazzo.GetNumeroCarte()==0 && primo.getNumeroCarte()==3)
			java.awt.Toolkit.getDefaultToolkit().beep();
	}
	
	private void Gioca(int quale) {
		boolean continua=true;
		try {
			utente.Gioca(quale);
		} catch (IndexOutOfBoundsException e) {
			continua=false;
		}
		if (continua) {
			if (!cpu.HasCartaGiocata())
				cpu.Gioca(utente, 0);
			repaint();
			c=primo.GetCartaGiocata();
            c1=secondo.GetCartaGiocata();
			repaint();
            if ((primo.StessoSemeCartaGiocata(secondo) && br.Compara(c.GetNumero(), c1.GetNumero())==CartaHelper.RISULTATI_COMPARAZIONE.MAGGIORE_LA_SECONDA) || (secondo.StessoSeme(Carta.GetCarta(e.GetCartaBriscola())) && !primo.StessoSeme(Carta.GetCarta(e.GetCartaBriscola())))) {
            	temp=primo;
                primo=secondo;
                secondo=temp;
             }
            t.schedule(new TimerTask() {

				@Override
				public void run() {OnTimer();}}, dataOpzioni.secondi*1000);
		}		
	}
	
	private void OnOpzioni() {
		OpzioniFrame f=new OpzioniFrame(this, dataOpzioni);
		f.setVisible(true);
		dataOpzioni=f.getOpzioni();
		utente.setNome(dataOpzioni.nomeUtente);
		cpu.setNome(dataOpzioni.nomeCpu);
		utente.SetFlagOrdina(dataOpzioni.ordina);
		f.dispose();
		try {
			salvaStato(gson.toJson(dataOpzioni));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}
	
	private static void salvaStato(String stato) throws IOException {
		File f = new File(path);
		if (!f.exists()) {
			File d = new File(f.getParent());
			if (!f.exists()) {
				d.mkdirs();
			}
			f.createNewFile();
		}
 
		BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsoluteFile(), false));
		bw.write(stato.toString());
		bw.close();
 
	}
	
	private static JBriscolaOpzioni leggiStato() throws IOException {
		File f = new File(path);
		JBriscolaOpzioni o=null;
		if (!f.exists())
			throw new FileNotFoundException();
		else {
			JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
			o=gson.fromJson(reader, JBriscolaOpzioni.class);
		}
		return o;
	}

	private JMenu GetMenuMazzi() throws FileNotFoundException {
		JMenu m=new JMenu("Mazzi");
		JMenuItem i=null;
		File f=new File(Carta.GetPathMazzi());
		if (!f.exists())
			throw new FileNotFoundException("La path "+ Carta.GetPathMazzi()+" non esiste.");
		File[] dirs=f.listFiles();
		for (File file: dirs) {
			if (file.isDirectory()) {
				i=new JMenuItem(file.getName());
				i.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						String mazzo=Carta.GetNomeMazzo();
						try {
							Carta.CaricaImmagini(file.getName());
							motoreCpu.CaricaImmagine();
							p.CaricaImmagine();
							repaint();
							dataOpzioni.setMazzo(file.getName());
							salvaStato(gson.toJson(dataOpzioni));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null, e.getMessage()+"\n Il programma proverà a ricaricare il mazzo precedente.", "Errore", JOptionPane.ERROR_MESSAGE);
							try {
								Carta.CaricaImmagini(mazzo);
								motoreCpu.CaricaImmagine();
								p.CaricaImmagine();
								repaint();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(null, "Non è stato possibile caricare il mazzo precedente: uscire dal programma significa non poterlo avviare più. Caricare un mazzo completo prima di uscire", "Errore", JOptionPane.ERROR_MESSAGE);
							}
						}
					}});
				m.add(i);
			}
		}
		return m;
	}
	
	private void OnAbout() {
		JBriscolaAbout b=new JBriscolaAbout(this);
		b.dispose();
	}
	
	private void OnColoreSfondo() {
        dataOpzioni.coloreSfondo = JColorChooser.showDialog(this, "Seleziona un colore", dataOpzioni.coloreSfondo); 
        setBackground(dataOpzioni.coloreSfondo);
        repaint();
		try {
			salvaStato(gson.toJson(dataOpzioni));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void OnColoreTesto() {
        dataOpzioni.coloreTesto = JColorChooser.showDialog(this, "Seleziona un colore", dataOpzioni.coloreTesto); 
        p.setForeground(dataOpzioni.coloreTesto);
        repaint();
		try {
			salvaStato(gson.toJson(dataOpzioni));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void OnSelezionaFont() {
		   JFontChooser fontChooser = new JFontChooser();
		   int result = fontChooser.showDialog(this);
		   if (result == JFontChooser.OK_OPTION) {
		        dataOpzioni.font = fontChooser.getSelectedFont(); 
		        p.setFont(dataOpzioni.font);
				try {
					salvaStato(gson.toJson(dataOpzioni));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }
	}
	
}
