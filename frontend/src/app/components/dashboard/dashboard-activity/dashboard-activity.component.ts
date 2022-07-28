import { Component, OnInit } from '@angular/core';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';

@Component({
  selector: 'app-dashboard-activity',
  templateUrl: './dashboard-activity.component.html',
  styleUrls: ['./dashboard-activity.component.css']
})
export class DashboardActivityComponent implements OnInit {
  
  basicData: any;
  basicOption: any;
  timeframe: string[];
  totalData: number[];

  totalRecords: number;
  totalPending: number;
  totalOngoing: number;
  totalClosed: number;
  totalDropped: number;

  constructor(private ticketSupportService: TicketSupportService) { }

  ngOnInit(): void {
    this.getThisWeek();
  }

  getThisWeek(){
    this.totalRecords = 9;
    this.totalPending = 4;
    this.totalOngoing = 2;
    this.totalClosed = 2;
    this.totalDropped = 1;
    this.timeframe = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
    this.totalData = [0,1,4,2,1,1,0];
    this.generateChart();
  }

  getThisMonth(){
    this.totalRecords = 39;
    this.totalPending = 4;
    this.totalOngoing = 2;
    this.totalClosed = 33;
    this.totalDropped = 2;
    this.timeframe = ['Week 1','Week 2','Week 3','Week 4'];
    this.totalData = [7,12,9,11];
    this.generateChart();
  }

  getThisYear(){
    this.totalRecords = 435;
    this.totalPending = 4;
    this.totalOngoing = 2;
    this.totalClosed = 429;
    this.totalDropped = 3;
    this.timeframe = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    this.totalData = [32,20,18,25,38,23,19,27,21,29,17,21];
    this.generateChart();
  }

  generateChart(){
    this.basicData = {
      labels: this.timeframe,
      datasets: [
          {
              label: 'My First dataset',
              fill: false,
              borderColor: '#808080',
              backgroundColor: '#808080',
              tension: .4,
              data: this.totalData
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
