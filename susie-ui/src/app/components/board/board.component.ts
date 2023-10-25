import {Component, OnInit} from '@angular/core';
import {IssueDto} from "../../types/issue-dto";
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss']
})
export class BoardComponent implements OnInit {
  test1: IssueDto[] = [];
  test2: IssueDto[] = [];
  test3: IssueDto[] = [];
  test4: IssueDto[] = [];

  dragged: IssueDto | undefined | null;

  draggedStatusIndex: number = -1;

  data: any;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {

    this.data = this.route.snapshot.params['projectId'];

    this.test1 = [
      {id: 1, name: 'Take a shower', status: "test1"},
      {id: 2, name: 'Cook dinner', status: "test1"}
    ]
    this.test2 = [{id: 3, name: 'angular', status: 'test2'}]
  }

  dragStart(product: IssueDto, draggedStatus: number) {
    this.dragged = product;
    this.draggedStatusIndex = draggedStatus;
  }

  drop(newStatusIndex: number) {
    if (this.dragged) {
      let draggedProductIndex = this.findIndex(this.dragged);
      switch (newStatusIndex) {
        case 1:
          this.test1 = [...(this.test1 as IssueDto[]), this.dragged];
          break;
        case 2:
          this.test2 = [...(this.test2 as IssueDto[]), this.dragged];
          break;
        case 3:
          this.test3 = [...(this.test3 as IssueDto[]), this.dragged];
          break;
        case 4:
          this.test4 = [...(this.test4 as IssueDto[]), this.dragged];
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

  findIndex(product: IssueDto) {
    let index = -1;
    let tmp: IssueDto[] = [];

    switch (this.draggedStatusIndex) {
      case 1:
        tmp = this.test1 as IssueDto[];
        break;
      case 2:
        tmp = this.test2 as IssueDto[];
        break;
      case 3:
        tmp = this.test3 as IssueDto[];
        break;
      case 4:
        tmp = this.test4 as IssueDto[];
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
