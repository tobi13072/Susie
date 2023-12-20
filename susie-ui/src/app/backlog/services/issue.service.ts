import { Injectable } from '@angular/core';
import {env} from "../../../environments/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {IssueRequest} from "../types/request/issue-request";
import {Observable} from "rxjs";
import {IssueResponse} from "../types/resoponse/issue-response";

@Injectable({
  providedIn: 'root'
})
export class IssueService {
  private readonly ISSUE_PATH: string = env.apiUrl.concat("/issue");
  constructor(private http: HttpClient) { }

  createIssue(issue: IssueRequest): Observable<IssueRequest>{
    return this.http.post<IssueRequest>(this.ISSUE_PATH,issue);
  }
  getIssuesFromProductBacklog(projectId: number): Observable<IssueResponse[]>{
    const params = new HttpParams().set('projectID', projectId)
    return this.http.get<IssueResponse[]>(this.ISSUE_PATH.concat('/product-backlog'), {params});
  }

  updateIssue(issue: IssueRequest): Observable<IssueRequest>{
    return this.http.put<IssueRequest>(this.ISSUE_PATH,issue);
  }

  deleteIssue(issueId: number): Observable<any>{
    return this.http.delete<any>(this.ISSUE_PATH.concat(`/${issueId}`))
  }
}
