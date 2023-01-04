import { Component, OnInit, ViewChild } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { Teams } from 'src/app/models/teams';
import { AuthService } from 'src/app/services/auth/auth.service';
import { TeamService } from 'src/app/services/team/team.service';
import { ConfirmationDialogComponent } from '../../modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';
import { AddTeamComponent } from '../add-team/add-team.component';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.css']
})
export class TeamListComponent implements OnInit {

  accountId: string;
  teams: Teams[];
  loading: boolean;
  totalRecords: number;
  ref: DynamicDialogRef;
  @ViewChild('teamTable') teamTable: Table | undefined;

  constructor(public dialogService:DialogService, private teamService: TeamService, private authService: AuthService) { }

  ngOnInit() {
    this.accountId = this.authService.accountValue.accountId;
    this.getAllTeams();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAllTeams(): void{
    this.loading = true;
    this.teamService.getAllTeamsForSupervisor(this.accountId).subscribe({
      next: (teams: Teams[]) => {
        this.teams = cloneDeep(teams);
        this.loading = false;
        this.totalRecords = teams.length;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.teamTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  public deleteTeam(teamId: string):void{
    this.teamService.deleteTeam(teamId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.showResultDialog("Success","Team has been deleted successfully");
        this.getAllTeams();
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed","There was a problem, try again later");
      }
    });
  }

  showAddTeamDialog(){
    this.ref = this.dialogService.open(AddTeamComponent, {
      header: "Add Team",
      footer: " ",
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"550px", "max-width":"700px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAllTeams();
      } 
    });
  }

  showConfirmationDialog(title:string, message:string, action:string, teamId:string){
    this.ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((confirm:boolean) =>{
        if (confirm) {
          if(action == 'delete'){
            this.deleteTeam(teamId);
          }
        }
    });
  }

  showResultDialog(title:string, message:string){
    this.ref = this.dialogService.open(ResultDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });
  }

}
