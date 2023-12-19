export interface IssueRequest{
  name: string;
  description: string;
  estimation: number;
  projectID: number;
  issueTypeID: number;
  issuePriorityID: number;
}
