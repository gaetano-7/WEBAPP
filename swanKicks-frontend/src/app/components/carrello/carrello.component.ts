import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { asta } from 'src/app/model/asta';
import { scarpa } from 'src/app/model/scarpa';
import { utente } from 'src/app/model/utente';
import { ServiceService } from 'src/app/service/service.service';
import { AuthService } from 'src/app/auth/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { acquisto } from 'src/app/model/acquisto';
import { ErrordialogComponent } from '../errordialog/errordialog.component';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CheckoutComponent } from '../checkout/checkout.component';

@Component({
    selector: 'app-carrello',
    templateUrl: './carrello.component.html',
    styleUrls: ['./carrello.component.css']
})
export class CarrelloComponent implements OnInit{

    constructor(public auth: AuthService, private route: ActivatedRoute, private router: Router, private service: ServiceService, public dialog: MatDialog) {}

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

  ngOnInit(): void {
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

                            this.getScarpaImage(scarpa.id).subscribe({
                                next: (img) => {
                                    acquisto.immagine = img.img;
                                }
                            });
                        }
                    });
                });
            }
        }
    });
}

getScarpaImage(scarpaId: number): Observable<any> {
  return this.service.findImagesByScarpaID(scarpaId).pipe(
      map((imgs) => {
          if (imgs.length > 0) {
              return imgs[0];
          } else {
            return 'src\assets\no_images_found.png';
          }
      })
  );
}

  rimuoviAcquisto(id: Number){
    this.service.deleteAcquisto(id).subscribe({
      next: () => window.location.reload(),
      error: () => this.dialog.open(ErrordialogComponent),
    })
  }

  acquista() {
    this.dialog.open(CheckoutComponent, {
      width: '550px',
    });
}

}