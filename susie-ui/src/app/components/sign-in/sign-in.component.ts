import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {LoginService} from "../../service/auth/login.service";
import {LoginRequest} from "../../types/auth/request/login-request";
import {Router} from "@angular/router";
import {LoginResponse} from "../../types/auth/response/login-response";

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router) {

  }

  ngOnInit(): void {
  }

  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  })

  prepareDataToSend(): LoginRequest{
    return {
      email: this.loginForm.value.email!,
      password: this.loginForm.value.password!
    }
  }

  addDataToSessionStorage(result: LoginResponse){
    sessionStorage.setItem('token',result.access_token);
    sessionStorage.setItem('refresh_token',result.refresh_token);
    sessionStorage.setItem('roles',result.userRoles.map((role: { name: any; }) => role.name).join(','));
  }

  onSubmit() {
    this.loginService.loginUser(this.prepareDataToSend()).subscribe({
      next: result => {
        console.log(result);
        this.router.navigate(['board']);
        this.addDataToSessionStorage(result);
      },
      error: err => {
        console.log(err);
      }
    })
  }

}
