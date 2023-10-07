import { Component, OnInit } from '@angular/core';
import {MenuControlService} from "./service/menu-control.service";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  showMenuBar: boolean = false;
  ngOnInit(): void {
    this.menuBarService.getMenuStatus.subscribe((menuStatus) => {
      this.showMenuBar = menuStatus;
    })
  }

  constructor(private menuBarService: MenuControlService) {
  }


  protected readonly Component = Component;
}
