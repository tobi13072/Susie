import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {env} from "../../../environments/environment";
import {Observable} from "rxjs";
import {SprintDto} from "../types/sprint-dto";


@Injectable({
  providedIn: 'root'
})
export class SprintService {
  private readonly SPRINT_PATH: string = env.apiUrl.concat("/sprint");
  constructor(private http: HttpClient) { }


  getActiveSprints(projectId: number): Observable<SprintDto[]>{
    return this.http.get<SprintDto[]>(this.SPRINT_PATH.concat(`/active/${projectId}`))
  }
  getNonActiveSprints(projectId: number){
    return this.http.get<SprintDto[]>(this.SPRINT_PATH.concat(`/non-activated/${projectId}`))
  }
  createSprint(sprint: SprintDto):Observable<SprintDto>{
    return this.http.post<SprintDto>(this.SPRINT_PATH,sprint);
  }

  editSprint(sprint: SprintDto):Observable<SprintDto>{
    return this.http.put<SprintDto>(this.SPRINT_PATH,sprint);
  }

  deleteSprint(sprintId: number):Observable<any>{
    return this.http.delete(this.SPRINT_PATH.concat(`/${sprintId}`))
  }
}
