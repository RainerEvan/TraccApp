import { Component, OnInit } from '@angular/core';
import { Tickets } from 'src/app/models/tickets';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';

@Component({
  selector: 'app-dashboard-activity',
  templateUrl: './dashboard-activity.component.html',
  styleUrls: ['./dashboard-activity.component.css']
})
export class DashboardActivityComponent implements OnInit {
  
  tickets: Tickets[];
  loading: boolean;
  basicData: any;
  basicOption: any;

  totalRecords: number;
  totalPending: number;
  totalOngoing: number;
  totalClosed: number;
  totalDropped: number;

  constructor(private ticketSupportService: TicketSupportService) { }

  ngOnInit(): void {
    this.getThisWeek();
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

  getThisWeek(){
    this.totalRecords = 7;
    this.totalPending = 4;
    this.totalOngoing = 2;
    this.totalClosed = 1;
    this.totalDropped = 1;
  }

  getThisMonth(){
    this.totalRecords = 39;
    this.totalPending = 4;
    this.totalOngoing = 2;
    this.totalClosed = 33;
    this.totalDropped = 2;
  }

  getThisYear(){
    this.totalRecords = 435;
    this.totalPending = 4;
    this.totalOngoing = 2;
    this.totalClosed = 429;
    this.totalDropped = 3;
  }

  generateChart(data:any){
    let appName = data.map((ticket:any) => ticket.application.name);

    this.basicData = {
      labels: appName,
      datasets: [
          {
              label: 'My First dataset',
              fill: false,
              borderColor: '#808080',
              backgroundColor: '#808080',
              tension: .4,
              data: [65, 59, 42, 70, 52, 78]
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
