import {Component, OnInit} from '@angular/core';
import {IssueService} from "../../services/issue.service";
import {IssueResponse} from "../../types/resoponse/issue-response";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {IssueFormComponent} from "../issue form/issue-form.component";
import {SprintFormComponent} from "../sprint form/sprint-form.component";
import {SprintService} from "../../services/sprint.service";
import {SprintDto} from "../../types/sprint-dto";

@Component({
  selector: 'app-backlog',
  templateUrl: './backlog.component.html',
  styleUrls: ['./backlog.component.scss'],
  providers: [DialogService]
})
export class BacklogComponent implements OnInit {

  issuesProductBacklog: IssueResponse[] = [];
  activeSprints: SprintDto[] = [];
  nonActiveSprints: SprintDto[] = [];

  ngOnInit() {
    this.getAllProductBacklog();
    this.getAllSprints();
  }

  constructor(private issueWebService: IssueService,private sprintWebService:SprintService, public dialogService: DialogService) {
  }

  getAllProductBacklog() {
    this.issueWebService.getIssuesFromProductBacklog(history.state.projectId).subscribe({
      next: result => {
        this.issuesProductBacklog = result
        this.issuesProductBacklog.sort((a, b) => a.id - b.id);
      },
      error: err => {
        console.log(err)
      }
    })
  }

  getAllSprints(){
    this.sprintWebService.getNonActiveSprints(history.state.projectId).subscribe({
      next: result =>{
        this.nonActiveSprints = result
        this.nonActiveSprints.sort((a, b) => a.id! - b.id!);
      },
      error: err => {
        console.log(err)
      }
    })

    this.sprintWebService.getActiveSprints(history.state.projectId).subscribe({
      next: result =>{
        this.activeSprints = result
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
    let formDialog = this.dialogService.open(IssueFormComponent, {
      header: 'Create new issue',
      width: '500px',
      data: data
    })
    formDialog.onClose.subscribe(() => {
      this.getAllProductBacklog();
    })
  }

  showSprintForm(){
    let formDialog = this.dialogService.open(SprintFormComponent,{
      header: 'Create new issue',
      width: '500px',
      data: {
        projectId: history.state.projectId
      }
    })
    formDialog.onClose.subscribe(() =>{
      this.getAllSprints()
    })
  }
}
