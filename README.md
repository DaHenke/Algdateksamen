# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet


# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Vi har brukt git til å dokumentere arbeidet vårt. Jeg har 16 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

* Oppgave 1: Løste oppgaven ved å bruke kode fra kompendium til Ulf Uttersrud, kapittel 5.2.3 a). Metoden starter i roten. Starter med å flytte p til venstre eller høyre, avhenig av om verdien vi skal legge inn er mindre enn eller lik p. q setter vi til å være p sin forelder.
             Fortsetter til p er ute av treet og q er den siste vi passerte. Oppretter så en ny node med q som forelder. Hvis q er null settes roten til å være p. Hvis verdien er mindre enn q settes vi den til å være venstre barn. Hvis større setter vi den til høyre barn. antall øker deretter med 1.
* Oppgave 2: Løste ved å først sette opp en if-setning som sjekker om binærtreet er tomt eller ikke og inneholder verdien eller ikke. Hvis det er tomt returneres 0.
             Deretter initialiserer jeg en teller, som skal øke hvis metoden finner verdien jeg ser etter, og en hjelpenode for å traversere gjennom treet.
             Når hjelpenoden ikke er null starter jeg i rotnoden. 
             Hvis nodeverdien som sjekkes er mindre enn "verdi"-parameteren fortsetter vi på dens høyre barn og telleren øker ikke.
             Hvis nodeverdien som sjekkes er større enn "verdi"-parameteren fortsetter vi på dens venstre barn og telleren øker ikke.
             Hvis ikke ser jeg om noden har verdien eller ikke. Hvis den er lik verdien øker telleren og fortsetter ned på høyre side.
             Til slutt returneres telleren med antall forekomster av verdien som er etterspurt. 
* Oppgave 3: Løste ved å se på regel for den neste i Postorden i Kompendium til Ulf Uttersrud kapittel 5.1.7 og brukte koden fra 5.1.7 g).
             førstePostOrden: Når p ikke er null sjekker vi først om noden har et venstre barn. Hvis den har det går den til venstre barn. 
             Hvis ikke sjekker vi om den har et høyre barn. Hvis den har det går den til høyre barn. Deretter sjekker den om dette barnet har et venstre barn eller et høyre barn igjen.
             Hvis noden hverken har venstre eller høyre barn returneres p, som er noden nestePostorden starter på. 
             nestePostOrden starter med å sjekke om vi er på rot-noden. Hvis p er et høyre barn til sin forelder er forelder neste.
             Hvis p er venstre barnet til sin forelder sjekker vi om forelder har flere barn. Hvis den ikke har høyre barn er forelder neste.
             Hvis forelder har høyre barn går vi ned til sistnevnte barn og sjekker om den har et venstre barn.
* Oppgave 4: Løste ved å implementere kode, fra kompendium til Ulf Uttersrud, til rekursiv metode for preorden (5.1.7a) og inorden (5.1.7d). 
             For postorden() satte jeg først hjelpenoden p til å være den ytterste noden i treet, ved å kalle på førstePostorden() med rot som inn-parameter. (Se forklaring fir førstePostorden i oppgave 3).
             Deretter satte jeg en while-løkke slik oppgaveteksten ber om som kjører så lenge p ikke er null. 
             I denne løkken henter jeg verdien til noden jeg befinner meg på, og går deretter videre til neste node.
             Den neste noden finner jeg ved å kalle på nestePostorden() med nåværende node som inn-parameter. (Se forklaring for nestePostorden i oppgave 3.)
             For den rekursive metoden fulgte jeg kode-eksemplet til rekursive metode for preorden og inorden. Her endret jeg dermed på koden ved å følge den rekursive definisjon for postorden.
             Først starter jeg i rotnoden og ser om den har et venstre-barn, og hvis den har det går jeg ned til det barnet. Hvis ikke sjekker jeg om den har et høyre-barn og gjør det samme.
             Slik fortsetter metoden til den ytterst venstre noden ikke har barn. Deretter kaller jeg på oppgave som henter verdien til noden.
* Oppgave 5: Løste serialize ved å se på kode, for iterativ traversing med nivåorden, i kompendium til Ulf Uttersrud kapittel 5.1.6.
             For denne metoden sjekker jeg først om treet jeg tar er tomt eller ikke. Hvis ikke oppretter jeg en ArrayList som skal inneholde alle verdiene fra et binært tre. 
             Deretter oppretter jeg en kø som skal traversere gjennom det binære treet. Først legger jeg inn roten i køen, og deretter (hvis køen ikke er tom) henter jeg noden fra køen og sletter den fra køen.
             Verdien til noden blir så lagt inn i ArrayList. Etter dette traverserer jeg meg videre gjennom treet og legger til eventuelle venstre- og høyre-barn samtidig, for så å repetere samme prosess som beskrevet i de forrige setningene.
             Til slutt i metoden returneres en ferdig fylt ut ArrayList.
             For deserialize opprettes først et nytt Binært tre som tar inn comparatoren c. Deretter bruker jeg en for-løkke som går gjennom ArrayListen jeg tar inn, og legger inn nye noder per index ved å kalle på leggInn-metoden fra oppgave 1 (se beskrivelse for oppgave 1).
             Til slutt returnerer jeg det fullstendige binære treet.
* Oppgave 6: Løste oppgaven ved å implementere kode i fjern(T) fra kompendium til Ulf Uttersrud, kapittel 5.2.8 d). 
             I denne metoden sjekker jeg først om treet er tomt og om det inneholder verdien som er ønsket fjernet. Først oppretter jeg en hjelpenode, for så å traversere gjennom arrayet til vi finner den først noden som inneholder verdien. Hvis vi ikke finner verdien returnerer metoden false.
             Deretter sjekker vi de ulike tilfellene vi kan får; 1) p har ingen barn. Hvis p er roten settes rot og dens forelder til å være null. Hvis p er et venstre barn vil den ha forelder q. Hvis p er venstre barn til q settes q.venstre til null. Det samme blir gjort hvis per høyre barn.
             2) p har nøyaktig ett barn. Her settes p sine barn til å være q sine barn, og foreldre-referansen til p fjernes. 3) p har to barn. Her finner vi neste i inorden. Hvis r har et venstre barn setter vi s til å være r sin forelder, og r til å være venstre barnet. Vi kopierer så verdien i r til p.
             Til slutt sjekker vi om s og p er samme node, og setter r sitt høyre barn til å være s sitt venstre barn eller høyre barn.
             For fjernAlle(T) sjekker jeg først om treet inneholder verdien. Hvis ikke returneres 0. Deretter sjekker jeg om det bare er en node i treet og om denne noden er i treet eller ikke. Metoden sjekker så om verdien ligger på venstre eller høyre side og fjerner verdien når noden p har verdien. antallfjernet øker ved hver fjerning.
             nullstill() traverserer gjennom treet i postorden ved å først finne den ytterst venstre noden. Denne nodens venstre og høyre barn blir nullet ut, og metoden traverserer videre ved å kalle på nestePostorden. antall blir redusert for hver fjerning og når metoden kommer til rot blir den nullet ut.