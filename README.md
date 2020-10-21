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
             