import { Component, OnDestroy, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Accounts } from 'src/app/models/accounts';
import { AccountService } from 'src/app/services/account/account.service';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { environment } from 'src/environments/environment';
import { ConfirmationDialogComponent } from '../../modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';

@Component({
  selector: 'app-reassign-ticket',
  templateUrl: './reassign-ticket.component.html',
  styleUrls: ['./reassign-ticket.component.css']
})
export class ReassignTicketComponent implements OnInit, OnDestroy{

  developers: Accounts[];
  imageUrl = environment.apiUrl+'/accounts/profileImg/';
  loading: boolean;
  selectedDeveloper: Accounts;
  isReassignSubmitted: boolean = false;
  ticketInfo: any;
  ref2: DynamicDialogRef

  constructor(private dialogService: DialogService, public ref: DynamicDialogRef, public config: DynamicDialogConfig, private accountService: AccountService, private ticketSupportService:TicketSupportService) { }

  ngOnInit(): void {
    this.ticketInfo = this.config.data.ticketInfo;
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
        this.developers = cloneDeep(accounts).filter((developer) => developer.id != this.ticketInfo.currDeveloperId);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  reassignTicket(){
    if(this.selectedDeveloper){
      const reassignInfo = {
        ticketId: this.ticketInfo.ticketId,
        currSupportId: this.ticketInfo.currSupportId,
        developerId: this.selectedDeveloper.id
      }
  
      this.ticketSupportService.reassignTicket(reassignInfo).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isReassignSubmitted = true;
          this.ref.close(this.isReassignSubmitted);
          this.showResultDialog("Success","Ticket has been reassigned successfully");
        },
        error: (error: any) => {
          console.log(error);
          this.isReassignSubmitted = false;
          this.ref.close(this.isReassignSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    }
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
          this.reassignTicket();
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
