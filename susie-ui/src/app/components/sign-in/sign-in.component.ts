import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {LoginService} from "../../service/auth/login.service";
import {LoginRequest} from "../../types/auth/request/login-request";
import {Router} from "@angular/router";
import {ConfirmationService} from "primeng/api";

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
  providers: [ConfirmationService]
})
export class SignInComponent implements OnInit {

  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router, private confirmDialog: ConfirmationService) {

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
        this.router.navigate(['project']);
        this.loginService.saveToken(result);
        this.loginService.saveRoles(result);
      },
      error: err => {
        console.log(err);
        this.confirmDialog.confirm({
          message: "Incorrect username or password. Please try again.",
          header: 'Error',
          icon: 'pi pi-exclamation-triangle',
          acceptVisible: false,
          rejectLabel: "OK",
          rejectIcon: 'pi',
          reject: () => {
            this.loginForm.get('password')?.setValue('');
            this.loginForm.get('email')?.setValue('');
          }
        })
      }
    })
  }
}
