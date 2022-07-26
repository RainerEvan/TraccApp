import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-activity',
  templateUrl: './dashboard-activity.component.html',
  styleUrls: ['./dashboard-activity.component.css']
})
export class DashboardActivityComponent implements OnInit {
  
  totalRecords: number;
  totalPending: number;
  totalOngoing: number;
  totalClosed: number;
  totalDropped: number;

  constructor() { }

  ngOnInit(): void {
    this.getThisWeek();
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

}
