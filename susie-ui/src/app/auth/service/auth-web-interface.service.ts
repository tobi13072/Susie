import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {RegistrationRequest} from "../../types/registration-request";
import {Observable} from "rxjs";
import {RegistrationResponse} from "../../types/registration-response";

@Injectable({
  providedIn: 'root'
})
export class AuthWebInterfaceService {

  private readonly SERVER_BASE_PATH = 'http://localhost:8081/api';
  private readonly AUTH_REGISTER_PATH = '/auth/register';

  constructor(private http: HttpClient) { }

  registerUser(data: RegistrationRequest): Observable<RegistrationResponse> {
    return this.http.post<RegistrationResponse>(this.SERVER_BASE_PATH.concat(this.AUTH_REGISTER_PATH), data);
  }
}
