import {CanActivateFn, Router} from '@angular/router';
import {LoginService} from "../auth/login.service";
import {inject} from "@angular/core";

export const isLoggedInGuard: CanActivateFn = () => {
  const loginService: LoginService = inject(LoginService);
  const router: Router = inject(Router);

  if (!loginService.isLoggedIn()) {
    router.navigate(['']);
    return false
  }
  return true;
};
