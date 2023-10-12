import {CanActivateFn, Router} from '@angular/router';
import {LoginService} from "./login.service";
import {inject} from "@angular/core";

export const authGuard: CanActivateFn = (route, state) => {
  const loginService = inject(LoginService);
  const router: Router = inject(Router);

  if (!loginService.isLoggedIn()) {
    router.navigate(['']);
    return false
  }
  return true;
};
