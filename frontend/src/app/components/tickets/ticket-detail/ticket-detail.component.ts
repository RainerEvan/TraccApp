import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { Tickets } from 'src/app/models/tickets';
import { ConfirmationDialogComponent } from '../../modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';
import { SolveTicketComponent } from '../solve-ticket/solve-ticket.component';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ImageDialogComponent } from '../../modal/image-dialog/image-dialog.component';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-ticket-detail',
  templateUrl: './ticket-detail.component.html',
  styleUrls: ['./ticket-detail.component.css']
})
export class TicketDetailComponent implements OnInit, OnDestroy {

  loading: boolean;
  ticket: Tickets;
  isCurrDeveloper: boolean;
  isCurrUser: boolean;
  ref: DynamicDialogRef;

  constructor(private router: Router, public dialogService:DialogService, private route:ActivatedRoute, private ticketSupportService:TicketSupportService, private notificationService:NotificationService, private authService: AuthService) { }

  ngOnInit(): void {
    this.getTicket();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getTicket():void{
    const ticketId = this.route.snapshot.paramMap.get('id');

    this.loading = true;
    this.ticketSupportService.getTicket(ticketId).subscribe({
      next: (ticket: Tickets) => {
        this.loading = false;
        this.ticket = ticket;
        this.checkDeveloper();
        this.checkUser();
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }
  
  public takeTicket():void{
    const ticketId = this.ticket.ticketId;

    this.ticketSupportService.addSupport(ticketId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.showResultDialog("Success","Ticket has been added to your task list",null);
        this.sendNotification(this.ticket.reporter.id,"Ticket Taken By Developer","Your ticket has been taken by a developer, check it out");
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed","There was a problem, try again later",null);
      }
    });
  }

  public closeTicket():void{
    const ticketId = this.ticket.ticketId;

    this.ticketSupportService.closeTicket(ticketId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.showResultDialog("Success","Ticket has been closed successfully",null);
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed","There was a problem, try again later",null);
      }
    });
  }

  public cancelTicket():void{
    const ticketId = this.ticket.ticketId;

    this.ticketSupportService.cancelTicket(ticketId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.showResultDialog("Success","Ticket has been canceled successfully","cancel");
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed",error,null);
      }
    });
  }

  sendNotification(accountId:string, title:string, body:string){
    const notification = {
      receiverId: accountId,
      title: title,
      body: body
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

  checkDeveloper(){
    this.isCurrDeveloper = false;

    const accountId = this.authService.accountValue.accountId;
    const developerId = this.ticket.support[0]?.developer.id;

    if(accountId == developerId){
      this.isCurrDeveloper = true;
    }
  }

  checkUser(){
    this.isCurrUser = false;

    const accountId = this.authService.accountValue.accountId;
    const userId = this.ticket.reporter.id;

    if(accountId == userId){
      this.isCurrUser = true;
    }
  }

  showSolveTicketDialog(){
    const supportId = this.ticket.support[0].id;

    this.ref = this.dialogService.open(SolveTicketComponent, {
      header: "Solve Ticket",
      footer: " ",
      data: {
        supportId: supportId,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px", "overflow": "auto"},
      width:'40vw',
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getTicket();
        this.sendNotification(this.ticket.reporter.id,"Ticket Solved By Developer","Your ticket has been solved by a developer, check it out");
      } 
    });
  }

  showConfirmationDialog(title:string, message:string, action:string){
    this.ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "500px", "overflow": "auto"},
      width:'30vw',
    });

    this.ref.onClose.subscribe((confirm:boolean) =>{
        if (confirm) {
          if(action == 'take'){
            this.takeTicket();
          }
          else if(action == 'close'){
            this.closeTicket();
          }
          else if(action == 'cancel'){
            this.cancelTicket();
          }
        }
    });
  }

  showResultDialog(title:string, message:string, action:string){
    this.ref = this.dialogService.open(ResultDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "500px", "overflow": "auto"},
      width:'30vw'
    });

    this.ref.onClose.subscribe(() =>{
      if (action == 'cancel') {
        this.router.navigate(['/my-ticket']);
      }
      else{
        this.getTicket();
      }
    });
  }

  showImageDialog(fileName:string, fileBase64:string){
    this.ref = this.dialogService.open(ImageDialogComponent, {
      header: fileName,
      data: {
        attachment: fileBase64,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "600px", "overflow": "auto"},
      width:'75vw',
    });
  }
}
