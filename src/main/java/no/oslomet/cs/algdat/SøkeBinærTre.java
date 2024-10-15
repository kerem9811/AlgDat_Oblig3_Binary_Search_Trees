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

        Node<T> pekerNode = rot;                                    // Starter pekernode i rot
        Node<T> forelderNode = null;                                // Lager forelderpeker
        int sammenligner = 0;                                       // Hjelpevariabel

        while (pekerNode != null) {                                 // Fortsetter til pekernode er ute av treet
            forelderNode = pekerNode;                               // Oppdaterer forelderpekeren
            sammenligner = comp.compare(verdi, pekerNode.verdi);    // sammenligner med komparatoren

            if (sammenligner < 0) {                                 // Hvis verdi er mindre, gå til venstre
                pekerNode = pekerNode.venstre;
            } else {                                                // Hvis verdi er større eller lik, gå til høyre
                pekerNode = pekerNode.høyre;
            }
        }

        pekerNode = new Node<>(verdi, forelderNode);                // Oppretter ny node på pekernoden, med foreldernode

        if (forelderNode == null){
            rot = pekerNode;                                        // Hvis foreldernoden er null, blir pekernoden rotnoden
        }

        else if (sammenligner < 0) {
            forelderNode.venstre = pekerNode;                       // Hvis verdi er mindre, blir det venstrebarn

        } else {
            forelderNode.høyre = pekerNode;                         // Hvis verdi er lik eller større blir det høyrebarn
        }

        endringer++;                                                // Inkrementerer endringer
        antall++;                                                   // og antall
        return true;
    }

// Oppgave 2 ------------------------------------------------------------------------------------------------
/*Oppgave 2 (Antall duplikater)
Metodene inneholder(), antall(), og tom() er allerede kodet. Treet tillater
duplikater, så en verdi kan forekomme flere ganger. Lag kode for den nye metoden
antall(T verdi), som teller hvor mange ganger verdi dukker opp i treet. Om
en verdi ikke er i treet (inkludert om verdien er null) skal metoden returnere 0.*/
    public int antall(T verdi){
        if (tom() || antall == 0 || verdi == null){
            return 0;                                           // Hvis verdi ikke finnes returneres 0
        }

        Node<T> pekerNode = rot;                                // Begynner i rot
        int teller = 0;                                         // Initialiserer en teller

        while (pekerNode != null) {                             // Fortsetter til pekernoden blir null
            int cmp = comp.compare(verdi, pekerNode.verdi);     // Sammenligner verdien med nodens verdi

            if (cmp < 0){
                pekerNode = pekerNode.venstre;                  // Gå til venstre hvis mindre
            }
            else {                                              // Gå til høyre hvis lik eller større

                if (cmp == 0) teller++;                         // Hvis lik øk teller
                pekerNode = pekerNode.høyre;                    // Gå til høyre
            }
        }
        return teller;
    }

// Oppgave 3 --------------------------------------------------------------------------------------
/*Oppgave 3 (Postorden)
Lag hjelpemetodene private Node førstePostorden(Node p) og private Node nestePostorden(Node p).
Da metodene er private, kan vi anta at parameteren p ikke er null, da det antas at vi passer på
 at vi ikke sender inn null til disse metodene. Metoden førstePostorden skal returnere første node i postorden som har
p som rot, og nestePostorden skal returnere noden som kommer etter p i postorden.
Hvis p er den siste noden i postorden, skal metoden returnere null.*/
    private Node<T> førstePostorden(Node<T> p) {
        if (p == null) return null;                 // Hvis null, returner null
        while (true) {                              // Går i loop til vi finner første node i postorden
            if (p.venstre != null) p = p.venstre;   // Hvis p har venstrebarn, gå dit
            else if (p.høyre != null) p = p.høyre;  // Hvis p derimot har høyrebarn, gå dit
            else return p;                          // Hvis ingen barn og er helt til venstre
        }
    }

    private Node<T> nestePostorden(Node<T> p) {
        if (p == null) return null;                         // Hvis null, returner null

        if (p.forelder == null) {                           // Hvis p ikke har forelder, så er den rot.
            return null;                                    // Rot er sist i postorden, ingen etter den, så returner null
        } else if (p == p.forelder.høyre || p.forelder.høyre == null) {
                                                            // Hvis p er høyre barn eller
            return p.forelder;                              // ikke har noen høyre søsken, gå opp til forelder
        } else {
            p = p.forelder.høyre;                           // Hvis p er venstre barn og har et høyre søsken
            while (p.venstre != null || p.høyre != null) {  // så bruker vi denne løkken til å traversere ned
                if (p.venstre != null) {                    // høyre søskens subtre for å finne noden lengst til venstre
                    p = p.venstre;
                } else {
                    p = p.høyre;
                }
            }                                               // Nå peker p på noden lengst til venstre i høyre søskens subtre
            return p;                                       // Denne noden er den neste i postorden.
        }
    }


    // Oppgave 4 ----------------------------------------------------------------------------------
/*Oppgave 4 (Utføre Oppgave i Postorden)
Lag hjelpemetodene public void postorden(Oppgave <? super T> oppgave) og
private void postordenRekursiv(Node p, Oppgave<? super T> oppgave)som brukes til å utføre en Oppgave.
Oppgaven kan for eksempel være å skrive noe til skjerm, og da vil denne metoden skrive ut treet i postorden.
Den første av disse metodene skal implementeres uten bruk av rekursjon, og uten bruk av hjelpestrukturer
som en stack/stabel eller queue/kø. Du skal i stedet bruke funksjonen nestePostorden fra forrige oppgave.
For den rekursive metoden skal du lage et rekursivt kall som traverserer treet i postorden-rekkefølge. */
    public void postOrden(Oppgave<? super T> oppgave) {
        Node<T> p = førstePostorden(rot);       // Bruker metodene fra forrige oppgave
        while (p != null) {                     // Så lenge første node i postorden fra rot ikke er null
            oppgave.utførOppgave(p.verdi);      // så utfører vi en generisk oppgave med p sin verdi
            p = nestePostorden(p);              // Så går vi videre til neste node, med metoden fra forrige oppgave
        }
    }

    public void postOrdenRekursiv(Oppgave<? super T> oppgave) {
        postOrdenRekursiv(rot, oppgave);        // Ferdig implementert
    }

    private void postOrdenRekursiv(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == null) return;                  // Unngå nullpeker
        postOrdenRekursiv(p.venstre, oppgave);  // Huskeregel postorden:
        postOrdenRekursiv(p.høyre, oppgave);    // Venstre - høyre - rot
        oppgave.utførOppgave(p.verdi);          // Derfor ligger utførOppgave til slutt
    }

// Oppgave 5 --------------------------------------------------------------------------------------
/*Oppgave 5 (Fjerne element)
Lag metoden public boolean fjern(T verdi). Du kan se på koden i kapittel 5.2.8,
men må gjøre endringene som trengs for at forelder-pekeren får rett verdi.
Lag så metoden public int fjernAlle(T verdi). Denne skal fjerne alle forekomster av en verdi i treet,
og returnere antallet som ble fjernet. Om treet ikke inneholder noen forekomster (inkludert om treet er tomt)
skal metoden returnere 0. Lag til slutt metoden public void nullstill().
Den skal gå gjennom treet og passe på at alle nodepekere og nodeverdier i treet blir nullet ut.
Det er ikke tilstrekkelig å kun sette rot til null og antall til 0. */
    public boolean fjern(T verdi) {
        if (verdi == null) return false;                            // Kan ikke fjerne null

        Node<T> pekerNode = rot;                                    // Starter i rot og finner noden vi skal fjerne fra
        Node<T> forelderNode = null;                                // Holde styr på foreldrenoden

        // Finne noden som skal fjernes -----------------------------------------------------------
        while (pekerNode != null) {                                 // Looper gjennom treet
            int cmp = comp.compare(verdi, pekerNode.verdi);         // Sammenligner input-verdi med nodens verdi

            if (cmp < 0) {                                          // Hvis verdi er mindre, gå til venstre
                forelderNode = pekerNode;                           // Oppdaterer foreldrenoden til å være pekernode
                pekerNode = pekerNode.venstre;                      // Oppdaterer aktuellNode til dens venstre barn

            } else if (cmp > 0) {                                   // Hvis verdi er større, gå til høyre
                forelderNode = pekerNode;                           // Oppdaterer foreldrenoden til å være pekernode
                pekerNode = pekerNode.høyre;                        // Oppdaterer pekernode til dens høyre barn

            } else break;                                           // Hvis cmp er 0, har vi funnet noden vi skal fjerne
        }

        if (pekerNode == null) return false;                        // Hvis pekernode er null, finnes ikke verdien i treet

        // Etter å ha funnet noden, må vi håndtere 3 tilfeller av fjerning
        // 1. pekernode har ingen barn, altså pekernode er en bladnode ****************************
        if (pekerNode.venstre == null && pekerNode.høyre == null) {

            if (pekerNode == rot) {                                 // pekernode er rot, treet blir tomt
                rot = null;

            } else if (pekerNode == forelderNode.venstre) {         // pekernode er foreldernoden sitt venstre barn
                forelderNode.venstre = null;

            } else {                                                // pekernode er foreldernoden sitt høyre barn
                forelderNode.høyre = null;
            }

        // 2. p har nøyaktig ett barn, enten venstre eller høyre **********************************
        } else if (pekerNode.venstre == null || pekerNode.høyre == null) {

            Node<T> barneNode = pekerNode.venstre;                  // barnenoden er høyre eller venstre barnet til pekernoden
            if (barneNode == null) {
                barneNode = pekerNode.høyre;
            }

            if (pekerNode == rot) {
                rot = barneNode;                                    // barnenoden blir ny rot
            } else if (pekerNode == forelderNode.venstre) {
                forelderNode.venstre = barneNode;                   // barnenoden erstatter pekernoden som foreldernoden sitt venstrebarn
            } else {
                forelderNode.høyre = barneNode;                     // barnenoden erstatter pekernoden som foreldernoden sitt høyrebarn
            }

            if (barneNode != null) {
                barneNode.forelder = forelderNode;                  // Oppdatere foreldrepekeren til barnenoden
            }
        }
        // 3. p har to barn, både venstre og høyre, bruke inorden-traversering ********************
        else {
            Node<T> etterfølgerForelder = pekerNode;                // Forelder til inorden-etterfølgeren til pekernoden
            Node<T> etterfølgerNode = pekerNode.høyre;              // Inorden-etterfølgeren til pekernoden er høyre barn

            while (etterfølgerNode.venstre != null) {               // Finner inorden-etterfølgeren til pekernoden (minste verdi i høyre subtre)
                etterfølgerForelder = etterfølgerNode;              // Oppdaterer etterfølgerforelderpekeren til å være forelder til etterfølgeren
                etterfølgerNode = etterfølgerNode.venstre;          // Flytter etterfølgernoden til å sitt venstre barn
            }

            pekerNode.verdi = etterfølgerNode.verdi;                // Kopierer verdien til inorden-etterfølgeren til pekernoden

            if (etterfølgerNode.høyre != null) {                    // Hvis etterfølgernoden har et høyre barn
                etterfølgerNode.høyre.forelder = etterfølgerForelder;// Oppdaterer forelderpekeren
            }

            if (etterfølgerForelder == pekerNode) {                 // Hvis forelder til etterfølger var pekernoden sitt høyre barn
                etterfølgerForelder.høyre = etterfølgerNode.høyre;  // Oppdaterer forelderpekeren

            } else {                                                // Hvis etterfølgerNode ikke var pekernoden sitt høyre barn
                etterfølgerForelder.venstre = etterfølgerNode.høyre;// Oppdaterer etterfølgerForelder sitt venstre barn til etterfølgerNode sitt høyre barn
            }
        }

        antall--;                                                   // Reduserer nodetelleren
        endringer++;                                                // Øker endrings-telleren
        return true;                                                // Returnerer true for å indikere at fjerningen var vellykket
    }

    public int fjernAlle(T verdi) {
        if (tom() || verdi == null) {                               // Hvis treet er tomt eller verdi er null
            return 0;                                               // Returner 0 for ingen fjerninger
        }
        int antallFjernet = 0;                                      // Initialiserer en teller for antall fjerninger

        final var skalFjerneStabel = hentNodeStabel(verdi);         // Lager en stabel med nodene med verdi som skal fjernes

        while (!skalFjerneStabel.isEmpty()) {                       // Så lenge det er noder i stabelen
            Node<T> skalFjerneNode = skalFjerneStabel.pop();        // Fjerner noden fra stabelen
            fjern(skalFjerneNode.verdi);                            // Fjerner noden
            antallFjernet++;                                        // Øker telleren for hver vellykkede fjerning
        }
        return antallFjernet;                                       // Returnerer det totale antallet noder med inputverdi som ble fjernet
    }

    private Stack<Node<T>> hentNodeStabel(T verdi) {
        Stack<Node<T>> stabelTilFjerning = new Stack<>();           // Lager en stabel med noder

        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) {
                p = p.venstre;
            } else if (cmp > 0) {
                p = p.høyre;
            } else {                                                // Fant noden med verdien vi leter etter
                stabelTilFjerning.push(p);                          // Legger noden til stabelen
                p = p.høyre;
            }
        }
        return stabelTilFjerning;
    }

    public void nullstill() {
        Random tilfeldig = new Random();
        int myntKast = tilfeldig.nextInt(2);
        if (myntKast % 2 == 0) {                        // Velger tilfeldig metode for nullstilling
            rot = nullstillRekursivt(rot);
        } else {
            rot = nullstillIterativt(rot);
        }
        antall = 0;
    }

    private Node<T> nullstillRekursivt(Node<T> inputNode) {
        if (inputNode == null) return null;                         // Basecase: Når p er null, returner null

        inputNode.venstre = nullstillRekursivt(inputNode.venstre);  // Nullstiller rekursivt venstre subtre
        inputNode.høyre = nullstillRekursivt(inputNode.høyre);      // Nullstiller rekursivt høyre subtre
        inputNode.forelder = null;                                  // Nullstiller forelder-pekeren
        return null;                                                // Returnerer null for å nullstille noden
    }

    private Node<T> nullstillIterativt(Node<T> inputNode) {
        if (inputNode == null) return null;                         // Hvis treet allerede er tomt, returner

        Stack<Node<T>> stabel = new Stack<>();                      // Oppretter en stabel for å tømme treet iterativt
        stabel.push(inputNode);                                     // Legger rotnoden på stabelen

        while (!stabel.isEmpty()) {                                 // Så lenge stabelen ikke er tom,
            Node<T> pekerNode = stabel.pop();                       // fjern øverste node fra stabelen

            if (pekerNode.høyre != null) {                          // Hvis noden har et høyre barn
                stabel.push(pekerNode.høyre);                       // Legg høyre barn på stabelen
            }

            if (pekerNode.venstre != null) {                        // Hvis noden har et venstre barn
                stabel.push(pekerNode.venstre);                     // Legg venstre barn på stabelen
            }

            pekerNode.verdi = null;                                 // Nullstill nodens verdi
            pekerNode.venstre = null;                               // Nullstill venstre barn
            pekerNode.høyre = null;                                 // Nullstill høyre barn
            pekerNode.forelder = null;                              // Nullstill forelder
        }

        return null;                                                // Returnerer null for å nullstille noden
    }
}