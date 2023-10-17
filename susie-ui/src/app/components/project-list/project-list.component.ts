import {Component, OnInit} from '@angular/core';
import {ProjectDto} from "../../types/project-dto";
import {ProjectWebService} from "../../service/project/project-web.service";

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.scss']
})
export class ProjectListComponent implements OnInit{

  projects: ProjectDto[] = [];

  ngOnInit(): void {
    this.getAllProjects()
  }
  constructor(private projectWebService: ProjectWebService) {
  }

  getAllProjects(){
    this.projectWebService.getProjects().subscribe({
      next: result =>{
        this.projects = result;
      },
      error: err => {
        console.log(err);
      }
    })
  }

  viewProjectDetails(project: ProjectDto){
    console.log(project);
  }

}
