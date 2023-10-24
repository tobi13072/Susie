import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ProjectService} from "../../../service/project/project.service";
import {ProjectDto} from "../../../types/project-dto";
import {DynamicDialogRef} from "primeng/dynamicdialog";
import {ConfirmationService} from "primeng/api";

@Component({
  templateUrl: './project-form.component.html',
  providers: [ConfirmationService]
})
export class ProjectFormComponent implements OnInit {

  constructor(private fb: FormBuilder, private projectWebService: ProjectService, public dialogRef: DynamicDialogRef,
              private confirmDialog: ConfirmationService) {
  }

  ngOnInit(): void {
  }

  projectForm = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    projectGoal: ['', Validators.required]
  });

  prepareDataToSend(): ProjectDto {
    return {
      projectID: null!,
      name: this.projectForm.value.name!,
      description: this.projectForm.value.description!,
      projectGoal: this.projectForm.value.projectGoal!
    }
  }

  onSubmit() {
    if (this.projectForm.valid) {
      this.projectWebService.createProject(this.prepareDataToSend()).subscribe({
        next: () => {
          this.dialogRef.close()
        },
        error: err => {
          this.confirmDialog.confirm({
            message: err.error.message,
            header: 'Error',
            icon: 'pi pi-exclamation-triangle',
            acceptVisible: false,
            rejectLabel: "OK",
            rejectIcon: 'pi'
          })
        }
      })
    }
  }
}
