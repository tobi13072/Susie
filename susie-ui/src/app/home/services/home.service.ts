import { Injectable } from '@angular/core';
import {env} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {IssueResponse} from "../../backlog/types/resoponse/issue-response";
import {DodDto} from "../types/dod-dto";

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private readonly ISSUE_ASSIGNED_PATH: string = env.apiUrl.concat("/issue/user-assigned");
  private readonly DOD_PATH: string = env.apiUrl.concat("/commitment/dod");
  constructor(private http: HttpClient) { }

  getIssuesAssignedToUser(): Observable<IssueResponse[]>{
    return this.http.get<IssueResponse[]>(this.ISSUE_ASSIGNED_PATH);
  }

  getDod(projectId: number):Observable<DodDto[]>{
    return this.http.get<DodDto[]>(this.DOD_PATH.concat(`/project/${projectId}`))
  }

  addRuleToDod(projectId: number, rule: string): Observable<DodDto>{
    const ruleData = new FormData;
    ruleData.append('rule', rule);
    return this.http.post<DodDto>(this.DOD_PATH.concat(`/project/${projectId}/rule`), ruleData)
  }

  deleteSingleRule(projectId: number, ruleId: number):Observable<any>{
    return this.http.delete<any>(this.DOD_PATH.concat(`/project/${projectId}/rule/${ruleId}`))
  }
}
