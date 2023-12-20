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

  backlogMenu: MenuItem[] | undefined;
  menuActiveItem: number | undefined;

  ngOnInit() {
    this.getAllProductBacklog();
    this.getAllSprints();

    this.backlogMenu = [
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
  }

  constructor(private issueWebService: IssueService, private sprintWebService: SprintService, public dialogService: DialogService,
              private confirmDialog: ConfirmationService) {}

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
  showSprintForm() {
    let formDialog = this.dialogService.open(SprintFormComponent, {
      header: 'Create new issue',
      width: '500px',
      data: {
        projectId: history.state.projectId
      }
    })
    formDialog.onClose.subscribe(() => {
      this.getAllSprints()
    })
  }

  protected readonly getInitials = getInitials;
  protected readonly console = console;
}
