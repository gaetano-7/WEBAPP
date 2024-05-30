import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { SupportoComponent } from './components/sidenav/supporto/supporto.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { ChiSiamoComponent } from './components/sidenav/chi-siamo/chi-siamo.component';
import { HomeComponent } from './components/home/home.component';
import { AdminCplComponent } from './components/admin-cpl/admin-cpl.component';
import { AnnuncioComponent } from './components/annuncio/annuncio.component';
import { CarrelloComponent } from './components/carrello/carrello.component';

import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { AggiungiAnnuncioComponent } from './components/aggiungi-annuncio/aggiungi-annuncio.component';
import { ProfiloComponent } from './components/profilo/profilo.component'
import { OrdiniComponent } from './components/ordini/ordini.component';

import { PagamentoComponent } from './components/pagamento/pagamento.component';
import { CheckoutComponent } from './components/checkout/checkout.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    HomeComponent,
    AnnuncioComponent,
    CarrelloComponent,
    ChiSiamoComponent,
    SupportoComponent,
    AdminCplComponent,
    AggiungiAnnuncioComponent,
    ProfiloComponent,
    OrdiniComponent,
    PagamentoComponent,
    CheckoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,

    FormsModule,
    ReactiveFormsModule,

    MatFormFieldModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    MatToolbarModule,
    MatIconModule,
    MatTableModule,
    MatCardModule,
    MatInputModule,
    MatMenuModule,
    MatGridListModule,
    MatSelectModule,
    MatDialogModule,
    FontAwesomeModule,
    MatCheckboxModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
