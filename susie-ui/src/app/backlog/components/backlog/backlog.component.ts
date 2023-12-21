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
import {confirmStop} from "../../../shared/stop.confirm";
import {IssueDetailsComponent} from "../issue details/issue-details.component";
import {colorPriorityTagById, namePriorityById} from "../../../shared/tag.priority.operations";
import {nameTypeById} from "../../../shared/tag.type.operations";
import {AuthService} from "../../../auth/services/auth.service";

@Component({
  selector: 'app-backlog',
  templateUrl: './backlog.component.html',
  styleUrls: ['./backlog.component.scss'],
  providers: [DialogService, ConfirmationService]
})
export class BacklogComponent implements OnInit {

  issuesProductBacklog: IssueResponse[] = [];
  activeSprint: SprintDto | undefined;
  nonActiveSprints: SprintDto[] = [];

  issueMenu: MenuItem[] | undefined;
  sprintMenu: MenuItem[] | undefined;
  assigneeMenu: MenuItem[] | undefined;
  isAssignee: any | undefined;
  isActive: any | undefined;
  menuActiveItem: number | undefined;
  onDropActiveSprint: SprintDto | undefined;

  ngOnInit() {
    this.getAllData();
    this.generateIssueMenu();
  }

  constructor(private issueWebService: IssueService, private sprintWebService: SprintService, public dialogService: DialogService,
              private confirmDialog: ConfirmationService, protected authService: AuthService) {
  }


  generateIssueMenu() {
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
  }

  generateSprintMenu() {
    if (!this.isActive) {
      this.sprintMenu = [
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
        },
        {
          label: 'Start sprint',
          icon: PrimeIcons.PLAY,
          command: () => {
            this.startSprint()
          }
        }
      ]
    } else {
      this.sprintMenu = [
        {
          label: 'Stop sprint',
          icon: PrimeIcons.PAUSE,
          command: () => {
            this.stopSprint()
          }
        }
      ]
    }
  }

  generateAssignMenu() {
    let assigneeOrDeleteAssignment;

    if (this.isAssignee) {
      assigneeOrDeleteAssignment =
        {
          label: 'Delete assignment',
          icon: PrimeIcons.TIMES,
          command: () => {
            this.deleteAssignment()
          }
        }
    } else {
      assigneeOrDeleteAssignment =
        {
          label: 'Assignee me',
          icon: PrimeIcons.CHECK_SQUARE,
          command: () => {
            this.assignIssue()
          }
        }
    }
    this.assigneeMenu = [
      assigneeOrDeleteAssignment!
    ]
  }

  getAllData() {
    this.getAllProductBacklog()
    this.getAllSprints()
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

  getIssuesFromSprints() {
    for (let sprint of this.nonActiveSprints) {
      this.getAllIssuesFromSingleSprint(sprint.id!, false)
    }
    if (!!this.activeSprint) {
      this.getAllIssuesFromSingleSprint(this.activeSprint.id!, true)
    }
  }

  getAllIssuesFromSingleSprint(sprintId: number, isActive: boolean) {
    this.issueWebService.getIssueFromSprint(sprintId).subscribe({
      next: res => {
        if (!isActive) {
          const index = this.nonActiveSprints.findIndex(element => element.id === sprintId)
          this.nonActiveSprints[index].issues = res;
        }
        if (isActive) {
          this.activeSprint!.issues = res;
        }
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
        this.getIssuesFromSprints()
      },
      error: err => {
        console.log(err)
      }
    })

    this.sprintWebService.getActiveSprint(history.state.projectId).subscribe({
      next: result => {
        this.activeSprint = result
        this.getIssuesFromSprints()
      },
      error: err => {
        console.log(err)
      }
    })
  }

  createSprint() {
    let data = {
      projectId: history.state.projectId
    }
    this.showSprintForm(data, 'Create new')
  }

  editSprint() {
    let data = {
      isEdit: true,
      sprint: this.nonActiveSprints.find(sprint => sprint.id === this.menuActiveItem)
    }
    this.showSprintForm(data, 'Edit')
  }

  deleteSprint() {
    let removeSprint = () => {
      this.sprintWebService.deleteSprint(this.menuActiveItem!).subscribe({
        next: () => {
          this.getAllData()
        }
      })
    }
    this.confirmDialog.confirm(confirmDeletion('sprint', removeSprint))
  }

  startSprint() {
    this.sprintWebService.startSprint(this.menuActiveItem!).subscribe({
      next: () => {
        this.getAllData()
      },
      error: err => {
        console.log(err)
        this.confirmDialog.confirm(errorDialog(err.error.message))
      }
    })
  }

  stopSprint() {
    let stopSprint = () => {
      this.sprintWebService.stopSprint(history.state.projectId).subscribe({
        next: () => {
          this.getAllData()
        },
        error: err => {
          console.log(err)
          this.confirmDialog.confirm(errorDialog(err.error.message))
        }
      })
    }
    this.confirmDialog.confirm(confirmStop('sprint', stopSprint))
  }

  dropOnSprintAction(sprint: SprintDto) {
    this.dynamicDeleteIssueFromSprint(sprint.id!).subscribe({
      next: () => {
        this.addIssueToSprint(sprint.id!)
      }
    })
  }

  dropOnBacklogAction(sprint: SprintDto) {
    if (!!sprint) {
      if (!!sprint.id) {
        this.deleteIssueFromSprint(sprint.id!)
      }
    }
  }

  addIssueToSprint(sprintId: number) {
    this.sprintWebService.addIssueToSprint(this.menuActiveItem!, sprintId).subscribe({
      next: () => {
        this.getAllData()
      },
      error: err => {
        this.confirmDialog.confirm(errorDialog(err.error.message))
      }
    })
  }

  dynamicDeleteIssueFromSprint(sprintId: number) {
    return this.sprintWebService.deleteIssueFromSprint(this.menuActiveItem!, sprintId)
  }

  deleteIssueFromSprint(sprintID: number) {
    this.sprintWebService.deleteIssueFromSprint(this.menuActiveItem!, sprintID).subscribe({
      next: () => {
        this.getAllData()
      },
      error: err => {
        this.confirmDialog.confirm(errorDialog(err.error.message))
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
    this.showIssueForm(data, 'Edit');
  }

  deleteIssue() {
    const removeIssue = () => {
      this.issueWebService.deleteIssue(this.menuActiveItem!).subscribe({
        next: () => {
          this.getAllData()
        },
        error: err => {
          this.confirmDialog.confirm(errorDialog(err.error.message));
        }
      })
    }

    this.confirmDialog.confirm(confirmDeletion('issue', removeIssue))
  }

  assignIssue() {
    this.issueWebService.assignIssueToLoggedUser(this.menuActiveItem!).subscribe({
      next: () => {
        this.getAllData()
      },
      error: err => {
        console.log(err)
      }
    })
  }

  deleteAssignment() {
    this.issueWebService.deleteIssueAssignment(this.menuActiveItem!).subscribe({
      next: () => {
        this.getAllData()
      },
      error: err => {
        console.log(err)
      }
    })
  }

  showIssueForm(data: Object, msg: string) {
    let formDialog = this.dialogService.open(IssueFormComponent, {
      header: `${msg} issue`,
      width: '30rem',
      data: data
    })
    formDialog.onClose.subscribe(() => {
      this.getAllData();
    })
  }

  showSprintForm(data: Object, msg: string) {
    let formDialog = this.dialogService.open(SprintFormComponent, {
      header: `${msg} sprint`,
      width: '30rem',
      data: data
    })
    formDialog.onClose.subscribe(() => {
      this.getAllSprints()
    })
  }

  showIssueDetails(issue: IssueResponse) {
    this.dialogService.open(IssueDetailsComponent, {
      header: `Details of "${issue.name}"`,
      width: '40rem',
      data: {
        issueId: issue.id
      }
    })
  }

  protected readonly getInitials = getInitials;
  protected readonly namePriorityById = namePriorityById;
  protected readonly colorPriorityTagById = colorPriorityTagById;
  protected readonly nameTypeById = nameTypeById;
}
