import { Component, OnInit } from '@angular/core';
import { Tickets } from 'src/app/models/tickets';
import { AuthService } from 'src/app/services/auth/auth.service';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  
  tickets: Tickets[];
  loading: boolean;

  basicData: any;
  basicOption: any;
  
  constructor(private ticketSupportService: TicketSupportService, private authService: AuthService) { }

  ngOnInit(): void {
    this.getAllTickets();
  }

  public getAllTickets(): void{
    this.loading = true;

    this.ticketSupportService.getAllTickets().subscribe({
      next: (tickets: Tickets[]) => {
        this.tickets = tickets;
        this.loading = false;
        this.generateChart(tickets);
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  public getAllTicketsForUser(): void{
    const accountId = this.authService.accountValue.accountId;

    this.loading = true;
    this.ticketSupportService.getAllTicketsForUser(accountId).subscribe({
      next: (tickets: Tickets[]) => {
        this.tickets = tickets;
        this.loading = false;
        this.generateChart(tickets);
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  generateChart(data:any){
    let appName = data.map((ticket:any) => ticket.application.name);

    this.basicData = {
      labels: appName,
      datasets: [
          {
              label: 'My First dataset',
              backgroundColor: '#808080',
              data: [65, 59, 80]
          },
      ]
    };

    this.basicOption = {
      plugins: {
          legend: {
              labels: {
                  color: '#000000'
              }
          }
      },
      scales: {
          x: {
              ticks: {
                  color: '#000000'
              },
              grid: {
                  color: 'rgba(255,255,255,0.2)'
              }
          },
          y: {
              ticks: {
                  color: '#000000'
              },
              grid: {
                  color: 'rgba(255,255,255,0.2)'
              }
          }
      }
    };
  }
}
