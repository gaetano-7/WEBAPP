import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { asta } from 'src/app/model/asta';
import { scarpa } from 'src/app/model/scarpa';
import { utente } from 'src/app/model/utente';
import { ServiceService } from 'src/app/service/service.service';
import { AuthService } from 'src/app/auth/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ordine } from 'src/app/model/ordine';

@Component({
    selector: 'app-ordini',
    templateUrl: './ordini.component.html',
    styleUrls: ['./ordini.component.css']
})
export class OrdiniComponent implements OnInit{

    constructor(public auth: AuthService, private route: ActivatedRoute, private router: Router, private service: ServiceService, public dialog: MatDialog) {}

  originalMarca: String = "";

  public id: number = 0;
  stringID: string = "";
  scarpa: scarpa = new scarpa();
  public ordini: ordine[] = []
  public scarpe_att : scarpa[] = []
  proprietario: utente = new utente();
  asta: asta = new asta();
  images: String[] = [];
  prezzoOrigStyle: String = "";
  public imagePath: string = "";

  ngOnInit(): void {
    this.stringID += localStorage.getItem('id');
    this.id = Number.parseInt(this.stringID);

    this.service.getOrdiniByUtenteID(this.stringID).subscribe({
        next: (ordini) => {
            if (ordini.length > 0) {
                this.ordini = ordini;
                this.ordini.forEach(ordine => {
                    this.service.getScarpa(ordine.scarpa).subscribe({
                        next: (scarpa) => {
                            ordine.scarpa = scarpa.id;
                            ordine.nome = scarpa.nome;
                            ordine.prezzo = scarpa.prezzo_attuale;
                            ordine.taglia = scarpa.taglia;

                            this.getScarpaImage(scarpa.id).subscribe({
                                next: (img) => {
                                    ordine.immagine = img.img;
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
}