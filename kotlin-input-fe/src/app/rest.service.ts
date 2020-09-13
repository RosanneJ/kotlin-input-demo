import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RestService {
  host = `http://localhost:8080`;

  constructor(private httpClient: HttpClient) { }

  sendName(name: string): Observable<Object> {
    return this.httpClient.get(`${this.host}/greeting?name=${name}`)
  }

  sendProblem(name: string, problem: number, input: string): Observable<Object> {
    return this.httpClient.post(`${this.host}/kotlin-input?name=${name}&problem=${problem}`, input);
  }
}
