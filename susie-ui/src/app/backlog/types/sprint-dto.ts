import {IssueResponse} from "./resoponse/issue-response";

export interface SprintDto {
  id?: number,
  name: string,
  startTime: string,
  active: boolean,
  sprintGoal: string,
  projectID: number,
  issues?: IssueResponse[]
}
