import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProjectDto} from "../../../types/project-dto";
import {ProjectService} from "../../../service/project/project.service";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {ProjectFormComponent} from "../project-form/project-form.component";
import {AuthService} from "../../../service/auth/auth.service";
import {Router} from "@angular/router";
import {MenuItem, PrimeIcons} from "primeng/api";

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.scss'],
  providers: [DialogService]
})
export class ProjectListComponent implements OnDestroy, OnInit {

  projects: ProjectDto[] = [];
  formDialog: DynamicDialogRef | undefined;
  projectContext: boolean = true;
  projectMenu: MenuItem[] | undefined;

  ngOnInit(): void {
    this.getAllProjects()

    this.projectMenu = [
      {
        label: 'New',
        icon: PrimeIcons.FILE_EDIT
      },
      {
        label: 'Delete',
        icon: PrimeIcons.TRASH
      }
    ];
  }

  constructor(private projectWebService: ProjectService, public dialogService: DialogService, protected loginService: AuthService, private router: Router) {
  }

  getAllProjects() {
    this.projectWebService.getProjects().subscribe({
      next: result => {
        this.projects = result;
      },
      error: err => {
        console.log(err);
      }
    })
  }

  viewProjectDetails(project: ProjectDto) {
    console.log(project.projectID);
    this.router.navigate(['board', project.projectID]);
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

  ngOnDestroy() {
    if (this.formDialog) {
      this.formDialog.close();
    }
  }
}
