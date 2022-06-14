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
  virtualDatabase: Ticket[];
  loading: boolean;
  totalRecords: number;

  @ViewChild('ticketTable') ticketTable: Table | undefined;

  constructor(private ticketService: TicketService) { }

  ngOnInit() {
    this.getAllTickets();
  }

  getAllTickets(): void{
    this.ticketService.getAllTickets().subscribe(tickets => {
      this.virtualDatabase = tickets;
      this.totalRecords = tickets.length;
    });
    
    this.totalRecords = this.tickets.length;
  }

  loadTickets(event: LazyLoadEvent){
    this.loading = true;

    setTimeout(() => {
      if(this.virtualDatabase){
        this.tickets = this.virtualDatabase.slice(event.first, (event.first + event.rows));
        this.loading = false;
      }
    }, 1000);
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.ticketTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

}
