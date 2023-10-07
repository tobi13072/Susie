import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MenuControlService {
  private showMenuBar: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  get getMenuStatus() {
    return this.showMenuBar.asObservable();
  }

  changeMenuStatus(newStatus: boolean) {
    this.showMenuBar.next(newStatus);
  }
}
