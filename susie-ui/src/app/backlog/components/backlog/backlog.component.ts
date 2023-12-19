import {Component, OnInit} from '@angular/core';
import {IssueService} from "../../services/issue.service";
import {IssueResponse} from "../../types/resoponse/issue-response";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {IssueFormComponent} from "../issue form/issue-form.component";

@Component({
  selector: 'app-backlog',
  templateUrl: './backlog.component.html',
  styleUrls: ['./backlog.component.scss'],
  providers: [DialogService]
})
export class BacklogComponent implements OnInit {

  issuesProductBacklog: IssueResponse[] = [];

  ngOnInit() {
    this.getAllIssue();
  }

  constructor(private issueWebService: IssueService, public dialogService: DialogService) {
  }

  getAllIssue() {
    this.issueWebService.getIssuesFromProductBacklog(history.state.projectId).subscribe({
      next: result => {
        this.issuesProductBacklog = result
      },
      error: err => {
        console.log(err)
      }
    })
  }

  createIssue(){
    let data = {
      projectId: history.state.projectId
    };
    this.showIssueForm(data)
  }

  editIssue(){

  }

  showIssueForm(data: Object) {
    let formDialog: DynamicDialogRef | undefined;
    formDialog = this.dialogService.open(IssueFormComponent, {
      header: 'Create new issue',
      width: '500px',
      data: data
    })
    formDialog.onClose.subscribe(() => {
      this.getAllIssue();
    })
  }

}
