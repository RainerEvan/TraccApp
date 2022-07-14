import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { Ticket } from 'src/app/models/ticket';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  
  tickets: Ticket[];
  loading: boolean;
  
  constructor(private ticketSupportService: TicketSupportService) { }

  ngOnInit(): void {
    this.getAllTickets();
  }

  public getAllTickets(): void{
    this.loading = true;

    this.ticketSupportService.getAllTickets().subscribe({
      next: (tickets: Ticket[]) => {
        this.tickets = cloneDeep(tickets);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }
}
