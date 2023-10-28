import {Component, OnInit} from '@angular/core';
import {AuthService} from "./service/auth/auth.service";
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
    this.primengConfig.ripple =  true
  }

  constructor(public loginService: AuthService,public primengConfig: PrimeNGConfig, protected router: Router) {
  }

  protected readonly Component = Component;
}
