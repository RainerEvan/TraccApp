import { Component, OnInit, ViewChild } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { Ticket } from 'src/app/models/ticket';
import { AddTicketComponent } from '../../tickets/add-ticket/add-ticket.component';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-my-ticket-list',
  templateUrl: './my-ticket-list.component.html',
  styleUrls: ['./my-ticket-list.component.css']
})
export class MyTicketListComponent implements OnInit {

  accountId: string;
  myTickets: Ticket[];
  loading: boolean;
  totalRecords: number;
  ref: DynamicDialogRef;
  @ViewChild('ticketTable') ticketTable: Table | undefined;

  constructor(public dialogService:DialogService, private ticketSupportService: TicketSupportService, private authService: AuthService) { }

  ngOnInit() {
    this.accountId = this.authService.accountValue.accountId;
    this.getAllTickets();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAllTickets(): void{
    this.loading = true;
    this.ticketSupportService.getAllTicketsForUser(this.accountId).subscribe({
      next: (tickets: Ticket[]) => {
        this.myTickets = cloneDeep(tickets);
        this.loading = false;
        this.totalRecords = tickets.length;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.ticketTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  showAddTicketDialog(){
    this.ref = this.dialogService.open(AddTicketComponent, {
      header: "Add Ticket",
      footer: " ",
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px", "overflow": "auto"},
      width:'40vw',
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAllTickets();
      } 
    });
  }

}
