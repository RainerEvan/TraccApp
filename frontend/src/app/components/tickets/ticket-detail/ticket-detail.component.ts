import { Component, OnInit } from '@angular/core';
import { TicketService } from 'src/app/services/ticket/ticket.service';
import { Ticket } from 'src/app/types/ticket';

@Component({
  selector: 'app-ticket-detail',
  templateUrl: './ticket-detail.component.html',
  styleUrls: ['./ticket-detail.component.css']
})
export class TicketDetailComponent implements OnInit {

  ticket: Ticket;

  constructor(private ticketService:TicketService) { }

  ngOnInit(): void {

  }

  
}
