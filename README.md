# JBriscola
JBriscola: porting in java di wxBriscola. Simulatore del gioco della briscola a due giocatori senza multiplayer.

Per la compilazione ha bisogno delle librerie gson e jfontchooser.

https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.2/

http://www.java2s.com/Code/Jar/j/Downloadjfontchooser105jar.htm

Ha due possibilità di inserimento degli input nella fase di gioco:

- Da tastiera tramite i tasti 1,2 e 3 anche dal tastierino numerico

- Visivo cliccando sulla carta


E' presente  un giochino iniziale che consente di stabilire chi gioca per primo: il gioco della carta più alta, si pesca una carta dal mazzo e chi ha il valore più alto è il primo di mano.

E' possibile giocare con un set di mazzi arbitrario. La cartella dei mazzi deve essere nella directory di lavoro.

Attualmente l'estensione delle traduzioni non è possibile senza aggiungere manualmente la nuova voce al menù, non come in wxBriscola.

# Installazione su Windows
Il jar è stato compilato con la java machine 17, per cui è necessaria la jre 17 o superiore per poter aprire il programma.
Stando alle mie ricerche, oracle ha dismesso la jre pubblicando solo il JDK, che sarebbe il tool di sviluppo, scaricabile all'indirizzo https://www.oracle.com/java/technologies/downloads/#jdk18-windows

# Mazzi di carte
Come mazzi di carte è possibile usare quelli di wxbriscola, ad ogni modo li ho uppati sia in formato windows che in formato linux per semplificare il download.
Ricordatevi che la jbriscola funziona benino solo sotto linux, perché sotto windows ha diversi bug grafici.
Se volete risolverli e darmi il vostro codice sorgente sotto forma di pull request su questa piattaforma, sarò ben felice di mettervi tra gli sviluppatori del progetto.

# Set di mazzi arbitrario
Sono necessari 4 semi, ognuno di 10 carte.
- Bastoni è rappresentato con le immagini jpeg coi numeri da 0 a 9 (0 è 1 di bastoni, 9 è 10 di bastoni, in sequenza)

- Coppe è rappresentato con le immagini jpeg coi numeri da 10 a 19

- Denari è rappresentato con le immagini jpeg da 20 a 29

- Spade è rappresentato con le immagini jpeg da 30 a 39

Sono presenti, in oltre:
- il retro di una singola carta che rappresenta le carte del computer di nome "retro carte pc.jpg"

- il retro della carta del pc girata di 90 gradi chiamata "retro carte mazzo.jpg" che rappresenta il tallone

Queste 42 immagini vanno posizionate in una sottocartella della cartella mazzi presente nella directory di lavoro.
Il programma vedrà la nuova cartella e la aggiungerà automaticamente al menù mazzi.

# Come localizzare il programma

Per localizzare il programma basta dotarsi di eclipse o di qualsiasi altro editor visuale, scaricare uno dei files JBriscolaMessages_xy.properties e localizzare tutto quello che è a destra del simbolo "=".
A questo punto, bisogna rinominare il file usando la nominazione a due caratteri della traduzione (se prendete JBriscolaMessages_it.properties e volete localizzare in tedesco bisogna rinominarlo in JBriscolaMessages_de.properties).
A questo punto è sufficiente avviare il programma passando come parametri "-Duser.language=xy -Duser.region=XY" per vedere il programma localizzato (esempuio java -Duser.language=fr -Duser.region=FR -jar ./Jbriscola-0.4.2.jar).

Se volete mandarmi le vostre localizzazioni sarò felice di immetterle nel programma origibnale e darvi darvi il credit.
Ricordatevi che la GPL obbliga a mantenere i credits originali, quindi, per fare, fate i seri...

![screen-2022-05-24-18-50-27](https://user-images.githubusercontent.com/49764967/170090089-8b48e119-b08d-4f8f-968e-f528b01891a8.png)
![screen-2022-05-24-18-50-06](https://user-images.githubusercontent.com/49764967/170090097-71f70454-08b5-472d-ac62-097a053ed222.png)
![screen-2022-05-24-20-59-26](https://user-images.githubusercontent.com/49764967/170112161-e61a6ba2-caf0-445a-bd1f-3e19e5a57555.png)
![screen-2022-05-24-20-58-37](https://user-images.githubusercontent.com/49764967/170112169-29b7087e-4673-4d1c-92db-189b46b17840.png)


# Webhook di IFTTT
Dalla versione 0.2 è possibile interagire con IFTTT.
In trigger sono 2:

- quando si inizia una nuova partita

- quando finisce la partita

Se si inizia una nuova partita le opzioni leggibili da IFTTT sono:

- il nome del giocatore

- il nome della cpu

- il mazzo usato per giocare

il trigger da intercettare in IFTTT è JBriscola.start

Se la partita è finita le opzioni leggibili da IFTTT sono:

- il nome del giocatore

- il nome della cpu

- il punteggio finale della partita

il trigger da intercettare in IFTTT è JBriscola.end

Affinché la connessione con IFTTT abbia luogo, è necessario settare nelle opzioni la propria chiave privata, raggiungibile nelle opzioni di IFTTT, raggiungibili dopo aver fatto il login all'indirizzo https://ifttt.com/maker_webhooks e cliccando in alto a destra su "Doocumentation".
La chiave verrà salvata in chiaro nel file delle opzioni.

A cosa serve? Per esempio è possibile interagire su twitter, oppure accendere una lampadina smart di colore verde se si vince o di rosso se si perde.

E' una cosa che mi è parsa sfiziosa da implementare.

# Upgrade dalla versione precedente
La versione 0.2 salva la dimensione di JBriscoFrame calcolata nelle opzioni, per cui se si apre il gioco avendo la versione del file delle opzioni del 0.1, il frame non comparirà, avendo dimensione nulla.
E' necessario cancellare il file delle opzioni della 0.1 prima di avviare la 0.2.

# Sviluppi futuri
Oltre a risolvere i bug grafici in ambiente windows, è opportuno effettuare la derivazione delle classi helper per sfruttare i socket al fine di ottenere un multiplayer alla tetrinet.
Se volete farlo, siete liberi di poterlo sviluppare e di mandarmi i sorgenti come pull request, sarà mia premura mettervi tra gli sviluppatori del programma.
Se, invece, volete produrre traduzioni di qualsiasi genere, siete comunque liberi di mandarmele, sempre facendo la pull request, in questo modo verrete inseriti tra i traduttori del programma
