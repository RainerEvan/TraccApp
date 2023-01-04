import { Component, OnInit, ViewChild } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { Tickets } from 'src/app/models/tickets';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  tickets: Tickets[];
  loading: boolean;
  totalRecords: number;
  ref: DynamicDialogRef;
  @ViewChild('ticketTable') ticketTable: Table | undefined;

  constructor(public dialogService:DialogService, private ticketSupportService: TicketSupportService) { }

  ngOnInit() {
    this.getAllTickets();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAllTickets(): void{
    this.loading = true;

    this.ticketSupportService.getAllTickets().subscribe({
      next: (tickets: Tickets[]) => {
        this.tickets = cloneDeep(tickets);
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

}
