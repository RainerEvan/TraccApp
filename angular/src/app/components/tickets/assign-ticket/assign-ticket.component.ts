import { Component, OnDestroy, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Accounts } from 'src/app/models/accounts';
import { AccountService } from 'src/app/services/account/account.service';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { environment } from 'src/environments/environment';
import { ConfirmationDialogComponent } from '../../modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-assign-ticket',
  templateUrl: './assign-ticket.component.html',
  styleUrls: ['./assign-ticket.component.css']
})
export class AssignTicketComponent implements OnInit {

  developers: Accounts[];
  imageUrl = environment.apiUrl+'/accounts/profileImg/';
  loading: boolean;
  selectedDeveloper: Accounts;
  isAssignSubmitted: boolean = false;
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
        this.developers = cloneDeep(accounts);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  assignTicket(){
    if(this.selectedDeveloper){
      const assignInfo = {
        ticketId: this.ticketInfo.ticketId,
        accountId: this.selectedDeveloper.id
      }

      const data = {
        ticketNo: this.ticketInfo.ticketNo,
        ticketId: this.ticketInfo.ticketId
      }
  
      this.ticketSupportService.addSupport(assignInfo).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isAssignSubmitted = true;
          this.ref.close(this.isAssignSubmitted);
          this.showResultDialog("Success","Ticket has been assigned successfully");
          this.sendNotification(this.selectedDeveloper.id,"Ticket Assigned By Supervisor","You have been assigned to support a new ticket, check it out",data);
        },
        error: (error: any) => {
          console.log(error);
          this.isAssignSubmitted = false;
          this.ref.close(this.isAssignSubmitted);
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
          this.assignTicket();
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
