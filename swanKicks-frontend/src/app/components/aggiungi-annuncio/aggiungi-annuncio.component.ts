import { Component, OnInit} from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ServiceService } from 'src/app/service/service.service';
import { AuthService } from "src/app/auth/auth.service";
import { MatDialog } from '@angular/material/dialog';
import { ErrordialogComponent } from 'src/app/components/errordialog/errordialog.component';
import { SuccessdialogComponent } from 'src/app/components/successdialog/successdialog.component';
import { NgxImageCompressService } from 'ngx-image-compress';
import { DatePipe } from '@angular/common';
import { scarpa } from 'src/app/model/scarpa';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-aggiungi-annuncio',
  templateUrl: './aggiungi-annuncio.component.html',
  styleUrls: ['./aggiungi-annuncio.component.css'],
  providers: [DatePipe],
})
export class AggiungiAnnuncioComponent implements OnInit{
  
  public formAggiungi: FormGroup = new FormGroup({});

  astaSelected: boolean = false;
  selectedValue: String = "";
  selectedValueType: String = "";
  images: String[] = [];
  minDateTime: any;
  myScarpeArray: scarpa[] = [];
  myScarpeArrayIndex: number = -1;
  myScarpeSelection: String = "";

  constructor(private service: ServiceService, private auth: AuthService, public dialog: MatDialog, private imageCompress: NgxImageCompressService, private datePipe: DatePipe) {}

  ngOnInit(): void {
    if(!this.auth.isLoggedIn) window.open("http://localhost:4200/home", "_self")
    this.formAggiungi = new FormGroup({
      titolo: new FormControl(),
      descrizione: new FormControl(),
      marca: new FormControl(),
      taglia: new FormControl(),
      anno_uscita: new FormControl(),
      colore: new FormControl(),
      prezzo: new FormControl(),
      tipo: new FormControl(),
      foto: new FormControl(),
      asta_endtime: new FormControl(),
      tipo_annuncio: new FormControl(),
      prezzo_attuale: new FormControl(),
    });

    const currentDate = new Date();
    this.minDateTime = this.datePipe.transform(currentDate, 'yyyy-MM-ddTHH:mm');

    const prop_id = localStorage.getItem("id");
    if(!this.auth.isAdmin()){
      this.service.findAllByOwner(prop_id || "").subscribe({
        next: (scarpe) => {
          this.myScarpeArray = scarpe;
        }
      });
    } else {
      this.service.getScarpe().subscribe({
        next: (scarpe) => {
          this.myScarpeArray = scarpe;
        }
      });
    }
  }

  onFileChange(event: any) {
    this.images = [];
    const files = event.target.files;
    if (files.length > 0) {
      for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader = new FileReader();
        reader.onload = (e: any) => {
          const image = e.target.result;

          this.imageCompress
            .compressFile(image, -1, 50, 50) 
            .then(compressedImage => {
              this.images.push(compressedImage);
            })
            .catch(error => {
              this.dialog.open(ErrordialogComponent);
            });
        };
        reader.readAsDataURL(file);
      }
    }
  }

  convertToTimestamp(value: string): number {
    const date = new Date(value);
    const timestamp = date.getTime();
    const postgresTimestamp = BigInt(timestamp);
    const postgresTimestampNumber = Number(postgresTimestamp);
    return postgresTimestampNumber;
  }

  onSubmit() {
    if(this.formAggiungi.value.prezzo_attuale > this.formAggiungi.value.prezzo) {
      this.formAggiungi.patchValue({
        prezzo: this.formAggiungi.value.prezzo_attuale
      })
    }
    if(this.myScarpeSelection == "") {
      this.service.setScarpa({
        nome: this.formAggiungi.value.titolo,
        tipo: this.formAggiungi.value.tipo,
        marca: this.formAggiungi.value.marca,
        taglia: this.formAggiungi.value.taglia,
        prezzo_orig: this.formAggiungi.value.prezzo,
        prezzo_attuale: this.formAggiungi.value.prezzo,
        descrizione: this.formAggiungi.value.descrizione,
        anno_uscita: this.formAggiungi.value.anno_uscita,
        colore: this.formAggiungi.value.colore,
        proprietario: localStorage.getItem("id"),
        tipo_annuncio: this.formAggiungi.value.tipo_annuncio,
        venduta: false,
      }).subscribe({
        next: () => {
          this.service.getLastAddedByOwner(localStorage.getItem("id") || "").subscribe(sca_id => {
            if(this.astaSelected) {
              this.service.setAsta({
                scarpa: sca_id,
                acquirente: null,
                nome: this.formAggiungi.value.titolo,
                taglia: this.formAggiungi.value.taglia,
                immagine: "",
                prezzo_partenza: this.formAggiungi.value.prezzo,
                prezzo_corrente: this.formAggiungi.value.prezzo,
                fine: this.convertToTimestamp(this.formAggiungi.value.asta_endtime),
              }).subscribe({
                error: () => this.dialog.open(ErrordialogComponent),
              });
            }

            if(this.images.length > 0) {
              this.images.forEach(image => {
                this.service.createImage({
                  id: null,
                  scarpa: sca_id,
                  img: image,
                }).subscribe({
                  error: () => this.dialog.open(ErrordialogComponent),
                });
              });
            }
          });
        },
        error: () => this.dialog.open(ErrordialogComponent),
        complete: () => this.dialog.open(SuccessdialogComponent).afterClosed().subscribe({
          next: () => window.open("http://localhost:4200/home", "_self"),
        }),
      });
    } else {
      const scarpa_id = this.myScarpeArray[this.myScarpeArrayIndex].id;
      var proprietario = "";
      if(this.myScarpeArray[this.myScarpeArrayIndex].proprietario != localStorage.getItem("id")){
        proprietario = this.myScarpeArray[this.myScarpeArrayIndex].proprietario;
      } else {
        proprietario = localStorage.getItem("id") || "";
      }
      this.service.updateScarpa(scarpa_id, {
        nome: this.formAggiungi.value.titolo,
        tipo: this.formAggiungi.value.tipo,
        marca: this.formAggiungi.value.marca,
        taglia: this.formAggiungi.value.taglia,
        prezzo_orig: this.formAggiungi.value.prezzo,
        prezzo_attuale: this.formAggiungi.value.prezzo,
        descrizione: this.formAggiungi.value.descrizione,
        anno_uscita: this.formAggiungi.value.anno_uscita,
        colore: this.formAggiungi.value.colore,
        proprietario: localStorage.getItem("id"),
        tipo_annuncio: this.formAggiungi.value.tipo_annuncio,
        venduta: false
      }).subscribe({
        next: () => {
          if(this.images.length > 0) {
            this.images.forEach(image => {
              this.service.createImage({
                id: null,
                scarpa: scarpa_id,
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
    }
  }

  getScarpaImage(scarpaId: number): Observable<any> {
    // Utilizziamo il servizio per ottenere l'immagine
    return this.service.findImagesByScarpaID(scarpaId).pipe(
        map((imgs) => {
            if (imgs.length > 0) {
                return imgs[0];
            } else {
                // Ritorna un valore di fallback nel caso in cui non ci sia un'immagine
                return { img: 'fallback_image_path.jpg' };
            }
        })
    );
  }

  onValueSelected(event: any) {
    const selectedValue = event.target.value;
    if (selectedValue === "In_Asta") {
      this.astaSelected = true;
    } else {
      this.astaSelected = false;
    }
  }

  onValueTypeSelected(event: any) {
    const selectedValue = event.target.value;
    this.selectedValueType = selectedValue;
  }

  onValueScarpeSelected(event: any) {
    const selectedValue = event.target.value;
    this.myScarpeArrayIndex = selectedValue;

    const selectedScarpa = this.myScarpeArray[this.myScarpeArrayIndex];
    this.formAggiungi.patchValue({
      titolo: selectedScarpa.nome,
      descrizione: selectedScarpa.descrizione,
      marca: selectedScarpa.marca,
      taglia: selectedScarpa.taglia,
      anno_uscita: selectedScarpa.anno_uscita,
      colore: selectedScarpa.colore,
      prezzo: selectedScarpa.prezzo_orig,
      tipo: selectedScarpa.tipo,
      tipo_annuncio: selectedScarpa.tipo_annuncio,
      prezzo_attuale: selectedScarpa.prezzo_attuale,
    });

    if(selectedScarpa.tipo_annuncio === "In_Asta") {
      this.astaSelected = true;
    } else {
      this.astaSelected = false;
    }
  }

  openNewPage() {
    window.open('http://localhost:4200/annuncio/'+this.myScarpeArray[this.myScarpeArrayIndex].id, '_blank');
  }

  deleteScarpa() {
    this.service.deleteScarpa(this.myScarpeArray[this.myScarpeArrayIndex].id).subscribe({
      next: () => {
        this.dialog.open(SuccessdialogComponent).afterClosed().subscribe({
          complete: () => window.location.reload(),
        })
      },
      error: () => this.dialog.open(ErrordialogComponent),
    });
  }
}
