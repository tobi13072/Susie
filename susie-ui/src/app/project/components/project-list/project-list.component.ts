import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProjectDto} from "../../types/project-dto";
import {ProjectService} from "../../services/project.service";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {ProjectFormComponent} from "../project-form/project-form.component";
import {AuthService} from "../../../auth/services/auth.service";
import {Router} from "@angular/router";
import {ConfirmationService, MenuItem, PrimeIcons} from "primeng/api";
import {errorDialog} from "../../../shared/error.dialog";
import {delay} from "rxjs";

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.scss'],
  providers: [DialogService, ConfirmationService]
})
export class ProjectListComponent implements OnDestroy, OnInit {

  projects: ProjectDto[] = [];
  formDialog: DynamicDialogRef | undefined;

  projectMenu: MenuItem[] | undefined;
  protected menuActiveItem: number | undefined;

  ngOnInit(): void {
    this.getAllProjects()

    this.projectMenu = [
      {
        label: 'Edit',
        icon: PrimeIcons.FILE_EDIT,
        command: () => {
          this.showEditProjectForm();
        }
      },
      {
        label: 'Delete',
        icon: PrimeIcons.TRASH,
        command: () => {
          this.deleteProject();
        }
      }
    ];
  }

  constructor(private projectWebService: ProjectService, public dialogService: DialogService, protected loginService: AuthService,
              private router: Router, private confirmDialog: ConfirmationService, protected authService: AuthService) {
  }

  getAllProjects() {
    this.projectWebService.getProjects().subscribe({
      next: result => {
        this.projects = result;
        this.projects.sort((a, b) => a.projectID - b.projectID);
      },
      error: err => {
        console.log(err);
      }
    })
  }

  viewProjectDetails(project: ProjectDto) {
    this.router.navigateByUrl('home', {state: {projectId: project.projectID}})
  }

  showAddProjectForm() {
    this.formDialog = this.dialogService.open(ProjectFormComponent, {
      header: "Add project",
      width: '500px'
    });
    this.formDialog.onClose.subscribe(() => {
      this.getAllProjects()
    });
  }

  showEditProjectForm() {
    this.formDialog = this.dialogService.open(ProjectFormComponent, {
      header: "Edit project",
      width: '500px',
      data: {
        project: this.projects.find(project => project.projectID === this.menuActiveItem)
      }
    });
    this.formDialog.onClose.subscribe(() => {
      this.getAllProjects()
    });
  }

  deleteProject() {
    const removeRequest = () => {
      this.projectWebService.removeProject(this.menuActiveItem!).subscribe({
        next: () => {
          this.getAllProjects();
        },
        error: () => {
          this.confirmDialog.confirm(errorDialog('Something went wrong with the deletion'));
        }
      })
    }

    this.confirmDialog.confirm({
      header: "Are you sure you want delete this project?",
      message: "This will delete this project permanently. You cannot und this action.",
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: "Delete",
      rejectLabel: "Cancel",
      acceptIcon: 'pi',
      rejectIcon: 'pi',
      accept: removeRequest
    })
  }

  ngOnDestroy() {
    if (this.formDialog) {
      this.formDialog.close();
    }
  }

  protected readonly delay = delay;
}
