import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TicketService } from 'src/app/services/ticket/ticket.service';
import { Ticket } from 'src/app/types/ticket';

@Component({
  selector: 'app-ticket-detail',
  templateUrl: './ticket-detail.component.html',
  styleUrls: ['./ticket-detail.component.css']
})
export class TicketDetailComponent implements OnInit {

  ticket: Ticket;

  constructor(private route:ActivatedRoute , private ticketService:TicketService) { }

  ngOnInit(): void {
    this.getTicket()
  }

  public getTicket():void{
    const ticketId = this.route.snapshot.paramMap.get('id');

    this.ticketService.getTicket(ticketId).subscribe({
      next: (ticket: Ticket) => {
        this.ticket = ticket;
      },
      error: (error: any) => {
        console.log(error);
      }
    })
  }
  
}
