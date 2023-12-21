import {Component, OnInit} from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from "@angular/cdk/drag-drop";
import {SprintService} from "../../backlog/services/sprint.service";
import {IssueService} from "../../backlog/services/issue.service";
import {SprintDto} from "../../backlog/types/sprint-dto";
import {IssueResponse} from "../../backlog/types/resoponse/issue-response";
import {ConfirmationService} from "primeng/api";
import {errorDialog} from "../../shared/error.dialog";
import {colorPriorityTagById, namePriorityById} from "../../shared/tag.priority.operations";
import {nameTypeById} from "../../shared/tag.type.operations";


@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
  providers: [ConfirmationService]
})
export class BoardComponent implements OnInit {

  ngOnInit() {
    this.getData()
  }

  constructor(private sprintWebService: SprintService, private issueWebService: IssueService,private confirmDialog: ConfirmationService) {
  }

  toDo: IssueResponse[] = [];
  inProgress: IssueResponse[] = [];
  codeReview: IssueResponse[] = [];
  inTest: IssueResponse[] = [];
  done: IssueResponse[] = [];

  activeSprint: SprintDto | undefined;

  getData() {
    this.sprintWebService.getActiveSprint(history.state.projectId).subscribe({
      next: res => {
        this.activeSprint = res;
        if(!!res) this.getIssueFromActiveSprint(res.id!)
      },
      error: err => {
        this.confirmDialog.confirm(errorDialog(err.error.message))
      }
    })
  }

  clearBoard(){
    this.toDo = [];
    this.inProgress = [];
    this.inTest = [];
    this.codeReview = [];
    this.done = [];
  }
  getIssueFromActiveSprint(activeSprintID: number) {
    this.issueWebService.getIssueFromSprint(activeSprintID).subscribe({
      next: res => {
        for (let issue of res) {
          switch (issue.issueStatusID) {
            case 1:
              this.toDo.push(issue);
              break;
            case 2:
              this.inProgress.push(issue);
              break;
            case 3:
              this.codeReview.push(issue);
              break;
            case 4:
              this.inTest.push(issue);
              break;
            case 5:
              this.done.push(issue);
              break;
          }
        }
      }
    })
  }

  changeIssueStatus(issueId: number, statusId: number) {
    this.issueWebService.changeIssueStatus(issueId, statusId).subscribe({
      error: err => {
        this.confirmDialog.confirm(errorDialog(err.error.message));
        this.clearBoard();
        this.getData();
      }
    })
  }

  drop(event: CdkDragDrop<IssueResponse[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex,);
      let currentIssueId = event.container.data.at(event.currentIndex)!.id
      let newStatus = parseInt(event.container.id.toString().at(length-1)!)+1

      this.changeIssueStatus(currentIssueId, newStatus)
    }
  }

  protected readonly colorPriorityTagById = colorPriorityTagById;
  protected readonly nameTypeById = nameTypeById;
  protected readonly namePriorityById = namePriorityById;
}
