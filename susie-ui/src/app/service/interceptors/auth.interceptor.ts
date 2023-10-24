import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {catchError, from, Observable, switchMap, throwError} from 'rxjs';
import {LoginService} from "../auth/login.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private loginService: LoginService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authToken = sessionStorage.getItem('token');

    if (!!authToken && !request.url.includes('api/auth/refresh')) {
      request = request.clone({
        setHeaders: {
          Authorization: "Bearer " + authToken
        }
      });
    }

    return next.handle(request).pipe(catchError(err => {
      if (err instanceof HttpErrorResponse && !request.url.includes('api/auth/sign-in') &&
        !request.url.includes('api/auth/register') && err.status === 401) {
        return this.handleAuthError(request, next);
      }
      return throwError(() => err);
    }))
  }

  private handleAuthError(request: HttpRequest<any>, next: HttpHandler) {
    return from(this.loginService.refreshToken(sessionStorage.getItem('refresh_token')!).pipe(
        switchMap(result => {

          this.loginService.saveToken(result);

          request = request.clone({
            setHeaders: {
              Authorization: 'Bearer ' + result.access_token,
            }
          });
          return next.handle(request)
        }),
        catchError((error) => {
          if (error.status == 401) {
            this.loginService.logoutUser();
          }
          return throwError(() => error);
        })
      )
    )
  }
}

