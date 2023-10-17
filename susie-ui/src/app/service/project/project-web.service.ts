import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {env} from "../../../environments/environment";
import {Observable} from "rxjs";
import {ProjectDto} from "../../types/project-dto";

@Injectable({
  providedIn: 'root'
})
export class ProjectWebService {

  private readonly PROJECT_PATH: string = env.apiUrl.concat("/scrum-project");

  constructor(private http: HttpClient) {
  }

  createProject(project: ProjectDto): Observable<ProjectDto> {
    return this.http.post<ProjectDto>(this.PROJECT_PATH, project);
  }

  getProjects(): Observable<ProjectDto[]> {
    return this.http.get<ProjectDto[]>(this.PROJECT_PATH);
  }

  updateProject(project: ProjectDto): Observable<ProjectDto> {
    return this.http.put<ProjectDto>(this.PROJECT_PATH, project);
  }

}
