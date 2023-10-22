import { Component, OnInit } from '@angular/core';
import {LoginService} from "./service/auth/login.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  ngOnInit(): void {
  }

  constructor(public loginService: LoginService) {
  }


  protected readonly Component = Component;
}
