import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {LoginRequest} from "../types/request/login-request";
import {LoginResponse} from "../types/response/login-response";
import {env} from "../../../environments/environment";
import {RefreshTokenResponse} from "../types/response/refreshToken-response";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private BASE_URL: string = env.apiUrl.concat("/auth");

  constructor(private http: HttpClient, private router: Router) {
  }

  loginUser(user: LoginRequest): Observable<LoginResponse> {
    let endpoint: string = `${this.BASE_URL}/sign-in`;
    return this.http.post<LoginResponse>(endpoint, user);
  }

  refreshToken(refreshToken: string): Observable<RefreshTokenResponse> {
    const endpoint: string = `${this.BASE_URL}/refresh`;
    const refreshData = new FormData;
    refreshData.append('refreshToken', refreshToken);

    return this.http.post<RefreshTokenResponse>(endpoint, refreshData);
  }

  saveToken(response: LoginResponse | RefreshTokenResponse) {
    sessionStorage.setItem('token', response.access_token);
    sessionStorage.setItem('refresh_token', response.refresh_token);
  }

  saveRoles(response: LoginResponse) {
    sessionStorage.setItem('roles', response.userRoles.map((role: { name: any; }) => role.name).join(','));
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
