import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Accounts } from 'src/app/models/accounts';
import { ConfirmationDialogComponent } from 'src/app/modules/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { AccountService } from 'src/app/services/account/account.service';
import { MemberService } from 'src/app/services/member/member.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-add-member',
  templateUrl: './add-member.component.html',
  styleUrls: ['./add-member.component.css']
})
export class AddMemberComponent implements OnInit {

  developers: Accounts[];
  currMembersId: string[];
  imageUrl = environment.apiUrl+'/accounts/profileImg/';
  loading: boolean;
  selectedDevelopers: Accounts[] = [];
  isReassignSubmitted: boolean = false;
  ref2: DynamicDialogRef

  constructor(private dialogService: DialogService, public ref: DynamicDialogRef, public config: DynamicDialogConfig, private accountService: AccountService, private memberService:MemberService) { }

  ngOnInit(): void {
    this.currMembersId = this.config.data.currMembersId;
    this.getAllDevelopers();
  }

  ngOnDestroy(): void {
    if(this.ref2){
      this.ref2.close();
    }
  }

  public getAllDevelopers(): void{
    this.loading = true;

    this.accountService.getAllDevelopers().subscribe({
      next: (accounts: Accounts[]) => {
        this.developers = cloneDeep(accounts).filter((developer) => !this.currMembersId.includes(developer.id));
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  addMember(developerId:string){
    const formData = {
      teamId: this.config.data.teamId,
      developerId: developerId
    }

    this.memberService.addMember(formData).subscribe({
      next: (result: any) => {
        console.log(result);
        this.isReassignSubmitted = true;
        this.ref.close(this.isReassignSubmitted);
        this.showResultDialog("Success","Member has been added successfully");
      },
      error: (error: any) => {
        console.log(error);
        this.isReassignSubmitted = false;
        this.ref.close(this.isReassignSubmitted);
        this.showResultDialog("Failed","There was a problem, try again later");
      }
    });
  }
  
  showConfirmationDialog(title:string, message:string){
    this.ref2 = this.dialogService.open(ConfirmationDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });

    this.ref2.onClose.subscribe((confirm:boolean) =>{
        if (confirm) {
          this.selectedDevelopers.forEach((developer) => {
            this.addMember(developer.id);
          });
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
