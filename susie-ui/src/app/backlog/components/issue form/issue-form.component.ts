import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {IssueService} from "../../services/issue.service";
import {DictionaryService} from "../../../shared/services/dictionary.service";
import {IssueTypeDto} from "../../../shared/types/issue-type-dto";
import {IssuePriorityDto} from "../../../shared/types/issue-priority-dto";
import {IssueRequest} from "../../types/request/issue-request";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {IssueDetailsResponse} from "../../types/resoponse/issueDetails-response";

@Component({
  selector: 'app-issue-form',
  templateUrl: './issue-form.component.html',
  styleUrls: ['./issue-form.component.scss']
})
export class IssueFormComponent implements OnInit {

  issueTypes: IssueTypeDto[] = []
  issuePriorities: IssuePriorityDto[] = []
  selectedType: IssueTypeDto | undefined;
  selectedPriority: IssuePriorityDto | undefined;

  issueDetails: IssueDetailsResponse | undefined;

  ngOnInit() {
    this.getAllTypes()
    this.getAllPriorities()

    if (this.dialogConfig.data.isEdit) {
      this.getIssueDetails(this.dialogConfig.data.issueId)
    }

  }

  constructor(private fb: FormBuilder, private issueWebService: IssueService, private dictionaryService: DictionaryService, public dialogRef: DynamicDialogRef,
              public dialogConfig: DynamicDialogConfig) {
  }

  issueForm = this.fb.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    estimation: [''],
    issueTypeID: ['', Validators.required],
    issuePriorityID: ['', Validators.required]
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

  getAllPriorities() {
    this.dictionaryService.getIssuePriorities().subscribe({
      next: result => {
        this.issuePriorities = result;
      },
      error: err => {
        console.log(err)
      }
    })
  }

  getIssueDetails(id: number) {
    this.issueWebService.getIssueDetails(id).subscribe({
      next: res => {
        this.issueDetails = res;
        this.issueForm.get('name')?.setValue(res.name);
        this.issueForm.get('description')?.setValue(res.description);
        this.issueForm.get('estimation')?.setValue(res.estimation.toString())
        this.selectedType = this.issueTypes.find(type => type.id === res.issueTypeID)!;
        this.selectedPriority = this.issuePriorities.find(priority => priority.id === res.issuePriorityID);
      },
      error: err => {
        console.log(err)
      }
    })
  }

  prepareDataToSend(): IssueRequest {
    return {
      issueID: this.dialogConfig.data.isEdit ? this.issueDetails!.issueID : null!,
      name: this.issueForm.value.name!,
      description: this.issueForm.value.description!,
      estimation: parseInt(this.issueForm.value.estimation!),
      projectID: this.dialogConfig.data.isEdit ? this.issueDetails!.projectID : this.dialogConfig.data.projectId,
      issueTypeID: this.selectedType!.id,
      issuePriorityID: this.selectedPriority!.id
    }

  }

  onSubmit() {
    if (this.dialogConfig.data.isEdit) {
      this.editIssue(this.prepareDataToSend())
    } else {
      this.createIssue(this.prepareDataToSend())
    }
  }

  createIssue(issue: IssueRequest) {
    this.issueWebService.createIssue(issue).subscribe({
      next: () => {
        this.dialogRef.close()
      },
      error: err => {
        console.log(err)
      }
    })
  }

  editIssue(issue: IssueRequest){
    this.issueWebService.updateIssue(issue).subscribe({
      next: result=>{
        this.dialogRef.close()
      },
      error: err =>{
        console.log(err)
      }
    })
  }
}
