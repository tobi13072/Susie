export interface IssueRequest{
  issueID?: number
  name: string;
  description: string;
  estimation: number;
  projectID: number;
  issueTypeID: number;
  issuePriorityID: number;
}
