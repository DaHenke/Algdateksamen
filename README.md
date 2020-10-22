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

* Oppgave 1: Løste ved å implementere ...
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