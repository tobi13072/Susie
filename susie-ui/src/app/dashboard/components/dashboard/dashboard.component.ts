import {Component, OnInit} from '@angular/core';
import {UserInfoResponse} from "../../../auth/types/response/user-info-response";
import {MembersService} from "../../services/members.service";
import {ProjectMembersDto} from "../../types/projectMembers-dto";
import {FormBuilder, Validators} from "@angular/forms";
import {ConfirmationService, MenuItem, PrimeIcons} from "primeng/api";
import {errorDialog} from "../../../shared/error.dialog";
import {env} from "../../../../environments/environment";
import {AuthService} from "../../../auth/services/auth.service";
import {getInitials} from "../../../shared/initials.generator";

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

  constructor(private memberWebService: MembersService, private fb: FormBuilder, private confirmDialog: ConfirmationService, protected authService: AuthService){}
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
      label: 'Assign the role \'Product Owner\'',
      icon: PrimeIcons.USER_EDIT,
      command: () => {
        this.assigneeUserPoRole(this.activeUserId!)
      }
    }

    let revokeSmRole = {
      label: 'Revoke the role \'Product Owner\'',
      icon: PrimeIcons.USER_EDIT,
      command: () => {
        this.revokeUserPoRole(this.activeUserId!)
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

  prepareDataToChangePoRole(userID: string): ProjectMembersDto{
    let projectID: number = history.state.projectId
    return {
      userUUID: userID,
      projectID: projectID,
      roleName: env.projectRoles.product_owner
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
    this.memberWebService.deleteUserFromProject(this.prepareDataToDelete(userID)).subscribe({
      next: () =>{
        this.getMembers()
      },
      error: err => {
        console.log(err)
      }
    })
  }

  assigneeUserPoRole(userID: string){
    this.memberWebService.assigneeUserRole(this.prepareDataToChangePoRole(userID)).subscribe({
      error: err =>{
        this.confirmDialog.confirm(errorDialog(err.error.message))
      }
    })
  }

  revokeUserPoRole(userID: string){
    this.memberWebService.revokeUserRole(this.prepareDataToChangePoRole(userID)).subscribe({
      error: err =>{
        this.confirmDialog.confirm(errorDialog(err.error.message))
      }
    })
  }

  protected readonly getInitials = getInitials;
}
