# SwanKicks
<div align="center">
  <img src="swanKicks-frontend/src/assets/logo.png" alt="Logo SwanKicks" width="350" height="350">
</div>

## Il team


Team di sviluppo:

- Francesco Gaetano Larocca
- Giuseppe Lentini
- Ernesto Gligora
- Riccardo Lavorato 


## Descrizione del progetto

SwanKicks è uno dei principali e-commerce online per la vendita e l'acquisto di sneakers. Gli utenti una volta registrati potranno accedere ad un vasto catalogo contenente sneakers di vario genere, dalle più famose alle più utili per ogni evento. SwanKicks offre molte funzionalità permettendo di filtrare la ricerca in base alle tue preferenze ed anche di decidere se vendere o acquistare un determinato articolo. Gli utenti che desiderano di acquistare una particolare sneakers potranno partecipare ad aste in tempo reale, oppure acquistare direttamente le sneakers vendute con un prezzo fisso. Ogni utente, inoltre, avrà a disposizione un proprio carrello per aggiungere le sneakers che desidera. Invece, gli utenti che desiderano vendere una sneakers potranno aggiungere, modificare o eliminare del tutto un annuncio. Potranno scegliere se metterla all'asta o fissare un prezzo. Tutta la piattaforma verrà gestita e monitorata da degli Admin, che si dedicheranno al controllo delle vendite e degli acquisti da parte dei milioni di utenti registrati.


## Istruzioni per l’utilizzo

### Operazioni preliminari

**********Back-end**********: Path repo → “/swanKicks-backend”

Prima di avviare normalmente il progetto, sarà necessario effettuare il ripristino del database, tramite il dump presente all’interno di questo repository in 

> /swanKicks-backend/DUMP/*.tar
> 

Una volta effettuato il ripristino del database, sarà inoltre necessario recarsi nel file

> /SwanKicks/swanKicks-backend/src/main/java/com/example/swankicksbackend/persistence/DBManager.java
> 

E, alla riga 36, inserire i propri dati per la connessione al DB

---

**Front-end**:  Path repo → “/swanKicks-frontend”

Per una sola volta, al primo avvio, sarà necessario usare precedentemente il seguente comando, per installare tutti i moduli richiesti dall’app front-end

```bash
npm install
```

Per avviare il progetto **Angular** sarà necessario usare il seguente comando 

```bash
ng serve 
```

### Tecnologie utilizzate

- [HTML/CSS];
- [Java];
- [Typescript];
- [Angular];
- [Angular Material Components];
- [Rest Controller];
- [Postgres];
- [Tomcat];
- [Springboot];
- [APISocial (Whatsapp, Facebook, Mail, Youtube)];
