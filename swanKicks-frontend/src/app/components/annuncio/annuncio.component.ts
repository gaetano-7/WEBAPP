import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, interval } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { asta } from 'src/app/model/asta';
import { scarpa } from 'src/app/model/scarpa';
import { recensione } from 'src/app/model/recensione';
import { utente } from 'src/app/model/utente';
import { ServiceService } from 'src/app/service/service.service';
import { AuthService } from 'src/app/auth/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { ErrordialogComponent } from '../errordialog/errordialog.component';
import { SuccessdialogComponent } from '../successdialog/successdialog.component';
import { faFacebook, faWhatsapp } from '@fortawesome/free-brands-svg-icons';
import { faEnvelope } from '@fortawesome/free-solid-svg-icons';
import { AddreviewdialogComponent } from '../addreviewdialog/addreviewdialog.component';
import { fineastadialogComponent } from '../fineastadialog/fineastadialog.component';


@Component({
  selector: 'app-annuncio',
  templateUrl: './annuncio.component.html',
  styleUrls: ['./annuncio.component.css']
})
export class AnnuncioComponent implements OnInit {

  constructor(public auth: AuthService, private route: ActivatedRoute, private router: Router, private service: ServiceService, public dialog: MatDialog) {}

  originalMarca: String = "";

  public id: number = 0;
  stringID: string = "";
  scarpa: scarpa = new scarpa();
  public recensioni: recensione[] = []
  proprietario: utente = new utente();
  asta: asta = new asta();
  images: String[] = [];
  prezzoOrigStyle: String = "";
  public imagePath: string = "";

  showRecensioneForm: boolean = false;
  formAddRecensione: FormGroup = new FormGroup({});

  menuOpen = false;
  faFacebook = faFacebook;
  faWhatsapp = faWhatsapp;
  faEnvelope = faEnvelope;

  subscription: Subscription = new Subscription;
  public dateNow = new Date();
  public dDay = new Date();
  milliSecondsInASecond = 1000;
  hoursInADay = 24;
  minutesInAnHour = 60;
  SecondsInAMinute  = 60;
  timeDifference?: number;
  secondsToDday?: number;
  minutesToDday?: number;
  hoursToDday?: number;
  daysToDday?: number;
  prezzo_minimo_asta: number = 0;
  ultimo_offerente: String = "nessuno";
  prezzo_asta_update: number = this.asta.prezzo_partenza;
  astaDisabled: boolean = false;

  ngOnInit() {
    /**
     * Prendo i dettagli della scarpa dato l'id
     */
    this.stringID += this.route.snapshot.paramMap.get("id");
    this.id = Number.parseInt(this.stringID);
    this.service.getScarpa(this.id).subscribe({
      next: (scarpa) => {
        this.originalMarca = scarpa.marca;
        scarpa.marca = scarpa.marca;
        this.scarpa = scarpa;
        
        /**
         * Prendo le info dell'utente
         */
        this.service.getUtente(scarpa.proprietario).subscribe({
          next: (utente) => {
            this.proprietario = utente;
          }
        })

        /**
         * Se la scarpa è di tipo asta, faccio una GET al server per prendere i dati dell'asta
         */
        if(scarpa.tipo_annuncio == "In_Asta") {
          this.service.getAstaByScarpa(scarpa.id).subscribe({
            next: (asta_info) => {
              this.asta = asta_info;

              this.dateNow = new Date();
              this.dDay = new Date(asta_info.fine);

              this.subscription = interval(1000)
                .subscribe(x => { this.getTimeDifference(); });

              this.prezzo_minimo_asta = asta_info.prezzo_corrente + 1;

              this.service.getUtente(asta_info.acquirente).subscribe({
                next: (utente) => {
                  this.ultimo_offerente = utente.email;
                }
              })
            }
          });
        }

        /**
         * Prendo le immagini dal database, se esistono
         */
        this.service.findImagesByScarpaID(scarpa.id).subscribe({
          next: (imgs) => {
            if(imgs.length > 0) {
                imgs.forEach(img => {
                  this.images.push(img.img);
              })
            }
          }
        });

        /**
         * Prendo le recensioni dal database, se esistono
         */
        this.service.getRecensioniByScarpaID(scarpa.id).subscribe({
          next: (recensioni) => {
            if(recensioni.length > 0) {
              this.recensioni = recensioni;
              this.recensioni.forEach(recensione => {
                this.service.getUtente(recensione.autore).subscribe({
                  next: (utente) => {
                    recensione.autore = utente.email;
                  }
                })
              })
            }
          }
        })

        this.service.findImagesByScarpaID(scarpa.id).subscribe({
          next: (imgs) => {
            if (imgs.length > 0) {
              /* Assegna il percorso dell'immagine alla variabile */
              this.imagePath = imgs[0].img;
            }
          }
        });

        this.prezzoOrigStyle = (this.scarpa.prezzo_orig > this.scarpa.prezzo_attuale)
          ? "text-decoration: line-through; text-decoration-color: red; font-size: 15px; margin-bottom: -10px;"
          : "";
      }
    });

    /**
     * Inizializza il form per le recensioni con i suoi campi
     */
    this.formAddRecensione = new FormGroup({ 
      titolo: new FormControl(), 
      rating: new FormControl() 
    });
  }


  deleteScarpa() {
    this.service.deleteScarpa(this.id);
    this.dialog.open(SuccessdialogComponent);
  }

  sendEmail() {
    window.location.href = `mailto:${this.proprietario.email}`;
  }

  makeCall() {
    window.location.href = `tel:${this.proprietario.telefono}`;
  }

  deleteRecensione(index: number) {
    this.service.deleteRecensione(index).subscribe({
      next: () => window.location.reload(),
      error: () => this.dialog.open(ErrordialogComponent),
    })
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  shareTo(social: string): void {
    const shareUrl = `localhost:4200/annuncio/${this.id}`;
    const shareText = `Guarda quest'annuncio: ${this.scarpa.nome}\nDescrizione: ${this.scarpa.descrizione}\nPrezzo: ${this.scarpa.prezzo_attuale}€`;
  
    switch (social) {
      case 'facebook':
        const facebookUrl = `https://www.facebook.com/sharer/sharer.php?quote=${encodeURIComponent(shareText)}`;
        window.open(facebookUrl, '_blank');
        break;
  
      case 'whatsapp':
        const whatsappUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent(shareText + '\n' + shareUrl)}`;
        window.open(whatsappUrl, '_blank');
        break;
  
      case 'mail':
        const subject = encodeURIComponent(`Check out this property: ${this.scarpa.nome}`);
        const body = encodeURIComponent(`${this.scarpa.descrizione}\nPrice: ${this.scarpa.prezzo_attuale}€\n\n${shareUrl}`);
        const mailtoUrl = `mailto:?subject=${subject}&body=${body}`;
        window.location.href = mailtoUrl;
        break;
  
      default:
        break;
    }
  }

  addReview(): void {
    this.auth.lastAnnuncioVisited = this.scarpa.id;
    this.dialog.open(AddreviewdialogComponent).afterClosed().subscribe({
      next: () => window.location.reload()
    })
  }

  addToCarrello(): void {
    this.auth.lastAnnuncioVisited = this.scarpa.id;
    this.service.setAcquisto({
      id: null,
      utente: localStorage.getItem('id'),
      scarpa: this.auth.lastAnnuncioVisited,
      nome: this.scarpa.nome,
      prezzo: this.scarpa.prezzo_attuale,
      taglia: this.scarpa.taglia,
      immagine: this.imagePath,
      completato: false,
    }).subscribe();
    this.dialog.open(SuccessdialogComponent).afterClosed().subscribe({
    })
  }

  getTimeDifference() {
    this.timeDifference = this.dDay.getTime() - new Date().getTime();
    this.allocateTimeUnits(this.timeDifference);
  
    if (this.timeDifference <= 0) {
      this.astaDisabled = true;
      this.fineAsta();
      this.ngOnDestroy();
    }
  }

  fineAsta(){
    if(this.astaDisabled == true){
      this.service.setOrdine({
        id: null,
        utente: this.asta.acquirente,
        scarpa: this.asta.scarpa,
        nome: this.asta.nome,
        prezzo: this.asta.prezzo_corrente,
        taglia: this.asta.taglia,
        immagine: this.asta.immagine,
    }).subscribe({
      next: () => {
        this.service.updateScarpa(this.asta.scarpa, {
          nome: this.asta.nome,
          tipo: "",
          marca: "",
          taglia: this.asta.taglia,
          prezzo_orig: this.asta.prezzo_corrente,
          prezzo_attuale: this.asta.prezzo_corrente,
          descrizione: "",
          anno_uscita: 2023,
          colore: "",
          proprietario: this.asta.acquirente,
          tipo_annuncio: "venduta",
          venduta: true,
        }).subscribe({});
      }
    });

    this.dialog.open(fineastadialogComponent).afterClosed().subscribe({})
  }
}

  allocateTimeUnits (timeDifference: number) {
    this.secondsToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond) % this.SecondsInAMinute);
    this.minutesToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond * this.minutesInAnHour) % this.SecondsInAMinute);
    this.hoursToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond * this.minutesInAnHour * this.SecondsInAMinute) % this.hoursInADay);
    this.daysToDday = Math.floor((timeDifference) / (this.milliSecondsInASecond * this.minutesInAnHour * this.SecondsInAMinute * this.hoursInADay));
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  sendUpdateAsta() {
    if(this.prezzo_asta_update <= this.prezzo_minimo_asta-1) {
      this.dialog.open(ErrordialogComponent);
    } else {
      this.service.updateAsta(this.asta.id, {
        id: this.asta.id,
        scarpa: this.asta.scarpa,
        acquirente: localStorage.getItem("id"),
        prezzo_partenza: this.asta.prezzo_partenza,
        prezzo_corrente: this.prezzo_asta_update,
        fine: this.asta.fine,
        nome: this.asta.nome,
        taglia: this.asta.taglia,
        immagine: this.asta.immagine,
      }).subscribe({
        next: () => this.dialog.open(SuccessdialogComponent).afterClosed().subscribe(() => {window.location.reload()}),
        error: () => this.dialog.open(ErrordialogComponent).afterClosed().subscribe(() => {window.location.reload()}),
      })
    }
  }

  checkIfSameOwner(): Boolean {
    return this.scarpa.proprietario == localStorage.getItem('id');
  }

  checkIfSameReviewOwner(recensione: recensione): Boolean {
    return recensione.autore == localStorage.getItem('email');
  }
}