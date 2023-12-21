import {UserInfoResponse} from "../../../auth/types/response/user-info-response";
import {CommentDto} from "../comment-dto";

export interface IssueDetailsResponse{
  issueID: number,
  name: string,
  description: string,
  estimation: number,
  reporter: UserInfoResponse,
  assignee: UserInfoResponse
  issueTypeID: number,
  issuePriorityID: number,
  issueStatusID: number,
  projectID: number,
  sprintID: number,
  comments: CommentDto[]
}
