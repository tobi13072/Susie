import {Component, OnInit} from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
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
  constructor(private homeWebService: HomeService, public dialogRef: DynamicDialogRef, public dialogConfig: DynamicDialogConfig){}
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
      }
    })
  }

  deleteDod(ruleId: number){
    this.homeWebService.deleteSingleRule(history.state.projectId, ruleId).subscribe({
      next: () =>{
        this.getDod()
      }
    })
  }
}
