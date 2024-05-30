import { Component, OnInit, ViewChild } from '@angular/core';
import { BreakpointObserver, BreakpointState, MediaMatcher } from '@angular/cdk/layout';
import { Router } from '@angular/router';
import { ServiceService } from 'src/app/service/service.service';
import { scarpa } from 'src/app/model/scarpa';
import { MatSidenav } from '@angular/material/sidenav';
import { FormControl, FormGroup } from '@angular/forms';
import { filtro } from 'src/app/model/filtro';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  scarpe: scarpa[] = [];
  scarpe_backup: scarpa[] = [];
  images: String[] = [];
  formFiltri : FormGroup = new FormGroup({});
  formRicerca: FormGroup = new FormGroup({});
  filtro: filtro = new filtro();

  tipoScarpaValue: String = "";
  tipoAnnuncioValue: String = "";
  tipoOrdinamentoValue: String = "";
  searchWord: String = "";

  showOnly2Cols: boolean = false;

  ngOnInit(): void {
    /**
     * Prendo tutte le scarpe e applico le modifiche grafiche all'indirizzo
     */
    this.service.getScarpe().subscribe({
      next: (scarpe) => {
        scarpe.forEach(scarpa => {
          scarpa.marca = scarpa.marca;
          this.service.findImagesByScarpaID(scarpa.id).subscribe({
            next: (imgs) => {
              if (imgs.length > 0) {
                const base64String = imgs[0].img;
                this.images[scarpa.id] = base64String;
              }
            }
          })
        })
        this.scarpe = scarpe.filter(scarpa => scarpa.venduta !== true);
      },
    });

    //inizializzazione dei form che restano in attesa
    this.formFiltri = new FormGroup({
      tipoScarpa: new FormControl(),
      tipoAnnuncio: new FormControl(),
      tipoOrdinamento: new FormControl()
    });

    this.formRicerca = new FormGroup({
      searchWord: new FormControl()
    });

    this.tipoScarpaValue = "";
    this.tipoAnnuncioValue = "";
    this.tipoOrdinamentoValue = "";
    this.searchWord = "";
  }

  @ViewChild('sidenav') sidenav!: MatSidenav;
  private mobileQuery: MediaQueryList;

  constructor(private breakpointObserver: BreakpointObserver, private mediaMatcher: MediaMatcher, private router: Router, private service: ServiceService) {
    this.breakpointObserver.observe(["(max-width: 1450px)"]).subscribe((result: BreakpointState) => {
      if (result.matches) {
          this.showOnly2Cols = true;     
      } else {
          this.showOnly2Cols = false;
      }
    });
    this.mobileQuery = mediaMatcher.matchMedia('(max-width: 600px)');
  }

  Mobile(): boolean {
    return this.mobileQuery.matches;
  }

  sortScarpe(value: String) {
    if (value === "piu_costosi") {
      this.scarpe.sort((a, b) => b.prezzo_attuale - a.prezzo_attuale);
    } else if (value === "meno_costosi") {
      this.scarpe.sort((a, b) => a.prezzo_attuale - b.prezzo_attuale);
    } else if (value === "piu_recenti") {
      this.scarpe.sort((a, b) => b.anno_uscita - a.anno_uscita);
    } else if (value === "meno_recenti") {
      this.scarpe.sort((a, b) => a.anno_uscita - b.anno_uscita);
    }
  }

  tipoScarpaValueChange(event: any) {
    const selectedValuea = event.target.value;
    this.tipoScarpaValue = selectedValuea;
  }

  tipoAnnuncioValueChange(event: any) {
    const selectedValueb = event.target.value;
    this.tipoAnnuncioValue = selectedValueb;
  }

  tipoOrdinamentoValueChange(event: any) {
    const selectedValuec = event.target.value;
    this.tipoOrdinamentoValue = selectedValuec;
    this.sortScarpe(this.tipoOrdinamentoValue);
  }

  OpenScarpa(id: number) {
    this.router.navigate(['/annuncio',id]);
  }

  onSubmit() {
    if(this.scarpe_backup.length == 0) {
      this.scarpe_backup = this.scarpe;
    } else {
      this.scarpe = this.scarpe_backup;
    }
  
    this.filtro.tipoScarpa = this.formFiltri.value.tipoScarpa;
    this.filtro.tipoAnnuncio = this.formFiltri.value.tipoAnnuncio;
    this.filtro.tipoOrdinamento = this.formFiltri.value.tipoOrdinamento;
    this.filtro.searchWord = this.formRicerca.value.searchWord;
  
    if(this.formFiltri.value.tipoScarpa == "" &&
       this.formFiltri.value.tipoAnnuncio == "" &&
       this.formFiltri.value.tipoOrdinamento == "" &&
       this.formRicerca.value.searchWord == null) {
      return;
    } else {
      this.scarpe = this.scarpe.filter(sca => {
        if (this.filtro.tipoScarpa && !this.filtro.tipoAnnuncio && !this.filtro.searchWord) {
          return sca.tipo === this.filtro.tipoScarpa;
        } else if ((!this.filtro.tipoScarpa || this.filtro.tipoScarpa === "") && this.filtro.tipoAnnuncio && !this.filtro.searchWord) {
          return sca.tipo_annuncio === this.filtro.tipoAnnuncio;
        } else if ((!this.filtro.tipoScarpa || this.filtro.tipoScarpa === "") && (!this.filtro.tipoAnnuncio || this.filtro.tipoAnnuncio === "") && (this.filtro.searchWord || this.filtro.searchWord === "")) {
          const searchWordLower = this.filtro.searchWord ? this.filtro.searchWord.toLowerCase() : "";
          const isWordInNome = sca.nome.toLowerCase().includes(searchWordLower);
          const isWordInDescrizione = sca.descrizione.toLowerCase().includes(searchWordLower);
          const isWordInMarca = sca.marca.toLowerCase().includes(searchWordLower);
          return isWordInNome || isWordInDescrizione || isWordInMarca;
        } else {
          const isTipoMatch = (!this.filtro.tipoScarpa || sca.tipo === this.filtro.tipoScarpa);
          const isTipoAnnuncioMatch = (!this.filtro.tipoAnnuncio || sca.tipo_annuncio === this.filtro.tipoAnnuncio);
          const searchWordLower = this.filtro.searchWord ? this.filtro.searchWord.toLowerCase() : "";
          const isWordInNome = (!this.filtro.searchWord || sca.nome.toLowerCase().includes(searchWordLower));
          const isWordInDescrizione = (!this.filtro.searchWord || sca.descrizione.toLowerCase().includes(searchWordLower));
          const isWordInMarca = (!this.filtro.searchWord || sca.marca.toLowerCase().includes(searchWordLower));
          const isTipoAndAnnuncioMatch = isTipoMatch && isTipoAnnuncioMatch;
          return (isTipoAndAnnuncioMatch && (isWordInNome || isWordInDescrizione || isWordInMarca));
        }
      });

      this.sortScarpe(this.filtro.tipoOrdinamento);
    }
  }
}