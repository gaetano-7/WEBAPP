import { Component, OnInit } from '@angular/core';
import { loadStripe, Stripe, StripeCardElement } from '@stripe/stripe-js';
import { ActivatedRoute, Router } from '@angular/router';
import { asta } from 'src/app/model/asta';
import { scarpa } from 'src/app/model/scarpa';
import { utente } from 'src/app/model/utente';
import { ServiceService } from 'src/app/service/service.service';
import { AuthService } from 'src/app/auth/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { acquisto } from 'src/app/model/acquisto';
import { ErrordialogComponent } from '../errordialog/errordialog.component';
import { SuccessdialogComponent } from '../successdialog/successdialog.component';

@Component({
  selector: 'app-pagamento',
  templateUrl: './pagamento.component.html',
  styleUrls: ['./pagamento.component.css']
})
export class PagamentoComponent implements OnInit {
  stripe: Stripe | null = null;
  card: StripeCardElement | null = null;
  isLoading = false;


  constructor(private router: Router,
              public auth: AuthService, 
              private route: ActivatedRoute, 
              private service: ServiceService, 
              public dialog: MatDialog) {
  }

  originalMarca: String = "";

  public id: number = 0;
  stringID: string = "";
  scarpa: scarpa = new scarpa();
  public acquisti: acquisto[] = []
  public scarpe_att : scarpa[] = []
  proprietario: utente = new utente();
  asta: asta = new asta();
  images: String[] = [];
  prezzoOrigStyle: String = "";
  public imagePath: string = "";
  public totale: number = 0;

  async ngOnInit() {
    this.stripe = await loadStripe('pk_test_51OX4YdJkGG7c6naXyn6rwgvQpsiw9NmvKzKZHy4yuGRdJSzblmelo4ffIzTas8nvjKIDnj7U7H5Un17PXaGMWLt300tAGxdxF7');
    const elements = this.stripe!.elements();
    this.card = elements.create('card');
    this.card.mount('#card-element');

    this.stringID += localStorage.getItem('id');
    this.id = Number.parseInt(this.stringID);

    this.service.getAcquistiByUtenteID(this.stringID).subscribe({
        next: (acquisti) => {
            if (acquisti.length > 0) {
                this.acquisti = acquisti;
                this.acquisti.forEach(acquisto => {
                    this.service.getScarpa(acquisto.scarpa).subscribe({
                        next: (scarpa) => {
                            acquisto.scarpa = scarpa.id;
                            acquisto.nome = scarpa.nome;
                            acquisto.prezzo = scarpa.prezzo_attuale;
                            acquisto.taglia = scarpa.taglia;
                            this.totale += acquisto.prezzo;
                        }
                    });
                });
            }
        }
    });
  }

  rimuoviTutto() {
    if (this.acquisti.length === 0) {
      this.dialog.open(ErrordialogComponent).afterClosed().subscribe({
        next: () => window.location.reload(),
      });
      return;
    }
  
    const deleteAndSetOrders = (index: number) => {
        if (index >= this.acquisti.length) {
            this.dialog.open(SuccessdialogComponent).afterClosed().subscribe({
                next: () => window.location.reload(),
            });
            return;
        }

        const acquisto = this.acquisti[index];

        this.service.setOrdine({
            id: null,
            utente: localStorage.getItem('id'),
            scarpa: acquisto.scarpa,
            nome: acquisto.nome,
            prezzo: acquisto.prezzo,
            taglia: acquisto.taglia,
            immagine: acquisto.immagine,
        }).subscribe({
            next: () => {
                this.service.updateScarpa(acquisto.scarpa, {
                    nome: acquisto.nome,
                    tipo: "",
                    marca: acquisto.nome,
                    taglia: acquisto.taglia,
                    prezzo_orig: acquisto.prezzo,
                    prezzo_attuale: acquisto.prezzo,
                    descrizione: "",
                    anno_uscita: 2023,
                    colore: "",
                    proprietario: localStorage.getItem("id"),
                    tipo_annuncio: "venduta",
                    venduta: true,
                  }).subscribe({
                    next: () => {
                      if(this.images.length > 0) {
                        this.images.forEach(image => {
                          this.service.createImage({
                            id: null,
                            scarpa: acquisto.immagine,
                            img: image,
                          }).subscribe({
                            error: () => this.dialog.open(ErrordialogComponent),
                          });
                        });
                      }
                    },
                    error: () => this.dialog.open(ErrordialogComponent),
                    complete: () => this.dialog.open(SuccessdialogComponent).afterClosed().subscribe({
                      next: () => window.open("http://localhost:4200/home", "_self"),
                    }),
                  });
                this.service.deleteAcquisto(acquisto.id).subscribe({
                    next: () => {
                        deleteAndSetOrders(index + 1); // Chiama nuovamente questa funzione ricorsivamente con l'indice incrementato
                    },
                });
            },
            error: (error) => {
                // Gestisci l'errore se si verifica durante l'aggiunta dell'ordine
                console.error("Errore durante l'aggiunta dell'ordine:", error);
                this.dialog.open(ErrordialogComponent);
            },
        });
    };

    deleteAndSetOrders(0); // Avvia il processo di eliminazione e impostazione degli ordini a partire dall'indice 0
  }
}
