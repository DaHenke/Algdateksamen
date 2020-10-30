package no.oslomet.cs.algdat.Eksamen;

import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>                                    // en indre nodeklasse
    {
        private T verdi;                                                  // nodens verdi
        private Node<T> venstre, høyre;                                   // venstre og høyre barn
        private Node<T> forelder;                                         // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)                           // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                                                   // peker til rotnoden
    private int antall;                                                    // antall noder
    private int endringer;                                                 // antall endringer

    private final Comparator<? super T> comp;                              // komparator

    public EksamenSBinTre(Comparator<? super T> c)                         // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;                                    //hvis verdien som blir tatt inn er null returnerer metoden false

        Node<T> p = rot;                                                    //start i roten

        while (p != null) {                                                 //holder på så lenge noden vi er på har en verdi
            int cmp = comp.compare(verdi, p.verdi);                         //cmp blir enten -1, 0 eller 1
            if (cmp < 0) p = p.venstre;                                     //hvis mindre enn 0 går vi til venstre barn
            else if (cmp > 0) p = p.høyre;                                  //hvis større enn 0 går vi til høyre barn
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }                                   //returnerer antall noder i treet

    public String toStringPostOrder() {
        if (tom()) return "[]";                                              //hvis treet er tomt får vi returnert strengen []

        StringJoiner s = new StringJoiner(", ", "[", "]");//opprettet en stringjoiner med start-, slutt- og

        Node<T> p = førstePostorden(rot);                                    // går til den første i postorden
        while (p != null) {                                                  //holder på så lenge noden vi er på har en verdi
            s.add(p.verdi.toString());                                       //legger til nodens verdi i strengen
            p = nestePostorden(p);                                           //går til den neste noden i postorden
        }
        return s.toString();                                                 //returnerer den totale strengen
    }

    public boolean tom() {
        return antall == 0;
    }                             //setter antall til å være 0

    public boolean leggInn(T verdi) {                                        //Brukt kode fra kompendiet til Ulf Uttersrud, kapittel 5.2.3 a)
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> gjeldende = rot, temp = null;                                //starter i roten
        int comparator = 0;
        while(gjeldende != null){                                            //fortsetter til noden vi er på er ute av treet
            temp = gjeldende;                                                //temp er forelder til gjeldende
            comparator = comp.compare(verdi,gjeldende.verdi);                //sammenligner verdiene
            gjeldende = comparator <0 ? gjeldende.venstre : gjeldende.høyre; //går enten til venstre eller høyre barn
        }

        gjeldende = new Node<>(verdi,temp);                                  //oppretter en ny node med verdien i seg og forelderen er den forrige noden

        if(temp == null){                                                    //oppretter en rotnode
            rot = gjeldende;
        }
        else if (comparator < 0){
            temp.venstre = gjeldende;                                        //oppretter venstre barn til noden vi var på
        }
        else{
            temp.høyre = gjeldende;                                          //oppretter høyre barn til noden vi var på
        }

        antall++;                                                            //antall noder i treet øker med én
        return true;
    }

    public boolean fjern(T verdi) {                                          //Brukt kode fra kompendium til Uld Uttersrud, kapittel 5.2.8 d).
        if(!tom() && inneholder(verdi)) {                                    //Hvis treet ikke er tomt og treet inneholder verdien vi ser etter kjører vi metoden
            if (verdi == null) return false;                                 // treet har ingen nullverdier

            Node<T> p = rot, q = null;                                       //q skal være p sin forelder

            while (p != null) {                                              //leter etter verdi
                int c = comp.compare(verdi, p.verdi);                        //sammenligner verdiene
                if (c < 0) {                                                 //går til venstre
                    q = p;
                    p = p.venstre;
                } else if (c > 0) {                                          //går til høyre
                    q = p;
                    p = p.høyre;
                } else break;                                                //den søkte verdien ligger i treet
            }
            if (p == null) return false;                                     //finner ikke verdi
            if (p.venstre == null || p.høyre == null) {                      //tilfelle 1) og 2)
                Node<T> barn = p.venstre != null ? p.venstre : p.høyre;      //oppretter barnet
                if (p == rot) {                                              //sletter rotnoden
                    rot = barn;
                    if(rot != null) {
                        rot.forelder = null;
                    }
                } else if (p == q.venstre) {                                 //sletter p og angir p sitt venstre barn til q
                    q.venstre = barn;
                    if (barn != null) {
                        barn.forelder = q;                                   //angir barnet til å ha noden over p som forelder
                    }
                } else {                                                     //sletter p og angir p sitt høyre barn til q
                    q.høyre = barn;
                    if (barn != null) {
                        barn.forelder = q;                                   //angir barnet til å ha noden over p som forelder
                    }
                }
            } else {                                                         //tilfelle 3)
                Node<T> s = p, r = p.høyre;                                  //finner neste i inorden
                while (r.venstre != null) {
                    s = r;                                                   //s er forelder til r
                    r = r.venstre;
                }
                p.verdi = r.verdi;                                           //kopierer verdien i r til p

                if (s != p) {
                    s.venstre = r.høyre;
                } else {
                    s.høyre = r.høyre;
                }
            }
            antall--;                                                        //reduserer antall noder i treet med én
            return true;
        }
        return false;
    }

    public int fjernAlle(T verdi) {
        if(!inneholder(verdi)){                                              //hvis treet ikke inneholder verdien sletter vi ingen elementer
            return 0;
        }

        int antallfjernet = 0;
        Node<T> p = rot;                                                     //starter i roten

        if(antall == 1){                                                     //hvis det bare er en node i treet
            if(p.verdi == verdi){                                            //sjekker om noden har verdien og sletter hvis det er tilfellet
                fjern(p.verdi);
                antallfjernet++;
            }
            else{
                return 0;
            }
            return antallfjernet;
        }
        while(p != null){                                                    //fortsetter nedover i treet til vi er ute av det
            if(p.venstre == null && p.høyre == null){                        //hvis vi er på en bladnode går vi ut av løkken
                break;
            }
            if(comp.compare(verdi,p.verdi)>=0){                              //hvis nodens verdi er mindre enn eller lik verdien fortsetter vi til høyre
                if(p != rot){
                    p = p.høyre;
                }
                if(p.verdi == verdi) {                                       //hvis noden har verdien sletter vi noden
                    fjern(p.verdi);
                    antallfjernet++;
                }
                if(p.høyre != null) {                                        //fortsetter til høyre så lenge noden har høyre barn
                    p = p.høyre;
                }
            }else{                                                           //hvis nodens verdi er større enn verdien går vi til venstre
                p = p.venstre;
                if(p.verdi == verdi){                                        //hvis noden har verdien sletter vi noden
                    fjern(p.verdi);
                    antallfjernet++;
                }
            }

        }
        return antallfjernet;
    }

    public int antall(T verdi) {
        if(antall() > 0 && inneholder(verdi)) {                              //hvis treet ikke er tomt og treet inneholder verdien kjører vi koden
            int forekomster = 0;
            Node<T> gjeldende = rot;                                         //starter i roten
            while (gjeldende != null) {                                      //så lenge vi er i treet
                if (comp.compare(verdi, gjeldende.verdi) > 0){               //hvis nodens verdi er mindre enn verdien vi ser etter går vi bare til høyre
                    gjeldende = gjeldende.høyre;
                }else if (comp.compare(verdi,gjeldende.verdi) < 0){          //hvis nodens verdi er større enn verdien vi ser etter går vil bare til venstre
                    gjeldende = gjeldende.venstre;
                }else{                                                       //hvis nodens verdi er lik verdien øker vi antall forekomster med én og går til høyre
                    forekomster++;
                    gjeldende = gjeldende.høyre;
                }
            }
            return forekomster;
        }else {
            return 0;
        }
    }

    public void nullstill() {
        Node<T> p = førstePostorden(rot);                                    //starter med å finne den ytterst venstre noden
        while (p != null) {                                                  //så lenge vi er i treet gjør vi følgende;
            p.venstre = null;                                                //sletter venstre barn
            p.høyre = null;                                                  //sletter høyre barn
            if(p == rot){                                                    //hvis vi er på rotnoden sletter vi den
                rot = null;
            }
            antall--;                                                        //reduserer antall noder i treet for hver sletting med én
            p = nestePostorden(p);                                           //traverserer videre gjennom treet i postorden
        }
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {                  //Brukt kode fra kompendiet til Ulf Uttersrud, kapittel 5.1.7 g)
        while (p != null) {                                                  //metoden kjører så lenge vi er i treet
            if (p.venstre != null) {                                         //hvis p har et venstre barn går vi til venstre
                p = p.venstre;
            }
            else if (p.høyre != null){                                       //hvis p ikke har et venstre barn, men har et høyre barn går vi til høyre
                p = p.høyre;
            }
            else{                                                            //vi er på den ytterst venstre bladnoden
                return p;
            }
        }
        return null;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {                   //Leste gjennom regel for den neste i Postorden i Kompendium til Ulf Uttersrud kapittel 5.1.7. Kode skrevet selv.
        if (p.forelder == null) {                                            //vi er på roten
            return null;
        } else if (p == p.forelder.høyre) {                                  //hvis vi er på høyre barnet går vi opp i treet igjen
            p = p.forelder;
        }else if (p == p.forelder.venstre) {                                 //hvis vi er på venstre barn
            if (p.forelder.høyre == null) {                                  //sjekker om forelder har et høyre barn, hvis ikke går vi opp i treet
                p = p.forelder;
            } else {                                                         //hvis forelder har et høyre barn går vi på det høyre barnet
                p = p.forelder.høyre;
                while(p.venstre != null){                                    //ser om noden har venstre barn igjen og går ned på det hvis det er tilfellet
                    p = p.venstre;
                }while(p.høyre != null){                                     //ser om noden har høyre barn og går ned på det hvis det er tilfellet
                    p = p.høyre;
                }
            }
        }
        return p;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = førstePostorden(rot);                                    //kaller på førstePostorden får å finne ytterst venstre node

        while (p != null) {                                                  //så lenge vi er i treet
            oppgave.utførOppgave(p.verdi);                                   //henter nodens verdi
            p = nestePostorden(p);                                           //går på neste node i postorden
        }
    }


    public void postordenRecursive(Oppgave<? super T> oppgave) {             //kaller på annen metode som traverserer treet i postorden med rekursive kall
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) { //kode fra kompendium til Ulf Uttersrud, til rekursiv metode for preorden (5.1.7a) og inorden (5.1.7d).
        if(p.venstre != null){                                               //går til venstre barnet
            postordenRecursive(p.venstre,oppgave);
        }
        if(p.høyre != null){                                                 //går til høyre barn
            postordenRecursive(p.høyre,oppgave);
        }
        oppgave.utførOppgave(p.verdi);                                       //utfører oppgaven
    }

    public ArrayList<T> serialize() {                                        //sett på kode, for iterativ traversing med nivåorden, i kompendium til Ulf Uttersrud kapittel 5.1.6.
        if(tom()){                                                           //hvis treet er tomt returnerer vi ingenting
            return null;
        }

        ArrayList<T> array = new ArrayList<>();                              //oppretter en ArrayList

        Node<T> p = rot;                                                     //p starter i roten

        Queue<Node<T>> queue = new ArrayDeque<>();                           //oppretter en kø
        queue.add(p);                                                        //legger roten inn i køen

        while(!queue.isEmpty()){                                             //så lenge køen ikke er tom kjører løkken
            p = queue.poll();                                                //henter den første noden i køen og sletter den fra køenv
            array.add(p.verdi);                                              //legger denne nodens verdi i ArrayListen

            if(p.venstre != null)queue.add(p.venstre);                       //hvis noden har et venstre barn legger vi barnet i køen
            if(p.høyre != null)queue.add(p.høyre);                           //hvis noden har et høyre barn legger vi barnet i køen
        }
        return array;                                                        //returnerer ArrayListen
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> tree = new EksamenSBinTre<>(c);                    //Oppretter et nytt BST

        for(int i = 0; i < data.size(); i++){                                //for-løkke som kjører gjennom hele ArrayListen metoden tar inn
            tree.leggInn(data.get(i));                                       //legger hver verdi i ArrayListen i hver sin node og legger noden inn i treet
        }
        return tree;                                                         //returnerer treet
    }
} // ObligSBinTre
