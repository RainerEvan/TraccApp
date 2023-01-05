import { Component, OnInit } from '@angular/core';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Accounts } from 'src/app/models/accounts';
import { ConfirmationDialogComponent } from 'src/app/modules/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { AccountService } from 'src/app/services/account/account.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-reassign-ticket',
  templateUrl: './reassign-ticket.component.html',
  styleUrls: ['./reassign-ticket.component.css']
})
export class ReassignTicketComponent implements OnInit {

  developers: Accounts[];
  imageUrl = environment.apiUrl+'/accounts/profileImg/';
  loading: boolean;
  selectedDeveloper: Accounts;
  isReassignSubmitted: boolean = false;
  ticketInfo: any;
  ref2: DynamicDialogRef

  constructor(private dialogService: DialogService, public ref: DynamicDialogRef, public config: DynamicDialogConfig, private accountService: AccountService, private ticketSupportService:TicketSupportService, private notificationService:NotificationService) { }

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
        this.developers = accounts.filter((developer) => developer.id != this.ticketInfo.currDeveloperId);
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

      const data = {
        ticketNo: this.ticketInfo.ticketNo,
        ticketId: this.ticketInfo.ticketId
      }
  
      this.ticketSupportService.reassignTicket(reassignInfo).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isReassignSubmitted = true;
          this.ref.close(this.isReassignSubmitted);
          this.showResultDialog("Success","Ticket has been reassigned successfully");
          this.sendNotification(reassignInfo.developerId,"Ticket Assigned By Supervisor","You have been assigned to support a new ticket, check it out",data);
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
  
  sendNotification(accountId:string, title:string, body:string, data:any){
    const notification = {
      receiverId: accountId,
      title: title,
      body: body,
      data: JSON.stringify(data)
    }

    this.notificationService.addNotification(notification).subscribe({
      next: (result: any) => {
        console.log(result);
      },
      error: (error: any) => {
        console.log(error);
      }
    })
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
