import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { LazyLoadEvent } from 'primeng/api';
import { Table } from 'primeng/table';
import { TicketService } from 'src/app/services/ticket/ticket.service';
import { Ticket } from 'src/app/types/ticket';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  tickets: Ticket[];
  loading: boolean;
  totalRecords: number;

  @ViewChild('ticketTable') ticketTable: Table | undefined;

  constructor(private ticketService: TicketService) { }

  ngOnInit() {
    this.getAllTickets();
  }

  getAllTickets(): void{
    this.loading = true;

    this.ticketService.getAllTickets().subscribe({
      next: (tickets: Ticket[]) => {
        this.tickets = tickets;
        this.loading = false;
        this.totalRecords = tickets.length;
      },
      error: (err: HttpErrorResponse) => {
        alert(err.message);
      }
    });
  }

  refresh(){
    window.location.reload();
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.ticketTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

}
