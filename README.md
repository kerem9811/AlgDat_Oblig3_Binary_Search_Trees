# Obligatorisk Oppgave 3 i DATS2300 - Algoritmer og Datastrukturer - Høst 2024
## Binære søketrær, innlegging & fjerning

Denne oppgaven er en innlevering i Algoritmer og datastrukturer.
Oppgaven er levert av:
* Ken Solbakken Remen, kerem9811@oslomet.no

## Oppgavebeskrivelser

### Oppgave 1 - Innlegging i binært søketre
Metoden public boolean leggInn(T verdi) legger til en ny node med gitt verdi i søkebinærtreet.
For å opprettholde treets søkeegenskap, sammenligner metoden den nye verdien med eksisterende noder
og traverserer nedover til riktig plassering. Metoden setter også riktig venstre- og høyrebarn, 
og oppdaterer forelderpekeren til den nye noden. Rotnoden får null som forelder. 
Metoden returnerer true hvis innleggingen var vellykket.

### Oppgave 2 - Antall forekomster
Metoden public int antall(T verdi) teller hvor mange ganger en gitt verdi finnes i søkebinærtreet. 
Dersom verdien ikke finnes, eller treet er tomt, returnerer metoden 0.

### Oppgave 3 - Postorden traversering
Denne oppgaven implementerer postorden traversering i et binært søketre uten bruk av rekursjon eller hjelpestrukturer 
som stacker eller køer. To hjelpemetoder er laget for å støtte traverseringen:
førstePostorden finner den første noden i postorden, gitt en startnode p.
nestePostorden finner noden som kommer etter p i postorden. Returnerer null hvis p er den siste noden.
Disse metodene brukes for å effektivt finne neste node som skal behandles i postorden, og danner grunnlaget for oppgave 4.

### Oppgave 4 - Utfør Oppgave
Denne oppgaven handler om å utføre en gitt oppgave på alle nodene i søkebinærtreet i postorden. To metoder er implementert:
postOrden(Oppgave<? super T> oppgave): Traverserer treet i postorden uten å bruke rekursjon eller hjelpestrukturer.
postOrdenRekursiv(Oppgave<? super T> oppgave): Traverserer treet i postorden rekursivt. 
Begge metodene tar imot et Oppgave-objekt som definerer handlingen som skal utføres på hver node. 
Dette gjør metodene generiske og gjenbrukbare for ulike oppgaver, for eksempel å skrive ut treet eller summere verdiene.

### Oppgave 5 - Fjerne elementer
Denne oppgaven implementerer fjerning av noder fra søkebinærtreet. 
public boolean fjern(T verdi) fjerner den første forekomsten av en gitt verdi, mens 
public int fjernAlle(T verdi) fjerner alle forekomster og returnerer antallet som ble fjernet. 
For å håndtere ulike tilfeller av noder (ingen barn, ett barn, to barn) benyttes hjelpemetoder 
for å opprettholde treets struktur. Til slutt sørger public void nullstill() for å tømme hele treet 
ved å nullstille alle nodepekere og verdier.

#### Tre alternative implementasjoner er tilgjengelige:
   nullstillPostorden(Node<T> p): Benytter postorden traversering for å nullstille noder.\
   nullstillRekursivt(Node<T> p): Nullstiller treet rekursivt.\
   nullstillIterativt(Node<T> p): Nullstiller treet iterativt ved hjelp av en stack.\
   En av disse implementasjonene velges tilfeldig for å demonstrere ulike løsningsmetoder.\
   rot settes til null, og antall settes til 0 for å reflektere at treet er tomt.
   
#### Hjelpemetoder:
   private void fjernNode(Node<T> p): Hjelpemetode for å fjerne en gitt node p fra treet.\
   private void fjernBladNode(Node<T> p, Node<T> q): Hjelpemetode for å fjerne en bladnode p med forelder q.\
   private void fjernNodeMedEttBarn(Node<T> p, Node<T> q): Hjelpemetode for å fjerne en node p med ett barn, der q er forelderen til p.\
   private void fjernNodeMedToBarn(Node<T> p): Hjelpemetode for å fjerne en node p med to barn.
