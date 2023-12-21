import {Component, OnInit} from '@angular/core';
import {DynamicDialogConfig} from "primeng/dynamicdialog";
import {IssueService} from "../../services/issue.service";
import {IssueDetailsResponse} from "../../types/resoponse/issueDetails-response";
import {nameTypeById} from "../../../shared/tag.type.operations";
import {colorPriorityTagById, namePriorityById} from "../../../shared/tag.priority.operations";
import {nameStatusById} from "../../../shared/tag.status.operations";
import {CommentsService} from "../../services/comments.service";
import {CommentDto} from "../../types/comment-dto";
import {getInitials} from "../../../shared/initials.generator";


@Component({
  selector: 'app-issue-details',
  templateUrl: './issue-details.component.html',
  styleUrls: ['./issue-details.component.scss']
})
export class IssueDetailsComponent implements OnInit {

  issueDetails: IssueDetailsResponse | undefined;
  commentToSend: string | undefined;

  ngOnInit() {
    this.getIssueDetails()
  }

  constructor(private issueWebService: IssueService, private commentWebService: CommentsService, public dialogConfig: DynamicDialogConfig) {
  }

  getIssueDetails() {
    this.issueWebService.getIssueDetails(this.dialogConfig.data.issueId).subscribe({
      next: res => {
        this.issueDetails = res;
      },
      error: err => {
        console.log(err)
      }
    })
  }

  prepareDataToSend(): CommentDto {
    return {
      issueID: this.dialogConfig.data.issueId,
      body: this.commentToSend!
    }
  }

  sendComment() {
    this.commentWebService.sendComment(this.prepareDataToSend()).subscribe({
      next: res => {
        console.log(res)
        this.getIssueDetails()
        this.commentToSend = ''
      },
      error: err => {
        console.log(err)
      }
    })
  }

  protected readonly nameTypeById = nameTypeById;
  protected readonly colorPriorityTagById = colorPriorityTagById;
  protected readonly namePriorityById = namePriorityById;
  protected readonly nameStatusById = nameStatusById;
  protected readonly getInitials = getInitials;
}
