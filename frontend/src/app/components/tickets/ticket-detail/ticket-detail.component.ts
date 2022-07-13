import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { Ticket } from 'src/app/models/ticket';
import { ConfirmationDialogComponent } from '../../modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';
import { SolveTicketComponent } from '../solve-ticket/solve-ticket.component';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-ticket-detail',
  templateUrl: './ticket-detail.component.html',
  styleUrls: ['./ticket-detail.component.css']
})
export class TicketDetailComponent implements OnInit, OnDestroy {

  loading: boolean;
  ticket: Ticket;
  isCurrDeveloper: boolean;
  isCurrUser: boolean;
  ref: DynamicDialogRef;

  constructor(private router: Router, public dialogService:DialogService, private route:ActivatedRoute, private ticketSupportService:TicketSupportService, private authService: AuthService) { }

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
      next: (ticket: Ticket) => {
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

  checkDeveloper(){
    this.isCurrDeveloper = false;

    const accountId = this.authService.accountValue.accountId;
    const developerId = this.ticket.support[0].developer.id;

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
}
