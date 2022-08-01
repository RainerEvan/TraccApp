import { Component, OnInit } from '@angular/core';
import { DashBoardActivity } from 'src/app/models/dashboard';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';

@Component({
  selector: 'app-dashboard-analytics',
  templateUrl: './dashboard-analytics.component.html',
  styleUrls: ['./dashboard-analytics.component.css']
})
export class DashboardAnalyticsComponent implements OnInit {

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

  generateChart(){
    this.basicData = {
      labels: this.label,
      datasets: [
          {
              label: 'Tickets',
              backgroundColor: '#808080',
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
