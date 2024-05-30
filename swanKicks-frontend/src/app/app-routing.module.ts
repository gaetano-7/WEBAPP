import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SupportoComponent } from './components/sidenav/supporto/supporto.component';
import { ChiSiamoComponent } from './components/sidenav/chi-siamo/chi-siamo.component';
import { ProfiloComponent } from './components/profilo/profilo.component';
import { AggiungiAnnuncioComponent } from './components/aggiungi-annuncio/aggiungi-annuncio.component';
import { AdminCplComponent } from './components/admin-cpl/admin-cpl.component';
import { AnnuncioComponent } from './components/annuncio/annuncio.component';
import { CarrelloComponent } from './components/carrello/carrello.component';
import { OrdiniComponent } from './components/ordini/ordini.component';

const routes: Routes = [
  {path: '', component: DashboardComponent, children: [
    {path: '', redirectTo: 'home', pathMatch: 'full'},
    {path: 'home', component: HomeComponent},
    {path: 'supporto', component: SupportoComponent},
    {path: 'chi-siamo', component: ChiSiamoComponent},
    {path: 'profilo', component: ProfiloComponent},
    {path: 'aggiungi-annuncio', component: AggiungiAnnuncioComponent},
    {path: 'admin-cpl', component: AdminCplComponent},
    {path: 'annuncio/:id', component: AnnuncioComponent},
    { path: 'carrello/:id', component: CarrelloComponent},
    { path: 'ordini/:id', component: OrdiniComponent},
  ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
