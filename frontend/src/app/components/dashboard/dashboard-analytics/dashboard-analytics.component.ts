import { Component, OnInit } from '@angular/core';
import { DashboardActivity, DashboardAnalytics } from 'src/app/models/dashboard';
import { DashboardService } from 'src/app/services/dashboard/dashboard.service';

@Component({
  selector: 'app-dashboard-analytics',
  templateUrl: './dashboard-analytics.component.html',
  styleUrls: ['./dashboard-analytics.component.css']
})
export class DashboardAnalyticsComponent implements OnInit {

  barChartData: any;
  barChartOption: any;
  loading: boolean;
  
  dashboardAnalytics: DashboardAnalytics[];
  period: string;
  minTickets: number;
  maxTickets: number;
  avgTickets: number;
  totalTickets: number;
  label: string[];
  data: number[];

  pieChartData: any;
  pieChartOption: any;
  
  constructor(private dashboardService:DashboardService) { }

  ngOnInit(): void {
    this.getDashboardAnalytics();
    this.generatePieChart()
  }

  public getDashboardAnalytics(){
    this.loading = true;

    this.dashboardService.getDashboardAnalytics().subscribe({
      next: (analytics: DashboardAnalytics[]) => {
        this.dashboardAnalytics = analytics;
        this.getAnalyticsData(analytics[0]);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  getAnalyticsData(analtyics:DashboardAnalytics){
    this.period = analtyics.period;
    this.minTickets = analtyics.minTickets;
    this.maxTickets = analtyics.maxTickets;
    this.avgTickets = analtyics.avgTickets;
    this.totalTickets = analtyics.totalTickets;
    this.label = analtyics.label;
    this.data = analtyics.data
    this.generateBarChart();
  }

  generateBarChart(){
    this.barChartData = {
      labels: this.label,
      datasets: [
          {
              label: 'Tickets',
              backgroundColor: '#808080',
              data: this.data
          },
      ]
    };

    this.barChartOption = {
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

  generatePieChart(){
    this.pieChartData = {
      datasets: [
          {
              backgroundColor: [
                "#D3D3D3",
                "#A9A9A9",
                "#808080",
                "#555555"
              ],
              data: [20,15,25,40],
          },
      ]
    };

    this.pieChartOption = {
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
    };
  }
}
