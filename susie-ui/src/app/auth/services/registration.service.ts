import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegistrationRequest} from "../types/request/registration-request";
import {Observable} from "rxjs";
import {RegistrationResponse} from "../types/response/registration-response";
import {env} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private readonly SERVER_BASE_PATH = env.apiUrl.concat('/auth/register');


  constructor(private http: HttpClient) {
  }

  registerUser(data: RegistrationRequest): Observable<RegistrationResponse> {
    return this.http.post<RegistrationResponse>(this.SERVER_BASE_PATH, data);
  }
}
