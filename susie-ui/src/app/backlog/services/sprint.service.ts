import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {env} from "../../../environments/environment";
import {Observable} from "rxjs";
import {SprintDto} from "../types/sprint-dto";


@Injectable({
  providedIn: 'root'
})
export class SprintService {
  private readonly SPRINT_PATH: string = env.apiUrl.concat("/sprint");

  constructor(private http: HttpClient) {
  }


  getActiveSprint(projectId: number): Observable<SprintDto> {
    return this.http.get<SprintDto>(this.SPRINT_PATH.concat(`/active/${projectId}`))
  }

  getNonActiveSprints(projectId: number) {
    return this.http.get<SprintDto[]>(this.SPRINT_PATH.concat(`/non-activated/${projectId}`))
  }

  createSprint(sprint: SprintDto): Observable<SprintDto> {
    return this.http.post<SprintDto>(this.SPRINT_PATH, sprint);
  }

  editSprint(sprint: SprintDto): Observable<SprintDto> {
    return this.http.put<SprintDto>(this.SPRINT_PATH, sprint);
  }

  deleteSprint(sprintId: number): Observable<any> {
    return this.http.delete(this.SPRINT_PATH.concat(`/${sprintId}`))
  }

  startSprint(sprintId: number): Observable<any> {
    return this.http.patch(this.SPRINT_PATH.concat(`/start/${sprintId}`), {})
  }

  stopSprint(projectId: number): Observable<any> {
    return this.http.patch(this.SPRINT_PATH.concat(`/project/${projectId}/stop`), {})
  }

  addIssueToSprint(issueId: number, sprintId: number): Observable<any> {
    return this.http.post(this.SPRINT_PATH.concat(`/${sprintId}/issue/${issueId}`),{})
  }

  deleteIssueFromSprint(issueId: number, sprintId: number): Observable<any>{
    return this.http.delete(this.SPRINT_PATH.concat(`/${sprintId}/issue/delete/${issueId}`),{})
  }
}
