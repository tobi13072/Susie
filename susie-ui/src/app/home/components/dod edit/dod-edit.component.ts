import {Component, OnInit} from '@angular/core';
import {DynamicDialogConfig} from "primeng/dynamicdialog";
import {DodDto} from "../../types/dod-dto";
import {HomeService} from "../../services/home.service";

@Component({
  selector: 'app-dod-edit',
  templateUrl: './dod-edit.component.html',
  styleUrls: ['./dod-edit.component.scss']
})
export class DodEditComponent implements OnInit{

  dod: DodDto[] = [];
  dodToSend: string | undefined;
  isEdit: boolean = false;
  ruleIdToEdit: number | undefined;

  constructor(private homeWebService: HomeService, public dialogConfig: DynamicDialogConfig){}
  ngOnInit() {
    this.dod = this.dialogConfig.data.dod
    console.log(this.dod)
  }

  sendRule(){
    this.homeWebService.addRuleToDod(history.state.projectId, this.dodToSend!).subscribe({
      next: () =>{
        this.dodToSend = '';
        this.getDod();
      }
    })
  }

  getDod(){
    this.homeWebService.getDod(history.state.projectId).subscribe({
      next: res =>{
        this.dod = res;
        this.dod.sort((a, b) => a.ruleID - b.ruleID);
      }
    })
  }

  deleteSingleRule(ruleId: number){
    this.homeWebService.deleteSingleRule(history.state.projectId, ruleId).subscribe({
      next: () =>{
        this.getDod()
      }
    })
  }

  cancelEdit(){
    this.isEdit = false;
    this.dodToSend = '';
  }

  editSingleRule(ruleId: number){
    this.homeWebService.editSingleRule(ruleId, this.dodToSend!).subscribe({
      next: () =>{
        this.cancelEdit();
        this.getDod();
      }
    })
  }
}
