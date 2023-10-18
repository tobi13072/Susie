import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ProjectWebService} from "../../../service/project/project-web.service";
import {ProjectDto} from "../../../types/project-dto";
import {DynamicDialogRef} from "primeng/dynamicdialog";

@Component({
  templateUrl: './project-form.component.html',
})
export class ProjectFormComponent implements OnInit {

  constructor(private fb: FormBuilder, private projectWebService: ProjectWebService, public dialogRef: DynamicDialogRef) {
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
          console.log(err);
        }
      })
    }
  }
}
