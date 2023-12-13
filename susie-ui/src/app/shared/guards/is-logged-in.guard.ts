import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from "../../auth/services/auth.service";
import {inject} from "@angular/core";

export const isLoggedInGuard: CanActivateFn = () => {
  const loginService: AuthService = inject(AuthService);
  const router: Router = inject(Router);

  if (!loginService.isLoggedIn()) {
    router.navigate(['']);
    return false
  }
  return true;
};
