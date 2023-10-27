import {Component, OnInit} from '@angular/core';
import {AuthService} from "./service/auth/auth.service";
import {PrimeNGConfig} from "primeng/api";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  ngOnInit(): void {
    this.primengConfig.ripple =  true
  }

  constructor(public loginService: AuthService,public primengConfig: PrimeNGConfig) {
  }

  protected readonly Component = Component;
}
