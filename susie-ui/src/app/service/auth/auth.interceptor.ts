import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {
  }

  private getToken(): string | null {
    let token = sessionStorage.getItem('token');

    if (!!token) {
      return token;
    } else {
      return null;
    }
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authToken = this.getToken();

    if (authToken !== null) {
      request = request.clone({
        setHeaders: {
          Authorization: "Bearer " + authToken
        }
      })
    }

    return next.handle(request);
  }
}
