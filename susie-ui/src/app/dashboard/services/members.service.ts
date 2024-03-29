import { Injectable } from '@angular/core';
import {env} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MembersDetailsDto} from "../types/membersDetails-dto";
import {ProjectMembersDto} from "../types/projectMembers-dto";

@Injectable({
  providedIn: 'root'
})
export class MembersService {
  private readonly PATH: string = env.apiUrl.concat("/scrum-project");

  constructor(private http: HttpClient) {}

  getMembers(projectId: number):Observable<MembersDetailsDto>{
    return this.http.get<MembersDetailsDto>(this.PATH.concat(`/${projectId}`));
  }

  addUserToProject(data: Object): Observable<ProjectMembersDto>{
    return this.http.post<ProjectMembersDto>(this.PATH.concat('/user-association'), data);
  }

  deleteUserFromProject(data: Object): Observable<any>{
    return this.http.delete<any>(this.PATH.concat('/delete-user'),{body: data});
  }

  assigneeUserRole(data: Object): Observable<any>{
    return this.http.patch<any>('http://localhost:8081/api/auth/user/permission', data);
  }
  revokeUserRole(data: Object): Observable<any>{
    return this.http.patch<any>('http://localhost:8081/api/auth/user/permission-revoke', data);
  }

}
