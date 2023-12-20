import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {env} from "../../../environments/environment";
import {Observable} from "rxjs";
import {SprintRequest} from "../types/request/sprint-request";


@Injectable({
  providedIn: 'root'
})
export class SprintService {
  private readonly SPRINT_PATH: string = env.apiUrl.concat("/sprint");
  constructor(private http: HttpClient) { }

  createSprint(sprint: SprintRequest):Observable<SprintRequest>{
    return this.http.post<SprintRequest>(this.SPRINT_PATH,sprint);
  }

  editSprint(sprint: SprintRequest):Observable<SprintRequest>{
    return this.http.put<SprintRequest>(this.SPRINT_PATH,sprint);
  }
}
