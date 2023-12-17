import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ProjectService} from "../../services/project.service";
import {ProjectDto} from "../../types/project-dto";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {ConfirmationService} from "primeng/api";
import {errorDialog} from "../../../shared/error.dialog";

@Component({
  templateUrl: './project-form.component.html',
  providers: [ConfirmationService]
})
export class ProjectFormComponent implements OnInit {

  constructor(private fb: FormBuilder, private projectWebService: ProjectService, public dialogRef: DynamicDialogRef, public dialogConfig: DynamicDialogConfig,
              private confirmDialog: ConfirmationService) {
  }

  ngOnInit(): void {
    if (this.dialogConfig.data) {
      let editedProject = this.dialogConfig.data.project;
      this.projectForm.get('name')?.setValue(editedProject?.name);
      this.projectForm.get('description')?.setValue(editedProject?.description);
      this.projectForm.get('projectGoal')?.setValue(editedProject?.projectGoal);
    }
  }

  projectForm = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    projectGoal: ['', Validators.required]
  });

  prepareDataToSend(): ProjectDto {
    return {
      projectID: this.dialogConfig.data ? this.dialogConfig.data.project.projectID : null!,
      name: this.projectForm.value.name!,
      description: this.projectForm.value.description!,
      projectGoal: this.projectForm.value.projectGoal!
    }
  }

  onSubmit() {
    if (this.projectForm.valid) {
      if (this.dialogConfig.data) {
        this.onSubmitEdit();
      } else {
        this.onSubmitCreate();
      }
    }
  }

  onSubmitEdit() {
    this.projectWebService.updateProject(this.prepareDataToSend()).subscribe({
      next: () => {
        this.dialogRef.close()
      },
      error: err => {
        this.confirmDialog.confirm(errorDialog(err))
      }
    })
  }

  onSubmitCreate() {
    this.projectWebService.createProject(this.prepareDataToSend()).subscribe({
      next: () => {
        this.dialogRef.close()
      },
      error: err => {
        this.confirmDialog.confirm(errorDialog(err))
      }
    })
  }
}
