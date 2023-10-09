import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {RegistrationRequest} from "../../types/registration-request";
import {AuthWebInterfaceService} from "../../service/auth-web-interface.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {
  constructor(private _fb: FormBuilder,
              private authService: AuthWebInterfaceService,
              private router: Router) {
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

  onSubmit() {
    console.log(this.registrationForm.value);
    this.authService.registerUser(this.prepareDataToSend()).subscribe({
      next: result => {
        if (result.success) {
          console.log('Registration success');
          this.router.navigate(['project']).then();
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
