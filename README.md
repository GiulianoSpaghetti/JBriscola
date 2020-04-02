# JBriscola
JBriscola: porting in java di wxBriscola. Simulatore del gioco della briscola a due giocatori senza multiplayer.

Per la compilazione ha bisogno delle librerie gson e jfontchooser.

https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.2/

http://www.java2s.com/Code/Jar/j/Downloadjfontchooser105jar.htm

Per i mazzi, quelli di wxBriscola vanno benissimo.

Ha due possibilità di inserimento degli input nella fase di gioco:

*Da tastiera tramite i tasti 1,2 e 3 anche dal tastierino numerico

*Visivo cliccando sulla carta


E' presente  un giochino iniziale che consente di stabilire chi gioca per primo: il gioco della carta più alta, si pesca una carta dal mazzo e chi ha il valore più alto è il primo di mano.

E' possibile giocare con un set di mazzi arbitrario
Attualmente l'estensione delle traduzioni non è possibile senza aggiungere manualmente la nuova voce al menù, non come in wxBriscola.

# Set di mazzi arbitrario
Sono necessari 4 semi, ognuno di 10 carte.
*Bastoni è rappresentato con le immagini jpeg coi numeri da 0 a 9 (0 è 1 di bastoni, 9 è 10 di bastoni, in sequenza)

*Coppe è rappresentato con le immagini jpeg coi numeri da 10 a 19

*Denari è rappresentato con le immagini jpeg da 20 a 29

*Spade è rappresentato con le immagini jpeg da 30 a 39

Sono presenti, in oltre:
*il retro di una singola carta che rappresenta le carte del computer di nome "retro carte pc.jpg"

*il retro della carta del pc girata di 90 gradi chiamata "retro carte mazzo.jpg" che rappresenta il tallone

Queste 42 immagini vanno posizionate in una sottocartella della cartella mazzi presente nella directory di lavoro.
Il programma vedrà la nuova cartella e la aggiungerà automaticamente al menù mazzi.

# Webhook di IFTTT
Dalla versione 0.2 è possibile interagire con IFTTT.
In trigger sono 2:

*quando si inizia una nuova partita

*quando finisce la partita

Se si inizia una nuova partita le opzioni leggibili da IFTTT sono:

*il nome del giocatore

*il nome della cpu

*il mazzo usato per giocare

il trigger da intercettare in IFTTT è JBriscola.start

Se la partita è finita le opzioni leggibili da IFTTT sono:

*il nome del giocatore

*il nome della cpu

*il punteggio finale della partita

il trigger da intercettare in IFTTT è JBriscola.end

Affinché la connessione con IFTTT abbia luogo, è necessario settare nelle opzioni la propria chiave privata, raggiungibile nelle opzioni di IFTTT, raggiungibili dopo aver fatto il login all'indirizzo https://ifttt.com/maker_webhooks e cliccando in alto a destra su "Doocumentation".
La chiave verrà salvata in chiaro nel file delle opzioni.

A cosa serve? Per esempio è possibile interagire su twitter, oppure accendere una lampadina smart di colore verde se si vince o di rosso se si perde.

E' una cosa che mi è parsa sfiziosa da implementare.

# Upgrade dalla versione precedente
La versione 0.2 prerelease salva la dimensione di JBriscoFrame calcolata nelle opzioni, per cui se si apre il gioco avendo la versione del file delle opzioni del 0.1, il frame non comparirà, avendo dimensione nulla.
E' necessario cancellare il file delle opzioni della 0.1 prima di avviare la 0.2.
