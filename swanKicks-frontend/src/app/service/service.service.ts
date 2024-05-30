import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { scarpa } from '../model/scarpa';
import { utente } from '../model/utente';
import { recensione } from '../model/recensione';
import { asta } from '../model/asta';
import { image } from '../model/image';
import { acquisto } from '../model/acquisto';
import { ordine } from '../model/ordine';


@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  constructor(private http: HttpClient) { }

  // ### SCARPE ###
  /**
   * FindAll generica per le scarpe
   */
  getScarpe(): Observable<scarpa[]>{
    return this.http.get<scarpa[]>('http://localhost:8080/api/scarpe/findAll');
  }

  /**
   * Get singola scarpa dato id
   */
  getScarpa(id: number): Observable<scarpa>{
    return this.http.get<scarpa>('http://localhost:8080/api/scarpe/' + id );
  }

  /**
   * Effettua una ricerca di tutti gli annunci inseriti dal venditore tramite cf
   */
  getScarpeByCF(cf: string): Observable<scarpa[]>{
    return this.http.get<scarpa[]>('http://localhost:8080/api/scarpe/' + cf);
  }

  /**
   * Prendi l'ultima scarpa aggiunta dall'utente, utile per salvare le aste  
   */
  getLastAddedByOwner(id: string): Observable<Number> {
    return this.http.get<Number>('http://localhost:8080/api/scarpe/getLastAddedByOwner/' + id)
  }

  /**
   * Prendi tutte le scarpe dato il codice fiscale
   */
  findAllByOwner(id: string): Observable<scarpa[]> {
    return this.http.get<scarpa[]>('http://localhost:8080/api/scarpe/findAllByOwner/' + id)
  }

  /**
   * SaveOrUpdate scarpa
   */
  setScarpa(body: {}) {
    return this.http.post('http://localhost:8080/api/scarpe', body);
  }

  /**
   * Delete scarpa dato l'id
   */
  deleteScarpa(id: Number) {
    return this.http.delete('http://localhost:8080/api/scarpe/' + id);
  }

  /**
   * Update scarpa dato id + body
   */
  updateScarpa(id: Number, body: {}): Observable<scarpa> {
    return this.http.put<scarpa>('http://localhost:8080/api/scarpe/' + id, body);
  }

  // ### UTENTE ###
  /**
   * Get singolo utente dato codice fiscale
   */
  getUtente(cf: string): Observable <utente>{
    return this.http.get<utente>('http://localhost:8080/api/utenti/' + cf );
  }

  /**
   * Elimina utente dato id
   */
  deleteUtente(cf: string) {
    return this.http.delete('http://localhost:8080/api/utenti' + cf);
  }

  /**
   * Update utente dato id + body
   */
  updateUtente(cf: string, body: {}): Observable<utente> {
    return this.http.put<utente>('http://localhost:8080/api/utenti/' + cf, body);
  }

  /**
   * Get dettagli utente dato sessionId dal backend
   */
  getUserDetails(sessionId: string | null | undefined) {
    return this.http.get<utente>(`http://localhost:8080/api/utenti/user-details?sessionId=` + sessionId);
  }

  // ### RECENSIONI ###
  /**
   * Crea recensione dato body
   */
  setRecensione(body: {}){
    return this.http.post('http://localhost:8080/api/recensioni', body);
  }

  /**
   * Trova recensioni dato la scarpa id
   */
  getRecensioniByScarpaID(id: number): Observable <recensione[]>{
    return this.http.get<recensione[]>('http://localhost:8080/api/recensioni/findByScarpa/' + id);
  }

  /**
   * Elimina recensione dato l'id
   */
  deleteRecensione(id: Number) {
    return this.http.delete('http://localhost:8080/api/recensioni/' + id);
  }

  // ### ACQUISTO ###
  /**
   * Crea acquisto dato body
   */
  setAcquisto(body: {}){
    return this.http.post('http://localhost:8080/api/acquisti', body);
  }

  /**
   * Trova acquisti dato l'id utente
   */
  getAcquistiByUtenteID(id: string): Observable <acquisto[]>{
    return this.http.get<acquisto[]>('http://localhost:8080/api/acquisti/findByUtente/' + id);
  }

  getCompletatoForAcquisto(id: number): Observable<boolean> {
    // Effettua una richiesta HTTP per ottenere lo stato 'completato' per l'acquisto specificato
    // Supponendo che ci sia un endpoint sul tuo server che restituisce lo stato completato per un determinato acquistoId
    return this.http.get<boolean>('http://localhost:8080/api/aste/findCompletatoById/' + id);
  }

  /**
   * Elimina acquisto dato l'id
   */
  deleteAcquisto(id: Number) {
    return this.http.delete('http://localhost:8080/api/acquisti/' + id);
  }

  getAcquisto(id: number): Observable<acquisto>{
    return this.http.get<acquisto>('http://localhost:8080/api/acquisti/' + id );
  }

  // ### ORDINI ###
  /**
   * Crea ordine dato body
   */
  setOrdine(body: {}){
    return this.http.post('http://localhost:8080/api/ordini', body);
  }

  /**
   * Trova ordine dato l'id utente
   */
  getOrdiniByUtenteID(id: string): Observable <ordine[]>{
    return this.http.get<ordine[]>('http://localhost:8080/api/ordini/findByUtente/' + id);
  }

  /**
   * Elimina ordine dato l'id
   */
  deleteOrdine(id: Number) {
    return this.http.delete('http://localhost:8080/api/ordini/' + id);
  }

  getOrdine(id: number): Observable<ordine>{
    return this.http.get<ordine>('http://localhost:8080/api/ordini/' + id );
  }

  // ### ASTE ###
  /**
   * Creazione asta dato body
   */
  setAsta(body: {}) {
    return this.http.post('http://localhost:8080/api/aste', body);
  }

  /**
   * Get asta by scarpa id
   */
  getAstaByScarpa(id: number): Observable <asta>{
    return this.http.get<asta>('http://localhost:8080/api/aste/findByScarpa/' + id);
  }

  /**
   * Update asta dato id
   */
  updateAsta(id: number, body: {}): Observable<asta> {
    return this.http.put<asta>('http://localhost:8080/api/aste/' + id, body);
  }

  // ### IMAGES ###
  /**
   * Creazione immagine dato body
   */
  createImage(body: {}) {
    return this.http.post('http://localhost:8080/api/images', body)
  }

  /**
   * Trova tutte le immagini dato id
   */
  findImagesByScarpaID(id: number): Observable<image[]> {
    return this.http.get<image[]>('http://localhost:8080/api/images/findByScarpa/' + id);
  }
}
