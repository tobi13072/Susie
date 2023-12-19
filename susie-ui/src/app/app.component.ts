import {Component, OnInit} from '@angular/core';
import {AuthService} from "./auth/services/auth.service";
import {PrimeNGConfig} from "primeng/api";
import {Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  mainMenu: boolean = false;

  ngOnInit(): void {
  }

  constructor(public loginService: AuthService,public primengConfig: PrimeNGConfig, protected router: Router) {
    this.primengConfig.ripple =  true
  }

  getUserInitials(){
      let name = sessionStorage.getItem('name')
      let lastName = sessionStorage.getItem('lastName')

      return name!.charAt(0).concat(lastName!.charAt(0));
  }

  protected readonly history = history;
  protected readonly sessionStorage = sessionStorage;

}
