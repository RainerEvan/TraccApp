import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { Members } from 'src/app/models/members';
import { MemberService } from 'src/app/services/member/member.service';
import { environment } from 'src/environments/environment';
import { ConfirmationDialogComponent } from '../../modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';
import { AddMemberComponent } from '../add-member/add-member.component';

@Component({
  selector: 'app-member-list',
  templateUrl: './member-list.component.html',
  styleUrls: ['./member-list.component.css']
})
export class MemberListComponent implements OnInit {

  members: Members[];
  loading: boolean;
  totalRecords: number;
  imageUrl = environment.apiUrl+'/accounts/profileImg/';
  ref: DynamicDialogRef;

  constructor(public dialogService:DialogService, private route:ActivatedRoute, private memberService: MemberService) { }

  ngOnInit() {
    this.getAllMembers();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAllMembers(): void{
    const teamId = this.route.snapshot.paramMap.get('id');

    this.loading = true;
    
    this.memberService.getAllMembersForTeam(teamId).subscribe({
      next: (members: Members[]) => {
        this.members = cloneDeep(members);
        this.loading = false;
        this.totalRecords = members.length;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  public deleteMember(memberId: string):void{
    this.memberService.deleteMember(memberId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.showResultDialog("Success","Member has been deleted successfully");
        this.getAllMembers();
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed","There was a problem, try again later");
      }
    });
  }

  showAddMemberDialog(){
    const teamId = this.route.snapshot.paramMap.get('id');

    this.ref = this.dialogService.open(AddMemberComponent, {
      header: "Add Member",
      footer: " ",
      data:{
        teamId: teamId,
        currMembersId: this.members.map(member => member.developer.id)
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"550px", "max-width":"700px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAllMembers();
      } 
    });
  }

  showConfirmationDialog(title:string, message:string, action:string, memberId:string){
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
            this.deleteMember(memberId);
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
