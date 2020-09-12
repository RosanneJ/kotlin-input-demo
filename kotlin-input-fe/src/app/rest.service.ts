import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private httpClient: HttpClient) { }

  sendName(name: string): Observable<Object> {
    return this.httpClient.get(`http://localhost:8080/greeting?name=${name}`)
  }
}
