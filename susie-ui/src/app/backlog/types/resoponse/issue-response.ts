import {UserInfoResponse} from "../../../auth/types/response/user-info-response";

export interface IssueResponse {
  id: number;
  name: string;
  assignee: UserInfoResponse;
  issueStatusID: number;
  issueTypeID: number;
  issuePriorityID: number;
  projectID: number;
  sprintID: number;
}
