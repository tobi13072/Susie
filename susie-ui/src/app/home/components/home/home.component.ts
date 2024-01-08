import {Component, OnInit} from '@angular/core';
import {IssueResponse} from "../../../backlog/types/resoponse/issue-response";
import {HomeService} from "../../services/home.service";
import {colorPriorityTagById, namePriorityById} from "../../../shared/tag.priority.operations";
import {nameTypeById} from "../../../shared/tag.type.operations";
import {nameStatusById} from "../../../shared/tag.status.operations";
import {DodDto} from "../../types/dod-dto";
import {AuthService} from "../../../auth/services/auth.service";
import {DialogService} from "primeng/dynamicdialog";
import {DodEditComponent} from "../dod edit/dod-edit.component";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  providers: [DialogService]
})
export class HomeComponent implements OnInit{

  assignedIssue: IssueResponse[] = []
  dod: DodDto[] = []
  ngOnInit(){
    this.getAssignedIssue()
    this.getDod()
  }

  constructor(private homeWebService: HomeService, protected authService: AuthService, public dialogService: DialogService,) {
  }

  getAssignedIssue(){
    this.homeWebService.getIssuesAssignedToUser().subscribe({
      next: res =>{
        this.assignedIssue = res;
      }
    })
  }

  getDod(){
    this.homeWebService.getDod(history.state.projectId).subscribe({
      next: res =>{
        this.dod = res;
        this.dod.sort((a, b) => a.ruleID - b.ruleID);
      }
    })
  }

  editDod(){
    let editDodDialog = this.dialogService.open(DodEditComponent, {
      header: 'Edit definition of done',
      width: '30rem',
      data: {
        dod: this.dod
      }
    })
    editDodDialog.onClose.subscribe(() => {
      this.getDod();
    })
  }

  protected readonly history = history;
  protected readonly colorPriorityTagById = colorPriorityTagById;
  protected readonly nameTypeById = nameTypeById;
  protected readonly namePriorityById = namePriorityById;
  protected readonly nameStatusById = nameStatusById;
}
