import {Component, OnInit} from '@angular/core';
import {IssueRequest} from "../../types/issue-request";


@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {
  test1: IssueRequest[] = [];

  test2: IssueRequest[] = [];

  test3: IssueRequest[] = [];

  test4: IssueRequest[] = [];

  dragged: IssueRequest | undefined | null;

  draggedStatusIndex: number = -1;

  ngOnInit() {
    this.test1 = [
      {id: 1, name: 'Take a shower', status: "test1"},
      {id: 2, name: 'Cook dinner', status: "test1"}
    ]
    this.test2 = [{id: 3, name: 'pierdol angulara', status:'test2'}]
  }

  dragStart(product: IssueRequest, draggedStatus: number) {
    this.dragged = product;
    this.draggedStatusIndex = draggedStatus;
  }

  drop(event: any, newStatusIndex: number) {
    if (this.dragged) {
      let draggedProductIndex = this.findIndex(this.dragged);
      switch (newStatusIndex) {
        case 1:
          this.test1 = [...(this.test1 as IssueRequest[]), this.dragged];
          break;
        case 2:
          this.test2 = [...(this.test2 as IssueRequest[]), this.dragged];
          break;
        case 3:
          this.test3 = [...(this.test3 as IssueRequest[]), this.dragged];
          break;
        case 4:
          this.test4 = [...(this.test4 as IssueRequest[]), this.dragged];
          break;
      }

      switch (this.draggedStatusIndex) {
        case 1:
          this.test1 = this.test1?.filter((val, i) => i != draggedProductIndex);
          break;
        case 2:
          this.test2 = this.test2?.filter((val, i) => i != draggedProductIndex);
          break;
        case 3:
          this.test3 = this.test3?.filter((val, i) => i != draggedProductIndex);
          break;
        case 4:
          this.test4 = this.test4?.filter((val, i) => i != draggedProductIndex);
          break;
      }
      this.draggedStatusIndex = -1;
      this.dragged = null;
    }
  }

  findIndex(product: IssueRequest) {
    let index = -1;
    let tmp: IssueRequest[] = [];

    switch (this.draggedStatusIndex) {
      case 1:
        tmp = this.test1 as IssueRequest[];
        break;
      case 2:
        tmp = this.test2 as IssueRequest[];
        break;
      case 3:
        tmp = this.test3 as IssueRequest[];
        break;
      case 4:
        tmp = this.test4 as IssueRequest[];
        break;
    }

    for (let i = 0; i < tmp.length; i++) {
      if (product.id === tmp[i].id) {
        index = i;
        break;
      }
    }
    return index;
  }

}
