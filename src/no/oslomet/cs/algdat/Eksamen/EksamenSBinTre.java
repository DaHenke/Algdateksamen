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
            System.out.print(gjeldende.verdi +", ");
            rot = gjeldende;
        }
        else if (comparator < 0){
            System.out.print(gjeldende.verdi +", ");
            temp.venstre = gjeldende;
        }
        else{
            System.out.print(gjeldende.verdi +", ");
            temp.høyre = gjeldende;
        }

        antall++;
        return true;
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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
        } else if (p == p.forelder.venstre) {
            if (p.forelder.høyre == null) {
                p = p.forelder;
            } else {
                p = p.forelder.høyre;
                while(p.venstre != null){
                    p = p.venstre;
                }
            }
        }
        return p;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
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

    }



} // ObligSBinTre
