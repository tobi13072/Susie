import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent implements OnInit{

  projectForm = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    projectID: ['']
  });

  sendForm() {
    if (this.projectForm.valid) {
      console.log({
        name: this.projectForm.value.name,
        description: this.projectForm.value.description
      });
    }
  }

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {

  }
}
