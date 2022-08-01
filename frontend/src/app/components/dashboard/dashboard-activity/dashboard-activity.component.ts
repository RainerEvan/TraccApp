import { Component, OnInit } from '@angular/core';
import { DashBoardActivity } from 'src/app/models/dashboard';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';

@Component({
  selector: 'app-dashboard-activity',
  templateUrl: './dashboard-activity.component.html',
  styleUrls: ['./dashboard-activity.component.css']
})
export class DashboardActivityComponent implements OnInit {
  
  basicData: any;
  basicOption: any;
  loading: boolean;
  
  dashboardActivity: DashBoardActivity[];
  period: string;
  totalPending: number;
  totalInProgress: number;
  totalResolved: number;
  totalDropped: number;
  totalTickets: number;
  label: string[];
  data: number[];

  constructor(private dashboardService:DashboardService) { }

  ngOnInit(): void {
    this.getDashboardActivity();
  }

  public getDashboardActivity(){
    this.loading = true;

    this.dashboardService.getDashboardActivity().subscribe({
      next: (activity: DashBoardActivity[]) => {
        this.dashboardActivity = activity;
        this.getActivityData(activity[0]);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  getActivityData(activity:DashBoardActivity){
    this.period = activity.period;
    this.totalPending = activity.totalPending;
    this.totalInProgress = activity.totalInProgress;
    this.totalResolved = activity.totalResolved;
    this.totalDropped = activity.totalDropped;
    this.totalTickets = activity.totalTickets;
    this.label = activity.label;
    this.data = activity.data
    this.generateChart();
  }

  // getThisWeek(activity:DashBoardActivity){
  //   this.totalPending = activity.totalPending;
  //   this.totalInProgress = activity.totalInProgress;
  //   this.totalResolved = activity.totalResolved;
  //   this.totalDropped = activity.totalDropped;
  //   this.totalTickets = activity.totalTickets;
  //   this.label = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
  //   this.data = [0,1,4,2,1,1,0];
  //   this.generateChart();
  // }

  // getThisMonth(activity:DashBoardActivity){
  //   this.totalPending = 4;
  //   this.totalInProgress = 2;
  //   this.totalResolved = 33;
  //   this.totalDropped = 2;
  //   this.totalTickets = 39;
  //   this.label = ['Week 1','Week 2','Week 3','Week 4'];
  //   this.data = [7,12,9,11];
  //   this.generateChart();
  // }

  // getThisYear(activity:DashBoardActivity){
  //   this.totalPending = 4;
  //   this.totalInProgress = 2;
  //   this.totalResolved = 429;
  //   this.totalDropped = 3;
  //   this.totalTickets = 435;
  //   this.label = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
  //   this.data = [32,20,18,25,38,23,19,27,21,29,17,21];
  //   this.generateChart();
  // }

  generateChart(){
    this.basicData = {
      labels: this.label,
      datasets: [
          {
              label: 'Tickets',
              fill: false,
              borderColor: '#808080',
              backgroundColor: '#808080',
              tension: .4,
              data: this.data
          },
      ]
    };

    this.basicOption = {
      plugins: {
        legend: {
            labels: {
              font:{
                family:"'Montserrat', sans-serif",
              },
              color: '#000000'
            }
        }
      },
      scales: {
        x: {
          title: {
            display: true,
            text: this.period,
            font:{
              family:"'Montserrat', sans-serif",
              weight:500,
              size:14,
            },
            color:'#b4b4b4',
          },
          ticks: {
            font:{
              family:"'Montserrat', sans-serif",
            },
            color: '#000000'
          },
          grid: {
            color: 'rgba(255,255,255,0.2)',
          }
        },
        y: {
          ticks: {
            font:{
              family:"'Montserrat', sans-serif",
            },
            maxTicksLimit: 10,
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
