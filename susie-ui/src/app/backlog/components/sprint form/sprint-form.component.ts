import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {SprintService} from "../../services/sprint.service";
import {SprintRequest} from "../../types/request/sprint-request";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";

@Component({
  selector: 'app-sprint-form',
  templateUrl: './sprint-form.component.html',
  styleUrls: ['./sprint-form.component.scss']
})
export class SprintFormComponent implements OnInit{

  ngOnInit() {
  }

  constructor(private fb:FormBuilder, private sprintWebService: SprintService, public dialogRef: DynamicDialogRef,
              public dialogConfig: DynamicDialogConfig){}

  sprintForm = this.fb.group({
    name: ['', Validators.required],
    sprintGoal: ['', Validators.required],
    startTime: ['']
  })


  prepareDataToSend():SprintRequest{
    return {
      name: this.sprintForm.value.name!,
      startTime: this.sprintForm.value.startTime!,
      active: false,
      sprintGoal: this.sprintForm.value.sprintGoal!,
      projectID: this.dialogConfig.data.projectId
    }
  }
  onSubmit(){
    this.createSprint(this.prepareDataToSend())
    console.log(this.sprintForm.value.startTime)
  }

  createSprint(sprint: SprintRequest){
    this.sprintWebService.createSprint(sprint).subscribe({
      next: result =>{
        this.dialogRef.close()
      },
      error: err => {
        console.log(err)
      }
    })
  }

}
