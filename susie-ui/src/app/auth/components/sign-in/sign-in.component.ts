import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {LoginRequest} from "../../types/request/login-request";
import {Router} from "@angular/router";
import {ConfirmationService} from "primeng/api";
import {errorDialog} from "../../../shared/error.dialog";

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
  providers: [ConfirmationService]
})
export class SignInComponent implements OnInit {

  constructor(private fb: FormBuilder, private loginService: AuthService, private router: Router, private confirmDialog: ConfirmationService) {

  }

  ngOnInit(): void {
  }

  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  })

  prepareDataToSend(): LoginRequest {
    return {
      email: this.loginForm.value.email!,
      password: this.loginForm.value.password!
    }
  }

  onSubmit() {
    this.loginService.loginUser(this.prepareDataToSend()).subscribe({
      next: result => {
        this.router.navigateByUrl('project');
        this.loginService.saveToken(result);
        this.loginService.saveRoles(result);
        this.loginService.setUserInfo();
      },
      error: () => {
        this.confirmDialog.confirm(errorDialog('Incorrect username or password. Please try again.'));
      }
    })
  }
}
