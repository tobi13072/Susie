import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProjectDto} from "../../../types/project-dto";
import {ProjectWebService} from "../../../service/project/project-web.service";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {ProjectFormComponent} from "../project-form/project-form.component";

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.scss'],
  providers: [DialogService]
})
export class ProjectListComponent implements OnDestroy, OnInit {

  projects: ProjectDto[] = [];
  formDialog: DynamicDialogRef | undefined;

  ngOnInit(): void {
    this.getAllProjects()
  }

  constructor(private projectWebService: ProjectWebService, public dialogService: DialogService) {
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
    console.log(project);
  }

  showAddProjectForm() {
    this.formDialog = this.dialogService.open(ProjectFormComponent, {
      header: "Add project",
      width: '30%'
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
