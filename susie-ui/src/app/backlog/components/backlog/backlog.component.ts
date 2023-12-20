import {Component, OnInit} from '@angular/core';
import {IssueService} from "../../services/issue.service";
import {IssueResponse} from "../../types/resoponse/issue-response";
import {DialogService} from "primeng/dynamicdialog";
import {IssueFormComponent} from "../issue form/issue-form.component";
import {SprintFormComponent} from "../sprint form/sprint-form.component";
import {SprintService} from "../../services/sprint.service";
import {SprintDto} from "../../types/sprint-dto";
import {ConfirmationService, MenuItem, PrimeIcons} from "primeng/api";
import {errorDialog} from "../../../shared/error.dialog";
import {confirmDeletion} from "../../../shared/delete.confirm";
import {getInitials} from "../../../shared/initials.generator";

@Component({
  selector: 'app-backlog',
  templateUrl: './backlog.component.html',
  styleUrls: ['./backlog.component.scss'],
  providers: [DialogService, ConfirmationService]
})
export class BacklogComponent implements OnInit {

  issuesProductBacklog: IssueResponse[] = [];
  activeSprints: SprintDto[] = [];
  nonActiveSprints: SprintDto[] = [];

  issueMenu: MenuItem[] | undefined;
  sprintMenu: MenuItem[] | undefined;
  assigneeMenu: MenuItem[] | undefined;
  isAssignee: any | undefined;
  menuActiveItem: number | undefined;

  ngOnInit() {
    this.getAllProductBacklog();
    this.getAllSprints();
    this.generateMenus();
  }

  constructor(private issueWebService: IssueService, private sprintWebService: SprintService, public dialogService: DialogService,
              private confirmDialog: ConfirmationService) {}


  generateMenus() {
    this.issueMenu = [
      {
        label: 'Edit',
        icon: PrimeIcons.FILE_EDIT,
        command: () => {
          this.editIssue();
        }
      },
      {
        label: 'Delete',
        icon: PrimeIcons.TRASH,
        command: () => {
          this.deleteIssue()
        }
      }
    ];

    this.sprintMenu =[
      {
        label: 'Edit',
        icon: PrimeIcons.FILE_EDIT,
        command: () => {
          this.editSprint()
        }
      },
      {
        label: 'Delete',
        icon: PrimeIcons.TRASH,
        command: () => {
          this.deleteSprint()
        }
      }
    ]
  }

  generateAssignMenu(){
    if (this.isAssignee) {
      this.assigneeMenu = [
        {
          label: 'Delete assignment',
          icon: PrimeIcons.TIMES,
          command: () => {
            this.deleteAssignment()
          }
        }
      ]
    }else{
      this.assigneeMenu = [
        {
          label: 'Assignee me',
          icon: PrimeIcons.CHECK_SQUARE,
          command: () => {
            this.assignIssue()
          }
        }
      ]
    }
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

  getAllSprints() {
    this.sprintWebService.getNonActiveSprints(history.state.projectId).subscribe({
      next: result => {
        this.nonActiveSprints = result
        this.nonActiveSprints.sort((a, b) => a.id! - b.id!);
      },
      error: err => {
        console.log(err)
      }
    })

    this.sprintWebService.getActiveSprints(history.state.projectId).subscribe({
      next: result => {
        this.activeSprints = result
      },
      error: err => {
        console.log(err)
      }
    })
  }

  createSprint(){
    let data = {
      projectId: history.state.projectId
    }
    this.showSprintForm(data,'Create new')
  }
  editSprint(){
    let data= {
      isEdit: true,
      sprint: this.nonActiveSprints.find(sprint => sprint.id === this.menuActiveItem)
    }
    this.showSprintForm(data,'Edit')
  }

  deleteSprint(){
    this.sprintWebService.deleteSprint(this.menuActiveItem!).subscribe({
      next: () =>{
        this.getAllSprints()
      }
    })
  }

  createIssue() {
    let data = {
      projectId: history.state.projectId
    };
    this.showIssueForm(data, 'Create new')
  }

  editIssue() {
    let data = {
      isEdit: true,
      issueId: this.menuActiveItem
    }
    this.showIssueForm(data,'Edit');
  }

  deleteIssue() {
    const removeIssue = () => {
      this.issueWebService.deleteIssue(this.menuActiveItem!).subscribe({
        next: result => {
          this.getAllProductBacklog();
        },
        error: err => {
          this.confirmDialog.confirm(errorDialog('Something went wrong with the deletion'));
        }
      })
      }

    this.confirmDialog.confirm(confirmDeletion('issue', removeIssue))
  }

  assignIssue(){
    this.issueWebService.assignIssueToLoggedUser(this.menuActiveItem!).subscribe({
      next: () =>{
        this.getAllProductBacklog()
      },
      error: err =>{
        console.log(err)
      }
    })
  }

  deleteAssignment(){
    this.issueWebService.deleteIssueAssignment(this.menuActiveItem!).subscribe({
      next: () =>{
        this.getAllProductBacklog()
      },
      error: err =>{
        console.log(err)
      }
    })
  }

  showIssueForm(data: Object, msg: string) {
    let formDialog = this.dialogService.open(IssueFormComponent, {
      header: `${msg} issue`,
      width: '500px',
      data: data
    })
    formDialog.onClose.subscribe(() => {
      this.getAllProductBacklog();
    })
  }
  showSprintForm(data: Object, msg: string) {
    let formDialog = this.dialogService.open(SprintFormComponent, {
      header: `${msg} sprint`,
      width: '500px',
      data: data
    })
    formDialog.onClose.subscribe(() => {
      this.getAllSprints()
    })
  }

  protected readonly getInitials = getInitials;
}
