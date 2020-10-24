package no.oslomet.cs.algdat.Eksamen;


import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }
        System.out.println(s.toString());
        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) { //Brukt kode fra kompendiet til Ulf Uttersrud, kapittel 5.2.3 a)
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> gjeldende = rot, temp = null;
        int comparator = 0;
        while(gjeldende != null){
            temp = gjeldende;
            comparator = comp.compare(verdi,gjeldende.verdi);
            gjeldende = comparator <0 ? gjeldende.venstre : gjeldende.høyre;
        }

        gjeldende = new Node<>(verdi,temp);

        if(temp == null){
            rot = gjeldende;
        }
        else if (comparator < 0){
            temp.venstre = gjeldende;
        }
        else{
            temp.høyre = gjeldende;
        }

        antall++;
        return true;
    }

    public boolean fjern(T verdi) {
        if(!tom() && inneholder(verdi)) {
            if (verdi == null) return false; //treet har ingen nullverdier

            Node<T> p = rot, q = null; // q skal være forelder til p

            while (p != null) {
                int c = comp.compare(verdi, p.verdi); // sammmenligner
                if (c < 0) { // går til venstre
                    q = p;
                    p = p.venstre;
                } else if (c > 0) { // går til høyre
                    q = p;
                    p = p.høyre;
                } else break; // den søkte verdien ligger i p
            }
            if (p == null) return false; // finner ikke verdien
            if (p.venstre == null || p.høyre == null) { // tilfelle 1) og 2)
                Node<T> barn = p.venstre != null ? p.venstre : p.høyre; // b for barn
                if (p == rot) {
                    rot = barn;
                    if(rot != null) {
                        rot.forelder = null;
                    }
                } else if (p == q.venstre) {
                    q.venstre = barn;
                    if (barn != null) {
                        barn.forelder = q;
                        //System.out.println("Slettet: " + p.verdi + ", Slettet sin forelder: " + p.forelder.verdi + ", Barn: " + barn.verdi + ", Barn forelder: " + barn.forelder.verdi);
                    } else {
                        //System.out.println("Slettet: " + p.verdi + ", Slettet sin forelder: " + p.forelder.verdi + ", Barn: " + "null" + ", Barn forelder: " + q.verdi);
                    }
                } else {
                    q.høyre = barn;
                    if (barn != null) {
                        barn.forelder = q;
                        //System.out.println("Slettet: " + p.verdi + ", Slettet sin forelder: " + p.forelder.verdi + ", Barn: " + barn.verdi + ", Barn forelder: " + barn.forelder.verdi);
                    } else {
                        //System.out.println("Slettet: " + p.verdi + ", Slettet sin forelder: " + p.forelder.verdi + ", Barn: " + "null" + ", Barn forelder: " + q.verdi);
                    }
                }
            } else { //tilfelle 3
                Node<T> s = p, r = p.høyre; //finner neste i inorden
                while (r.venstre != null) {
                    s = r; // s er forelder til r
                    r = r.venstre;
                }
                p.verdi = r.verdi; //kopierer verdien i r til p

                if (s != p) {
                    s.venstre = r.høyre;
                } else {
                    s.høyre = r.høyre;
                }
            }
            antall--; // det er nå en node mindre i treet
            return true;
        }
        return false;
    }

    public int fjernAlle(T verdi) {
        int antallfjernet = 0;
        /*Node<T> p = rot;

        ArrayDeque<Node<T>> queue = new ArrayDeque<>();
        while(p != null) {
            if (p.verdi == verdi) {
                queue.add(p);
                System.out.println(p.verdi);
                if (p.høyre != null) {
                    while (!queue.isEmpty()) {
                        if (p.høyre != null) {
                            p = p.høyre;
                            if (p.verdi == verdi) {
                                queue.add(p);
                                System.out.println(p.verdi);
                            }
                        } else {
                            if (p.venstre != null) {
                                p = p.venstre;
                            }
                            if (p.verdi == verdi) {
                                queue.add(p);
                                System.out.println(p.verdi);
                            }
                        }
                    }
                }
            } else if (comp.compare(verdi, p.verdi) < 0) {
                if (p.venstre != null) {
                    p = p.venstre;
                }
                if (p.høyre != null) {
                    while (!queue.isEmpty()) {
                        if (p.høyre != null) {
                            p = p.høyre;
                            if (p.verdi == verdi) {
                                queue.add(p);
                                System.out.println(p.verdi);
                            }
                        }
                    }
                }
            }
            while(!queue.isEmpty()){
                Node<T> node = queue.pollLast();
                System.out.println(node.verdi);
            }
        }*/
        while(inneholder(verdi)){
            fjern(verdi);
            antallfjernet++;
        }
        return antallfjernet;
    }

    public int antall(T verdi) {
        if(antall() > 0 && inneholder(verdi)) {
            int forekomster = 0;
            Node<T> gjeldende = rot;
            while (gjeldende != null) {
                if (comp.compare(verdi, gjeldende.verdi) > 0){
                    gjeldende = gjeldende.høyre;
                }else if (comp.compare(verdi,gjeldende.verdi) < 0){
                    gjeldende = gjeldende.venstre;
                }else{
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
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
        while(!tom()){
            fjern(førstePostorden(rot).verdi);
            nullstill();
        }
    }

    private static <T> Node<T> førstePostorden(Node<T> p) { //Brukt kode fra kompendiet til Ulf Uttersrud, kapittel 5.1.7 g)
        while (p != null) {
            if (p.venstre != null) {
                p = p.venstre;
            }
            else if (p.høyre != null){
                p = p.høyre;
            }
            else{
                return p;
            }
        }
        return null;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) { //Leste gjennom regel for den neste i Postorden i Kompendium til Ulf Uttersrud kapittel 5.1.7. Kode skrevet selv.
        if (p.forelder == null) {
            return null;
        } else if (p == p.forelder.høyre) {
            p = p.forelder;
        }else if (p == p.forelder.venstre) {
            if (p.forelder.høyre == null) {
                p = p.forelder;
            } else {
                p = p.forelder.høyre;
                while(p.venstre != null){
                    p = p.venstre;
                }while(p.høyre != null){
                    p = p.høyre;
                }
            }
        }
        return p;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = førstePostorden(rot);

        while (p != null) {
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p);
        }
    }


    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if(p.venstre != null){
            postordenRecursive(p.venstre,oppgave);
        }
        if(p.høyre != null){
            postordenRecursive(p.høyre,oppgave);
        }
        oppgave.utførOppgave(p.verdi);
    }

    public ArrayList<T> serialize() {
        if(tom()){
            return null;
        }

        ArrayList<T> array = new ArrayList<>();

        Node<T> p = rot;

        Queue<Node<T>> queue = new ArrayDeque<>();
        queue.add(p);

        while(!queue.isEmpty()){
            p = queue.poll();
            array.add(p.verdi);

            if(p.venstre != null)queue.add(p.venstre);
            if(p.høyre != null)queue.add(p.høyre);
        }
        return array;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> tree = new EksamenSBinTre<>(c);

        for(int i = 0; i < data.size(); i++){
            tree.leggInn(data.get(i));
        }
        return tree;
    }

    public static void main(String[] args){
        //EksamenSBinTre<String> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        //System.out.println(tre.antall);

        System.out.println("--------------Oppgave 1--------------");
        Integer[] a1 = {4,7,2,9,5,10,8,1,3,6};
        EksamenSBinTre<Integer> tre1 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a1) tre1.leggInn(verdi);
        System.out.println(tre1.antall());


        System.out.println("--------------Oppgave 2--------------");
        Integer[] a2 = {4,7,2,9,4,10,8,7,4,6};
        EksamenSBinTre<Integer> tre2 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for(int verdi : a2) tre2.leggInn(verdi);

        System.out.println(tre2.antall());
        System.out.println(tre2.antall(5));
        System.out.println(tre2.antall(4));
        System.out.println(tre2.antall(7));
        System.out.println(tre2.antall(10));

        System.out.println("--------------Oppgave 5--------------");
        Integer[] a5 = {4,7,2,9,4,10,8,7,4,6};
        EksamenSBinTre<Integer> tre5 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for(int verdi : a5) {
            System.out.print(verdi+", ");
            tre5.leggInn(verdi);
        }
        System.out.println();
        tre5.serialize();
        deserialize(tre5.serialize(), Comparator.naturalOrder());

        System.out.println("--------------Oppgave 6--------------");
        int[] a6 = {4,7,2,9,4,10,8,7,4,6,1};
        EksamenSBinTre<Integer> tre6 = new EksamenSBinTre<>(Comparator.naturalOrder());
        for(int verdi : a6) tre6.leggInn(verdi);
        System.out.println(tre6.fjernAlle(4));
        tre6.fjernAlle(7);tre6.fjern(8);

        System.out.println(tre6.antall());

        System.out.println(tre6 + " " + tre6.toString());

        tre6.nullstill();
        System.out.println(tre6.toStringPostOrder());
        /*EksamenSBinTre<Integer> tre6 = new EksamenSBinTre<>(Comparator.naturalOrder());
        int[] a = {6, 3, 9, 1, 5, 7, 10, 2, 4, 8, 11, 6, 8};
        for (int verdi : a) tre6.leggInn(verdi);
        System.out.println("Antall: "+tre6.antall());
        System.out.println(tre6.toStringPostOrder());
        tre6.fjern(10);
        System.out.println(tre6.toStringPostOrder());

        tre6.fjernAlle(8);
        System.out.println(tre6.toStringPostOrder());

        tre6.nullstill();
        System.out.println(tre6.toStringPostOrder());*/
    }



} // ObligSBinTre
