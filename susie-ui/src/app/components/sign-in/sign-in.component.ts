import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {MenuControlService} from "../../service/menu-control.service";

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit{

  constructor(private fb: FormBuilder, private menuBarService: MenuControlService) {

  }
  ngOnInit(): void {
  }

  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['',Validators.required]
  })

  onSubmit(){
    if(this.loginForm.valid) {
      this.menuBarService.changeMenuStatus(true);
      console.log({email: this.loginForm.value.email, password: this.loginForm.value.password});
    }
  }
}
