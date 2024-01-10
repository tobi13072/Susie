import {Component, OnInit} from '@angular/core';
import {UserInfoResponse} from "../../../auth/types/response/user-info-response";
import {MembersService} from "../../services/members.service";
import {ProjectMembersDto} from "../../types/projectMembers-dto";
import {FormBuilder, Validators} from "@angular/forms";
import {ConfirmationService, MenuItem, PrimeIcons} from "primeng/api";
import {errorDialog} from "../../../shared/error.dialog";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  providers: [ConfirmationService]
})
export class DashboardComponent implements OnInit{

  members: UserInfoResponse[] = [];
  owner: UserInfoResponse | undefined;
  menuUser: MenuItem[] | undefined;
  activeUserId: string | undefined;

  constructor(private memberWebService: MembersService, private fb: FormBuilder, private confirmDialog: ConfirmationService){}
  ngOnInit() {
    this.getMembers();
  }

  generateUserMenu(){

    let deleteOption = {
      label: 'Delete',
      icon: PrimeIcons.TRASH,
      command: () => {
        this.deleteUserFromProject(this.activeUserId!)
      }
    }

    let assignSmRole = {
      label: 'Assign the role \'Scrum Master\'',
      icon: PrimeIcons.USER_EDIT,
      command: () => {
      }
    }

    let revokeSmRole = {
      label: 'Revoke the role \'Scrum Master\'',
      icon: PrimeIcons.USER_EDIT,
      command: () => {
      }
    }

    this.menuUser = [
      assignSmRole,
      revokeSmRole,
      deleteOption
    ]
  }

  addUserLabel = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
  })

  getMembers(){
    this.memberWebService.getMembers(history.state.projectId).subscribe({
      next: res =>{
        this.owner = res.owner;
        this.members = res.members;
        this.members = this.members.filter(member => member.email !== res.owner.email)
      }
    })
  }

  prepareDataToAdd(): ProjectMembersDto{
    return {
      email: this.addUserLabel.value.email!,
      projectID: history.state.projectId
    }
  }

  prepareDataToDelete(userID: string): ProjectMembersDto{
    let projectID: number = history.state.projectId
    return {
      userUUID: userID,
      projectID: projectID
    }
  }
  addUserToProject(){
   this.memberWebService.addUserToProject(this.prepareDataToAdd()).subscribe({
     next: () =>{
       this.getMembers()
       this.addUserLabel.patchValue({
         email: ''
       })
     },
     error: () =>{
       this.confirmDialog.confirm(errorDialog('User with given email does not exist'));
     }
   })
  }

  deleteUserFromProject(userID: string){
    console.log(this.prepareDataToDelete(userID))
    this.memberWebService.deleteUserFromProject(this.prepareDataToDelete(userID)).subscribe({
      next: () =>{
        this.getMembers()
      },
      error: err => {
        console.log(err)
      }
    })
  }
}
