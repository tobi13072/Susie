import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {RegistrationRequest} from "../../types/auth/request/registration-request";
import {RegistrationService} from "../../service/auth/registration.service";
import {Router} from "@angular/router";
import {LoginRequest} from "../../types/auth/request/login-request";
import {LoginService} from "../../service/auth/login.service";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {
  constructor(private _fb: FormBuilder, private registrationService: RegistrationService, private router: Router, private loginService: LoginService) {
  }

  ngOnInit(): void {
  }

  registrationForm = this._fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    isScrumMaster: [false, Validators.required]
  });

  private prepareDataToSend(): RegistrationRequest {
    return {
      email: this.registrationForm.value.email!,
      firstName: this.registrationForm.value.firstName!,
      lastName: this.registrationForm.value.lastName!,
      password: this.registrationForm.value.password!,
      isScrumMaster: this.registrationForm.value.isScrumMaster!
    };
  }

  loginAfterRegister() {
    let loginData: LoginRequest = {
      email: this.registrationForm.value.email!,
      password: this.registrationForm.value.password!
    };
    this.loginService.loginUser(loginData).subscribe({
      next: result => {
        sessionStorage.setItem('token', result.access_token);
        sessionStorage.setItem('refresh_token', result.refresh_token);
        sessionStorage.setItem('roles', result.userRoles.map((role: { name: any; }) => role.name).join(','));
      },
      error: err => {
        console.log(err);
      }
    })
  }

  checkScrumMasterSwitch() {
    if (this.registrationForm.value.isScrumMaster) {
      this.router.navigate(['project']).then();
    }
  }

  onSubmit() {
    this.registrationService.registerUser(this.prepareDataToSend()).subscribe({
      next: result => {
        if (result.success) {
          console.log('Registration success');
          this.loginAfterRegister();
          this.checkScrumMasterSwitch();

        } else {
          console.log('Registration failure')
        }
      },
      error: err => {
        console.log(err);
      }
    })
  }
}
