package no.oslomet.cs.algdat.Eksamen;

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

    public boolean fjern(T verdi) { // Brukt kode fra kompendium til Uld Uttersrud, kapittel 5.2.8 d).
        if(!tom() && inneholder(verdi)) {
            if (verdi == null) return false;

            Node<T> p = rot, q = null;

            while (p != null) {
                int c = comp.compare(verdi, p.verdi);
                if (c < 0) {
                    q = p;
                    p = p.venstre;
                } else if (c > 0) {
                    q = p;
                    p = p.høyre;
                } else break;
            }
            if (p == null) return false;
            if (p.venstre == null || p.høyre == null) {
                Node<T> barn = p.venstre != null ? p.venstre : p.høyre;
                if (p == rot) {
                    rot = barn;
                    if(rot != null) {
                        rot.forelder = null;
                    }
                } else if (p == q.venstre) {
                    q.venstre = barn;
                    if (barn != null) {
                        barn.forelder = q;
                    }
                } else {
                    q.høyre = barn;
                    if (barn != null) {
                        barn.forelder = q;
                    }
                }
            } else {
                Node<T> s = p, r = p.høyre;
                while (r.venstre != null) {
                    s = r;
                    r = r.venstre;
                }
                p.verdi = r.verdi;

                if (s != p) {
                    s.venstre = r.høyre;
                } else {
                    s.høyre = r.høyre;
                }
            }
            antall--;
            return true;
        }
        return false;
    }

    public int fjernAlle(T verdi) {
        if(!inneholder(verdi)){
            return 0;
        }

        int antallfjernet = 0;
        Node<T> p = rot;

        if(antall == 1){
            if(p.verdi == verdi){
                fjern(p.verdi);
                antallfjernet++;
            }
            else{
                return 0;
            }
            return antallfjernet;
        }
        while(p != null){
            if(p.venstre == null && p.høyre == null){
                break;
            }
            if(comp.compare(verdi,p.verdi)>=0){
                if(p != rot){
                    p = p.høyre;
                }
                if(p.verdi == verdi) {
                    fjern(p.verdi);
                    antallfjernet++;
                }
                if(p.høyre != null) {
                    p = p.høyre;
                }
            }else{
                p = p.venstre;
                if(p.verdi == verdi){
                    fjern(p.verdi);
                    antallfjernet++;
                }
            }

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
        Node<T> p = førstePostorden(rot);
        while (p != null) {
            p.venstre = null;
            p.høyre = null;
            if(p == rot){
                rot = null;
            }
            antall--;
            p = nestePostorden(p);
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
} // ObligSBinTre
