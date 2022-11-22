import { Component, OnInit } from '@angular/core';
import { DashboardActivity } from 'src/app/models/dashboard';
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
  
  dashboardActivity: DashboardActivity[];
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
      next: (activity: DashboardActivity[]) => {
        this.dashboardActivity = activity;
        this.getActivityData(activity[0]);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  getActivityData(activity:DashboardActivity){
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
              fill: false,
              borderColor: '#0066AE',
              backgroundColor: '#0066AE',
              tension: .4,
              data: this.data
          },
      ]
    };

    this.basicOption = {
      plugins: {
        legend: {
          display:false,
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
