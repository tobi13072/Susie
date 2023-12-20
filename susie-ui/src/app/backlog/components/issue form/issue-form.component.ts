import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {IssueService} from "../../services/issue.service";
import {DictionaryService} from "../../../shared/services/dictionary.service";
import {IssueTypeDto} from "../../../shared/types/issue-type-dto";
import {IssuePriorityDto} from "../../../shared/types/issue-priority-dto";
import {IssueRequest} from "../../types/request/issue-request";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";

@Component({
  selector: 'app-issue-form',
  templateUrl: './issue-form.component.html',
  styleUrls: ['./issue-form.component.scss']
})
export class IssueFormComponent implements OnInit{

  issueTypes: IssueTypeDto[] = []
  issuePriorities: IssuePriorityDto[] = []
  selectedType: IssueTypeDto | undefined;
  selectedPriority: IssuePriorityDto | undefined;
  ngOnInit() {
    this.getAllTypes()
    this.getAllPriorities()
    console.log(this.dialogConfig.data.projectId)
  }
  constructor(private fb:FormBuilder, private issueWebService: IssueService, private dictionaryService: DictionaryService,public dialogRef: DynamicDialogRef,
              public dialogConfig: DynamicDialogConfig){
  }

  issueForm = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    estimation: [''],
    issueTypeID: ['', Validators.required],
    issuePriorityID: ['',Validators.required]
  })

  getAllTypes() {
    this.dictionaryService.getIssueTypes().subscribe({
      next: result => {
        this.issueTypes = result;
        this.issueTypes = this.issueTypes.map((element) => ({
          id: element.id,
          type: element.type.replace(/_/g, ' ')
        }));
      },
      error: err => {
        console.log(err)
      }
    })
  }
    getAllPriorities(){
      this.dictionaryService.getIssuePriorities().subscribe({
        next: result =>{
          this.issuePriorities = result;
        },
        error: err =>{
          console.log(err)
        }
      })
  }

  prepareDataToSend(): IssueRequest{
    return {
      name: this.issueForm.value.name!,
      description: this.issueForm.value.description!,
      estimation: parseInt(this.issueForm.value.estimation!),
      projectID: this.dialogConfig.data.projectId,
      issueTypeID: this.selectedType!.id,
      issuePriorityID: this.selectedPriority!.id
    }

  }
  onSubmit(){
    this.createIssue(this.prepareDataToSend())
  }

  createIssue(issue: IssueRequest){
    this.issueWebService.createIssue(issue).subscribe({
      next: () => {
        this.dialogRef.close()
      },
      error: err =>{
        console.log(err)
      }
    })
  }
}
