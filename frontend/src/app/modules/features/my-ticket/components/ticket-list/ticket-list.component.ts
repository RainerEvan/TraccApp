import { Component, OnInit, ViewChild } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { Tickets } from 'src/app/models/tickets';
import { AuthService } from 'src/app/services/auth/auth.service';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { AddTicketComponent } from '../add-ticket/add-ticket.component';
import { cloneDeep } from '@apollo/client/utilities';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  accountId: string;
  myTickets: Tickets[];
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
      next: (tickets: Tickets[]) => {
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
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"550px", "max-width":"700px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAllTickets();
      } 
    });
  }

}
