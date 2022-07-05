import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { Ticket } from 'src/app/models/ticket';
import { ConfirmationDialogComponent } from '../../modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';
import { SolveTicketComponent } from '../solve-ticket/solve-ticket.component';

@Component({
  selector: 'app-ticket-detail',
  templateUrl: './ticket-detail.component.html',
  styleUrls: ['./ticket-detail.component.css']
})
export class TicketDetailComponent implements OnInit, OnDestroy {

  loading: boolean;
  ticket: Ticket;
  ref: DynamicDialogRef;

  constructor(public dialogService:DialogService, private route:ActivatedRoute , private ticketSupportService:TicketSupportService) { }

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
        this.getTicket();
        this.showResultDialog("Success","Ticket has been added to your task list");
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed","There was a problem, try again later");
      }
    });
  }

  public closeTicket():void{
    const ticketId = this.ticket.ticketId;

    this.ticketSupportService.closeTicket(ticketId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.getTicket();
        this.showResultDialog("Success","Ticket has been closed successfully");
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed","There was a problem, try again later");
      }
    });
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
      contentStyle: {"max-height": "500px", "overflow": "auto"},
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
      contentStyle: {"max-height": "500px", "overflow": "auto"},
      width:'30vw'
    });
  }
}
