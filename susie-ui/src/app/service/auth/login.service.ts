import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {LoginRequest} from "../../types/auth/request/login-request";
import {LoginResponse} from "../../types/auth/response/login-response";


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private base_url: string = 'http://localhost:8081/api/auth/';
  private readonly SM_ROLE:string = "sm";
  private readonly PO_ROLE:string = "po";

  constructor(private http: HttpClient, private router: Router) {
  }

  loginUser(user: LoginRequest): Observable<LoginResponse> {
    let endpoint: string = `${this.base_url}sign-in`;
    return this.http.post<LoginResponse>(endpoint, user);
  }

  logoutUser() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('refresh_token');

    this.router.navigate(['']);
  }

  isLoggedIn(): boolean {
    let authToken = sessionStorage.getItem('token');
    return !!authToken;
  }

  private getUserRoles(): string[] | null {
    let roles = sessionStorage.getItem('roles');
    if (!!roles) {
      return roles.split(',')
    } else {
      return null;
    }
  }

  isScrumMaster(): boolean {
    const roles: any = this.getUserRoles();

    if (!!roles) {
      return roles.includes(this.SM_ROLE);
    } else {
      return false;
    }
  }

  isProductOwner(): boolean {
    const roles: any = this.getUserRoles();

    if (!!roles) {
      return roles.includes(this.PO_ROLE);
    } else {
      return false;
    }
  }

}
