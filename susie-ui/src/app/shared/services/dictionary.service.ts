import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {IssueTypeDto} from "../types/issue-type-dto";
import {env} from "../../../environments/environment";
import {IssuePriorityDto} from "../types/issue-priority-dto";

@Injectable({
  providedIn: 'root'
})
export class DictionaryService {
  private readonly BASE_PATH: string = env.apiUrl.concat("/dictionary");
  constructor(private http: HttpClient) { }

  getIssueTypes(): Observable<IssueTypeDto[]>{
    return this.http.get<IssueTypeDto[]>(this.BASE_PATH.concat('/type'))
  }

  getIssuePriorities():Observable<IssuePriorityDto[]>{
    return this.http.get<IssuePriorityDto[]>(this.BASE_PATH.concat('/priority'))
  }
}
