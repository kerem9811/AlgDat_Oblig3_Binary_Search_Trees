[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/teLsEufN)

# Obligatorisk Oppgave 3 i DATS2300 - Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og datastrukturer.
Oppgaven er levert av:
* kerem9811, kerem9811@oslomet.no

## Oppgavebeskrivelser

# Oppgave 1 - Innlegging i binært søketre
Denne metoden, public boolean leggInn(T verdi), legger til en ny node med den gitte verdien 
på riktig plass i søkebinærtreet. For å opprettholde søkeegenskapen til treet, sammenligner metoden 
verdien som skal legges inn med verdiene i de eksisterende nodene. Den traverserer nedover i treet 
til den finner riktig plassering for den nye noden. I tillegg til å sette riktig venstre- og høyrebarn, 
oppdaterer metoden også forelder-pekeren til den nye noden. Hvis den nye noden blir den nye roten til treet, 
tilpasser metoden rot-pekeren. Metoden returnerer true hvis innleggingen var vellykket.

# Oppgave 2 - Antall forekomster
Denne metoden teller og returnerer hvor mange ganger en gitt verdi forekommer i søkebinærtreet. 
Den returnerer 0 hvis treet er tomt, verdi er null, eller verdi ikke finnes i treet. 
Metoden teller antall forekomster av en verdi, inkludert duplikater, og tillater at samme verdi kan lagres flere steder.

# Oppgave 3 - Postorden traversering
Denne oppgaven handler om å lage to hjelpemetoder som lar oss gå gjennom et binært søketre i "postorden" 
uten å bruke rekursjon eller ekstra datastrukturer.
1. private Node<T> førstePostorden(Node<T> p): Tenk deg at du starter ved en gitt node p i treet. 
   Denne metoden finner den aller første noden du skal besøke hvis du skal gå gjennom alle nodene i p sitt subtre i postorden.
   Metoden gjør dette ved å systematisk gå nedover i treet, først til venstre og så til høyre, 
   til den finner en node som ikke har noen barn.
2. private Node<T> nestePostorden(Node<T> p): Denne metoden tar også utgangspunkt i en node p. 
   Den finner noden som skal besøkes etter p når man går gjennom treet i postorden.
   Hvis p er den aller siste noden i postorden, returnerer metoden null for å signalisere at det ikke er flere noder å besøke.

# Oppgave 4 - Utfør Oppgave
public void postOrden(Oppgave<? super T> oppgave): Denne metoden traverserer treet i postorden uten rekursjon 
og uten bruk av hjelpestrukturer som stacker eller køer. Den bruker i stedet nestePostorden-metoden fra Oppgave 3
for å finne neste node i postorden. For hver node i traverseringen kalles oppgave.utførOppgave(node.verdi) 
for å utføre en generisk oppgave.

public void postOrdenRekursiv(Oppgave<? super T> oppgave): Denne metoden traverserer treet i postorden rekursivt. 
Den bruker en rekursiv hjelpemetode private void postOrdenRekursiv(Node<T> p, Oppgave<? super T> oppgave) som tar
en node og oppgaven som parametere. For hver node i traverseringen kalles oppgave.utførOppgave(node.verdi) 
for å utføre en generisk oppgave.

# Oppgave 5 - Fjerne elementer
Fjern-funksjonen public boolean fjern(T verdi) fjerner den første forekomsten av en gitt verdi fra søkebinærtreet.
Funksjonen søker først etter noden som inneholder verdien. Hvis verdien ikke finnes, returnerer funksjonen false.
Hvis verdien finnes, håndterer funksjonen tre forskjellige tilfeller for å fjerne noden og opprettholde trestrukturen:
-Noden er en løvnode (ingen barn): Noden fjernes direkte ved å sette forelderens peker til null.
-Noden har ett barn: Noden erstattes med sitt barn, og foreldre- og barnepekere oppdateres.
-Noden har to barn: Noden erstattes med sin inorden-etterfølger (den minste noden i det høyre subtreet). 
Inorden-etterfølgeren fjernes deretter, som enten vil være en løvnode eller ha ett barn.
Funksjonen returnerer true hvis verdien ble funnet og fjernet.

FjernAlle-funksjonen public int fjernAlle(T verdi) fjerner alle forekomster av en gitt verdi fra søkebinærtreet. 
Funksjonen traverserer treet og legger alle noder med den angitte verdien i en stabel. 
Deretter fjernes nodene fra stabelen i postorden, noe som sikrer at trestrukturen opprettholdes. 
Funksjonen returnerer antall noder som ble fjernet. Hvis verdien ikke finnes i treet, returnerer funksjonen 0.

Nullstill-funksjonen public void nullstill() tømmer hele søkebinærtreet. 
Den fjerner alle noder og tilbakestiller treet til sin opprinnelige, tomme tilstand. 
Funksjonen sørger for at alle nodepekere og nodeverdier blir nullet ut, slik at ingen referanser til tidligere noder beholdes. 
Dette gjøres enten rekursivt eller iterativt, avhengig av et tilfeldig valg.