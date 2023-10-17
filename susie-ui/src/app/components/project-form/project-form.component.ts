import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ProjectWebService} from "../../service/project/project-web.service";
import {ProjectDto} from "../../types/project-dto";

@Component({
  selector: 'app-project',
  templateUrl: './project-form.component.html',
  styleUrls: ['./project-form.component.scss']
})
export class ProjectFormComponent implements OnInit {

  constructor(private fb: FormBuilder, private projectWebService: ProjectWebService) {
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
        next: result => {
        },
        error: err => {
          console.log(err);
        }
      })
    }
  }
}
