import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {LoginRequest} from "../../types/auth/request/login-request";
import {LoginResponse} from "../../types/auth/response/login-response";
import {env} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private BASE_URL: string = env.apiUrl.concat("/auth");

  constructor(private http: HttpClient, private router: Router) {
  }

  loginUser(user: LoginRequest): Observable<LoginResponse> {
    let endpoint: string = `${this.BASE_URL}/sign-in`;
    return this.http.post<LoginResponse>(endpoint, user);
  }

  logoutUser() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('refresh_token');
    sessionStorage.removeItem('roles');

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
      return roles.includes(env.projectRoles.scrum_master);
    } else {
      return false;
    }
  }

  isProductOwner(): boolean {
    const roles: any = this.getUserRoles();

    if (!!roles) {
      return roles.includes(env.projectRoles.product_owner);
    } else {
      return false;
    }
  }

}
