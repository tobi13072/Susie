import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {SprintService} from "../../services/sprint.service";
import {SprintDto} from "../../types/sprint-dto";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {dateConvertFromUTC, dateConvertToUTC} from "../../../shared/dateConvertToUTC";
import {ConfirmationService} from "primeng/api";
import {errorDialog} from "../../../shared/error.dialog";

@Component({
  selector: 'app-sprint-form',
  templateUrl: './sprint-form.component.html',
  styleUrls: ['./sprint-form.component.scss'],
  providers: [ConfirmationService]
})
export class SprintFormComponent implements OnInit{

  selectedDate: Date | undefined;

  ngOnInit() {
    if(this.dialogConfig.data.isEdit){
      let editedSprint = this.dialogConfig.data.sprint!
      dateConvertFromUTC(editedSprint.startTime.toString())
      this.sprintForm.get('name')?.setValue(editedSprint.name)
      this.sprintForm.get('sprintGoal')?.setValue(editedSprint.sprintGoal)
      this.selectedDate = new Date(dateConvertFromUTC(editedSprint.startTime))
    }
  }

  constructor(private fb:FormBuilder, private sprintWebService: SprintService, public dialogRef: DynamicDialogRef,
              public dialogConfig: DynamicDialogConfig, private confirmDialog: ConfirmationService){}

  sprintForm = this.fb.group({
    name: ['', Validators.required],
    sprintGoal: ['', Validators.required],
    startTime: ['']
  })


  prepareDataToSend():SprintDto{
    return {
      id:this.dialogConfig.data.isEdit ? this.dialogConfig.data.sprint.id: null,
      name: this.sprintForm.value.name!,
      startTime: dateConvertToUTC(this.sprintForm.value.startTime!),
      active: this.dialogConfig.data.isEdit ?this.dialogConfig.data.sprint.active : false,
      sprintGoal: this.sprintForm.value.sprintGoal!,
      projectID:  this.dialogConfig.data.isEdit ? this.dialogConfig.data.sprint.projectID: this.dialogConfig.data.projectId
    }

  }
  onSubmit(){
    if(this.dialogConfig.data.isEdit) {
      this.editSprint(this.prepareDataToSend())
    }else {
      this.createSprint(this.prepareDataToSend())
    }
  }

  editSprint(sprint: SprintDto){
    console.log(sprint)
    this.sprintWebService.editSprint(sprint).subscribe({
      next: () =>{
        this.dialogRef.close()
      },
      error: err =>{
        console.log(err)
      }
      }
    )
  }

  createSprint(sprint: SprintDto){
    this.sprintWebService.createSprint(sprint).subscribe({
      next: result =>{
        this.dialogRef.close()
      },
      error: err => {
        this.confirmDialog.confirm(errorDialog(err.error.message))
      }
    })
  }

}
