import {Component, OnInit} from '@angular/core';
import {AuthService} from "./service/auth/auth.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  ngOnInit(): void {
  }

  constructor(public loginService: AuthService) {
  }

  protected readonly Component = Component;
}
