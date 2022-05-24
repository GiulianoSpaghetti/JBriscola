package org.numerone.altervista.jbriscola;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;
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
	private String version="0.4.1", Autore="Giulio Sorrentino <gsorre84@gmail.com>";
	private BriscoPanel p;
	private Giocatore utente, cpu, primo, secondo, temp;
	private Mazzo mazzo;
	private Carta c, c1;
	private GiocatoreHelperCpu motoreCpu=null;
	private JMenuBar menu;
	private JMenu fileMenu, infoMenu, colorMenu, languageMenu;
	private JMenuItem esci, nuovaPartita, opzioni, about, aggiornamenti, coloreSfondo, coloreTesto, font, en, es, it, fr;
	private boolean primaUtente;
	private JBriscolaOpzioni dataOpzioni;
	private static Gson gson = new Gson();
	private static String path="JBriscola"+File.separator+"jbriscola.json";
	private ResourceBundle bundle;  
	ElaboratoreCarteBriscola e;
	CartaHelperBriscola br;
	Timer t;
	Font f;
	public BriscoFrame(ResourceBundle Bundle) throws FileNotFoundException, IOException {
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
			f=new Font("Serif", Font.PLAIN, 20); 
	     	dataOpzioni.setFont(f);
			dataOpzioni.setLocale(4);
			dataOpzioni.setDimensioni(new Point(550,520));
			dataOpzioni.setIFTTTKey("");
			salvaStato(gson.toJson(dataOpzioni));
		}
		bundle=Bundle;
		bundle=ResourceBundle.getBundle("JBriscolaMessages", LoadLocale(dataOpzioni.locale));
		if (dataOpzioni.cartaAlta) {
			CartaAltaDialog cartaAlta=new CartaAltaDialog(this, dataOpzioni.getMazzo(), null, bundle);
			primaUtente=cartaAlta.giocaPrimaUtente();
			cartaAlta.dispose();
		} else
			primaUtente=true;
		setBackground(dataOpzioni.getColoreSfondo());
		e=new ElaboratoreCarteBriscola(dataOpzioni.punti);
		br=new CartaHelperBriscola(e);
		if (dataOpzioni.getMazzo().equals("Napoletano")) {
			Carta.Inizializza(40, br, dataOpzioni.getMazzo(), bundle, BriscoFrame.class);
			motoreCpu=new GiocatoreHelperCpu(e.GetCartaBriscola());
			motoreCpu.CaricaImmagine(BriscoFrame.class);
		} else {
			Carta.Inizializza(40, br, dataOpzioni.getMazzo(), bundle);
			motoreCpu=new GiocatoreHelperCpu(e.GetCartaBriscola());
			motoreCpu.CaricaImmagine();
		}
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
		
		
		f=dataOpzioni.getFont();
		p=new BriscoPanel(utente, cpu, mazzo, e, primaUtente, f, bundle);
		p.setForeground(dataOpzioni.getColoreTesto());
		p.setBackground(dataOpzioni.getColoreSfondo());
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
		fileMenu=new JMenu(bundle.getString("File"));
		nuovaPartita=new JMenuItem(bundle.getString("NewGame"));
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
		font=new JMenuItem(bundle.getString("Font"));
		font.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				OnSelezionaFont();
			}});
		fileMenu.add(font);
		opzioni=new JMenuItem(bundle.getString("Options"));
		opzioni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				OnOpzioni();
			}});
		fileMenu.add(opzioni);
		esci=new JMenuItem(bundle.getString("Exit"));
		esci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispatchEvent(new WindowEvent(BriscoFrame.this, WindowEvent.WINDOW_CLOSING));
			}});
		fileMenu.add(esci);
		infoMenu=new JMenu(bundle.getString("?"));
		about=new JMenuItem(bundle.getString("About"));
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				OnAbout();
			}});
		aggiornamenti=new JMenuItem(bundle.getString("CheckUpdates"));
		aggiornamenti.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				OnAggiornamenti(true);
				
			}});
		infoMenu.add(about);
		infoMenu.add(aggiornamenti);
		colorMenu=new JMenu(bundle.getString("Colors"));
		coloreTesto=new JMenuItem(bundle.getString("TextColor"));
		coloreTesto.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) 
			    { 
				  OnColoreTesto();
			    } 
		});
		colorMenu.add(coloreTesto);
		coloreSfondo=new JMenuItem(bundle.getString("BackgroundColor"));
		coloreSfondo.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) 
			    { 
				  OnColoreSfondo();
			    } 
		});
		colorMenu.add(coloreSfondo);
		en=new JMenuItem(bundle.getString("en"));
		en.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SetLocale(1);
			}});
		es=new JMenuItem(bundle.getString("es"));
		es.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SetLocale(2);
			}});
		fr=new JMenuItem(bundle.getString("fr"));
		fr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SetLocale(3);
			}});
		it=new JMenuItem(bundle.getString("it"));
		it.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SetLocale(4);
			}});
		languageMenu=new JMenu(bundle.getString("Translations"));
		languageMenu.add(en);
		languageMenu.add(es);
		languageMenu.add(fr);
		languageMenu.add(it);
		menu.add(fileMenu);
		menu.add(colorMenu);
		menu.add(GetMenuMazzi());
		menu.add(languageMenu);
		menu.add(infoMenu);
		setJMenuBar(menu);
		setSize(dataOpzioni.dimensioni.x, dataOpzioni.dimensioni.y);
		setLocationRelativeTo(null);
		setVisible(true);
		if (dataOpzioni.upgrades)
			OnAggiornamenti(false);
		Thread thread=new Thread( new Runnable() { 
			@Override
			public void run() {
				// TODO Auto-generated method stub
				IFTTTConnect("JBriscola.start", dataOpzioni.IFTTTKey, dataOpzioni.nomeUtente, dataOpzioni.nomeCpu, dataOpzioni.mazzo);

			}});
		thread.start();
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("wxBriscola.ico")));
	}
	
	private void OnNuovaPartita(boolean avvisa, boolean inizializza) throws FileNotFoundException, IOException {
		int punteggioUtente, punteggioCpu, i;
		if (avvisa && JOptionPane.showConfirmDialog(null, bundle.getString("newGameMessage"), bundle.getString("confirmDialog"), JOptionPane.OK_CANCEL_OPTION)==JOptionPane.CANCEL_OPTION) {
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
			if (dataOpzioni.getMazzo().equals("Napoletano")) {
				Carta.Inizializza(40, br, dataOpzioni.getMazzo(), bundle, BriscoFrame.class);
				motoreCpu=new GiocatoreHelperCpu(e.GetCartaBriscola());
				motoreCpu.CaricaImmagine(BriscoFrame.class);
			} else {
				Carta.Inizializza(40, br, dataOpzioni.getMazzo(), bundle);
				motoreCpu=new GiocatoreHelperCpu(e.GetCartaBriscola());
				motoreCpu.CaricaImmagine();
			}
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
			Thread t=new Thread( new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					IFTTTConnect("JBriscola.start", dataOpzioni.IFTTTKey, dataOpzioni.nomeUtente, dataOpzioni.nomeCpu, dataOpzioni.mazzo);
				}});
			t.start();
			repaint();
		}
	}

	private void IFTTTConnect(String trigger, String chiave, String value1, String value2, String value3) {
		if (!dataOpzioni.IFTTTKey.isEmpty()) {
			try {
				HttpsURLConnection connection;
				value1 = URLEncoder.encode(value1, StandardCharsets.UTF_8.name());
				value2 = URLEncoder.encode(value2, StandardCharsets.UTF_8.name());
				value3 = URLEncoder.encode(value3, StandardCharsets.UTF_8.name());
				String urlParameters  = String.format("value1=%s&value2=%s&value3=%s", value1, value2, value3);
				byte[] postData       = urlParameters.getBytes(StandardCharsets.UTF_8);
				URL url=new URL(String.format("https://maker.ifttt.com/trigger/%s/with/key/%s",trigger, chiave));
				connection=(HttpsURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("charset", "utf-8");
				connection.setRequestProperty("Content-Length", Integer.toString(postData.length));
				connection.setUseCaches(false);
				DataOutputStream stream = new DataOutputStream(connection.getOutputStream());
				stream.write( postData );
				connection.disconnect();
				if (connection.getResponseCode()!=200)
					JOptionPane.showMessageDialog(this, bundle.getString("ifttError"), bundle.getString("Error"), JOptionPane.OK_OPTION );
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), bundle.getString("Error"), JOptionPane.OK_OPTION);
			}
		}

	}
	
	private void OnAggiornamenti(boolean avvisa) {
		try {
			HttpsURLConnection connection;
			URL url=new URL("https://raw.githubusercontent.com/numerunix/JBriscola/master/version.info");
			connection=(HttpsURLConnection) url.openConnection();
 			connection.setUseCaches(false);
         	BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 			String input;
			input = br.readLine();
			float attuale=Float.parseFloat(version);
			float nuova=Float.parseFloat(input);
			if (attuale<nuova) {
				if (JOptionPane.showConfirmDialog(this, String.format(bundle.getString("newVersionMessage"), version, input), bundle.getString("newVersionTitle"), JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
					if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
					    Desktop.getDesktop().browse(new URI("https://github.com/numerunix/JBriscola/releases"));
					}
				}
			} else {
				if (avvisa)
					JOptionPane.showMessageDialog(this, bundle.getString("latestVersionMessage"), bundle.getString("latestVersionTitle"), JOptionPane.OK_OPTION);
			}
			br.close();
			if (connection.getResponseCode()!=200)
				JOptionPane.showMessageDialog(this, bundle.getString("httpError"), bundle.getString("Error"), JOptionPane.OK_OPTION );
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), bundle.getString("Error"), JOptionPane.OK_OPTION);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				s=bundle.getString("draw");
			else {
				if (scarto>0)
					s=bundle.getString("wonMessage");
				else
					s=bundle.getString("looseMessage");
				scarto=Math.abs(scarto);
				s=s+scarto + bundle.getString("points");
				Thread t=new Thread( new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						IFTTTConnect("JBriscola.end", dataOpzioni.IFTTTKey, dataOpzioni.nomeUtente, dataOpzioni.nomeCpu, utente.GetPunteggioStr()+bundle.getString("at")+cpu.GetPunteggioStr());
					}});
				t.start();				
				if (JOptionPane.showConfirmDialog(this, s+bundle.getString("doYouWishToContinue"), bundle.getString("GameOver"), JOptionPane.OK_CANCEL_OPTION)==JOptionPane.CANCEL_OPTION)
					System.exit(0);
				else
					try {
						OnNuovaPartita(false, true);
						return;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
		OpzioniFrame f=new OpzioniFrame(this, dataOpzioni, bundle);
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
		JMenu m=new JMenu(bundle.getString("Decks"));
		JMenuItem i=null;
		File f=new File(Carta.GetPathMazzi());
		if (!f.exists())
			throw new FileNotFoundException(bundle.getString("Path")+ Carta.GetPathMazzi()+bundle.getString("doesntExists"));
		File[] dirs=f.listFiles();
		i=new JMenuItem("Napoletano");
		i.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String mazzo=Carta.GetNomeMazzo();
				try {
					Carta.CaricaImmagini("Napoletano", bundle, BriscoFrame.class);
					motoreCpu.CaricaImmagine(BriscoFrame.class);
					p.CaricaImmagine(BriscoFrame.class);			
					repaint();
					dataOpzioni.dimensioni=p.getDimensioni();
					setSize(dataOpzioni.dimensioni.x, dataOpzioni.dimensioni.y);
					dataOpzioni.setMazzo("Napoletano");
					salvaStato(gson.toJson(dataOpzioni));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(BriscoFrame.this, e.getMessage()+bundle.getString("reloadPreviousDeck"), bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
					try {
						Carta.CaricaImmagini(mazzo, bundle, BriscoFrame.class);
						motoreCpu.CaricaImmagine(BriscoFrame.class);
						p.CaricaImmagine();
						repaint();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(BriscoFrame.this, bundle.getString("unableToReloadError"), bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
					}
				}
			}});
		m.add(i);
		for (File file: dirs) {
			if (file.isDirectory() && !file.getName().equals("Napoletano")) {
				i=new JMenuItem(file.getName());
				i.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						String mazzo=Carta.GetNomeMazzo();
						try {
							Carta.CaricaImmagini(file.getName(), bundle);
							motoreCpu.CaricaImmagine();
							p.CaricaImmagine();
							repaint();
							dataOpzioni.dimensioni=p.getDimensioni();
							setSize(dataOpzioni.dimensioni.x, dataOpzioni.dimensioni.y);
							dataOpzioni.setMazzo(file.getName());
							salvaStato(gson.toJson(dataOpzioni));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(BriscoFrame.this, e.getMessage()+bundle.getString("reloadPreviousDeck"), bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
							try {
								Carta.CaricaImmagini(mazzo, bundle, BriscoFrame.class);
								motoreCpu.CaricaImmagine(BriscoFrame.class);
								p.CaricaImmagine();
								repaint();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								JOptionPane.showMessageDialog(BriscoFrame.this, bundle.getString("unableToReloadError"), bundle.getString("Error"), JOptionPane.ERROR_MESSAGE);
							}
						}
					}});
				m.add(i);
			}
		}
		return m;
		
	}
	
	private void OnAbout() {
		JBriscolaAbout b=new JBriscolaAbout(this, version, bundle);
		b.dispose();
	}
	
	private void OnColoreSfondo() {
		Color sfondo=dataOpzioni.getColoreSfondo();
        sfondo=JColorChooser.showDialog(this, bundle.getString("selectColor"), sfondo); 
        if (sfondo == null)
        	return;
        setBackground(sfondo);
      dataOpzioni.setColoreSfondo(sfondo);
        repaint();
		try {
			salvaStato(gson.toJson(dataOpzioni));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void OnColoreTesto() {
		Color testo=dataOpzioni.getColoreTesto();
        testo=JColorChooser.showDialog(this, bundle.getString("selectColor"), testo); 
        if (testo==null)
        	return;
       p.setForeground(testo);
       dataOpzioni.setColoreTesto(testo);
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
		        f = fontChooser.getSelectedFont(); 
		        p.setFont(f);
		        dataOpzioni.setFont(f);
				try {
					salvaStato(gson.toJson(dataOpzioni));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }
	}
	
	private void SetLocale(int quale) {
		dataOpzioni.locale=quale;
		try {
			salvaStato(gson.toJson(dataOpzioni));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(this, bundle.getString("restartMessage"), bundle.getString("Attention"), JOptionPane.OK_OPTION);		System.exit(0);
	}
	
	private Locale LoadLocale(int quale) {
		switch (quale) {
			case 1: return Locale.ENGLISH; 
			case 2: return new Locale("es", "ES"); 
			case 3: return Locale.FRENCH;
			default:
				return Locale.ITALIAN;
		}
	}
	
}
