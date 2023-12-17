import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {RegistrationRequest} from "../../types/request/registration-request";
import {RegistrationService} from "../../services/registration.service";
import {Router} from "@angular/router";
import {LoginRequest} from "../../types/request/login-request";
import {AuthService} from "../../services/auth.service";
import {passwordMatchValidator} from "../../../shared/password-match.validator";
import {ConfirmationService} from "primeng/api";
import {errorDialog} from "../../../shared/error.dialog";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss'],
  providers: [ConfirmationService]
})
export class SignUpComponent implements OnInit {

  private readonly NAMES_REGEX: string = '^[A-Z][a-zA-Z\\s\'-]+$';

  constructor(private _fb: FormBuilder, private registrationService: RegistrationService, private router: Router, private loginService: AuthService,
              private confirmDialog: ConfirmationService) {
  }

  ngOnInit(): void {
  }

  registrationForm = this._fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    confirmPassword: ['',Validators.required],
    firstName: ['', [Validators.required, Validators.pattern(this.NAMES_REGEX)]],
    lastName: ['', [Validators.required, Validators.pattern(this.NAMES_REGEX)]],
    isScrumMaster: [false, Validators.required]
  },{
    validators: passwordMatchValidator
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
        this.router.navigateByUrl('project');
      }
    })
  }

  onSubmit() {
    this.registrationService.registerUser(this.prepareDataToSend()).subscribe({
      next: () => {
        this.loginService.setUserInfo();
        this.loginAfterRegister();
      },
      error: () => {
        this.confirmDialog.confirm(errorDialog('Something was wrong. Try again.'));
      }
    })
  }
}
