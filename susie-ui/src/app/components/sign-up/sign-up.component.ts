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

  private readonly NAMES_REGEX: string = '^[A-Z][a-zA-Z\\s\'-]+$';

  constructor(private _fb: FormBuilder, private registrationService: RegistrationService, private router: Router, private loginService: LoginService) {
  }

  ngOnInit(): void {
  }

  registrationForm = this._fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    firstName: ['', [Validators.required, Validators.pattern(this.NAMES_REGEX)]],
    lastName: ['', [Validators.required, Validators.pattern(this.NAMES_REGEX)]],
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
        this.loginService.saveToken(result);
        this.loginService.saveRoles(result);
        this.router.navigate(['project']);
      }
    })
  }

  onSubmit() {
    this.registrationService.registerUser(this.prepareDataToSend()).subscribe({
      next: () => {
        this.loginAfterRegister();
      },
      error: err => {
        console.log(err);
      }
    })
  }
}
