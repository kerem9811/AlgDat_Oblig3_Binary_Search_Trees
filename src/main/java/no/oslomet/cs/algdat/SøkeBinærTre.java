package no.oslomet.cs.algdat;

import java.util.*;

public class SøkeBinærTre<T>  implements Beholder<T> {

    // En del kode er ferdig implementert, hopp til linje 91 for Oppgave 1

    private static final class Node<T> { // En indre nodeklasse
        private T verdi; // Nodens verdi
        private Node<T> venstre, høyre, forelder; // barn og forelder

        // Konstruktører
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> f) {
            this.verdi = verdi;
            venstre = v; høyre = h; forelder = f;
        }
        private Node(T verdi, Node<T> f) {
            this(verdi, null, null, f);
        }

        @Override
        public String toString() {return verdi.toString();}
    } // class Node

    private final class SBTIterator implements Iterator<T> {
        Node<T> neste;
        public SBTIterator() {
            neste = førstePostorden(rot);
        }

        public boolean hasNext() {
            return (neste != null);
        }

        public T next() {
            Node<T> denne = neste;
            neste = nestePostorden(denne);
            return denne.verdi;
        }
    }

    public Iterator<T> iterator() {
        return new SBTIterator();
    }

    private Node<T> rot;
    private int antall;
    private int endringer;

    private final Comparator<? super T> comp;

    public SøkeBinærTre(Comparator<? super T> c) {
        rot = null; antall = 0;
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

    public int antall() { return antall; }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot);
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() { return antall == 0; }

// Oppgave 1 ------------------------------------------------------------------------------------------------
/*Oppgave 1 (Legge inn verdier)
En Node har referanser til sine barn og sin forelder. Forelder må få riktig verdi ved hver innlegging,
men rotnoden skal ha null som sin forelder. Lag metoden public boolean leggInn(T verdi), som legger inn en
verdi riktig sted i treet. En null-verdi er ikke lov, og skal kaste en NullPointerException.
Du kan se på koden i kapittel 5.2 men må gjøre endringene som trengs for at forelder-pekeren får korrekt verdi for hver node*/
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Verdi kan ikke være null-peker!");

        Node<T> p = rot, q = null;                  // Starter i rot
        int cmp = 0;                                // Hjelpevariabel

        while (p != null) {                         // Fortsetter til p er ute av treet
            q = p;                                  // q er p sin forelder
            cmp = comp.compare(verdi, p.verdi);     // sammenligner med komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;      // flytter p
        }                                           // p er nå null, altså ute av treet. q er den siste vi passerte

        p = new Node<>(verdi, q);                   // Oppretter ny node på p-pekeren, med q som forelder
        if (q == null) rot = p;                     // Hvis q er null, blir p rotnoden
        else if (cmp < 0) {                         // Hvis verdi er mindre, blir det venstrebarn
            q.venstre = p;
        } else {                                    // Hvis verdi er lik eller større blir det høyrebarn
            q.høyre = p;
        }

        endringer++;                                // Inkrementerer endringer
        antall++;                                   // og antall
        return true;
    }

// Oppgave 2 ------------------------------------------------------------------------------------------------
/*Oppgave 2 (Antall duplikater)
Metodene inneholder(), antall(), og tom() er allerede kodet. Treet tillater
duplikater, så en verdi kan forekomme flere ganger. Lag kode for den nye metoden
antall(T verdi), som teller hvor mange ganger verdi dukker opp i treet. Om
en verdi ikke er i treet (inkludert om verdien er null) skal metoden returnere 0.*/
    public int antall(T verdi){
        if (tom() || antall == 0 || verdi == null) return 0;
                                                        // Hvis verdi ikke finnes blir det 0

        Node<T> p = rot;                                // Begynner i rot
        int teller = 0;                                 // Initialiserer en teller

        while (p != null) {                             // Fortsetter til p blir null
            int cmp = comp.compare(verdi, p.verdi);     // Sammenligner verdien med nodens verdi
            if (cmp < 0) p = p.venstre;                 // Gå til venstre hvis mindre
            else {                                      // Gå til høyre hvis lik eller større
                if (cmp == 0) teller++;                 // Hvis lik øk teller
                p = p.høyre;                            // Gå til høyre
            }
        }
        return teller;
    }

// Oppgave 3 ------------------------------------------------------------------------------------------------
/*Oppgave 3 (Postorden)
Lag hjelpemetodene private Node førstePostorden(Node p) og private Node nestePostorden(Node p).
Da metodene er private, kan vi anta at parameteren p ikke er null, da det antas at vi passer på
 at vi ikke sender inn null til disse metodene. Metoden førstePostorden skal returnere første node i postorden som har
p som rot, og nestePostorden skal returnere noden skom kommer etter p i postorden.
Hvis p er den siste noden i postorden, skal metoden returnere null.*/
    private Node<T> førstePostorden(Node<T> p) {

        throw new UnsupportedOperationException();
    }

    private Node<T> nestePostorden(Node<T> p) {

        throw new UnsupportedOperationException();
    }

// Oppgave 4 ------------------------------------------------------------------------------------------------
/*Oppgave 4 (Utføre Oppgave i Postorden)
Lag hjelpemetodene public void postorden(Oppgave <? super T> oppgave) og
private void postordenRekursiv(Node p, Oppgave<? super T> oppgave)som brukes til å utføre en Oppgave.
Oppgaven kan for eksempel være å skrive noe til skjerm, og da vil denne metoden skrive ut treet i postorden.
Den første av disse metodene skal implementeres uten bruk av rekursjon, og uten bruk av hjelpestrukturer
som en stack/stabel eller queue/kø. Du skal i stedet bruke funksjonen nestePostorden fra forrige oppgave.
For den rekursive metoden skal du lage et rekursivt kall som traverserer treet i postorden-rekkefølge. */
    public void postOrden(Oppgave<? super T> oppgave) {

        throw new UnsupportedOperationException();
    }

    public void postOrdenRekursiv(Oppgave<? super T> oppgave) {
        postOrdenRekursiv(rot, oppgave); // Ferdig implementert
    }

    private void postOrdenRekursiv(Node<T> p, Oppgave<? super T> oppgave) {

        throw new UnsupportedOperationException();
    }

// Oppgave 5 ------------------------------------------------------------------------------------------------
/*Oppgave 5 (Fjerne element)
Lag metoden public boolean fjern(T verdi). Du kan se på koden i kapittel 5.2.8,
men må gjøre endringene som trengs for at forelder-pekeren får rett verdi.
Lag så metoden public int fjernAlle(T verdi). Denne skal fjerne alle forekomster av en verdi i treet,
og returnere antallet som ble fjernet. Om treet ikke inneholder noen forekomster (inkludert om treet er tomt)
skal metoden returnere 0. Lag til slutt metoden public void nullstill().
Den skal gå gjennom treet og passe på at alle nodepekere og nodeverdier i treet blir nullet ut.
Det er ikke tilstrekkelig å kun sette rot til null og antall til 0. */
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException();
    }

    public void nullstill() {
        throw new UnsupportedOperationException();
    }
}