import { Component, OnInit } from '@angular/core';
import { DashboardActivity, DashboardAnalytics, TicketRate, TopApplications, TopTags } from 'src/app/models/dashboard';
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
  applications: string[];
  count: number[];
  topTags: TopTags[];
  ticketRate: TicketRate;

  pieChartData: any;
  pieChartOption: any;
  
  constructor(private dashboardService:DashboardService) { }

  ngOnInit(): void {
    this.getDashboardAnalytics();
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

  getAnalyticsData(analytics:DashboardAnalytics){
    this.period = analytics.period;
    this.minTickets = analytics.minTickets;
    this.maxTickets = analytics.maxTickets;
    this.avgTickets = analytics.avgTickets;
    this.totalTickets = analytics.totalTickets;
    this.label = analytics.label;
    this.data = analytics.data;
    this.applications = analytics.topApplications.map(topApplication => topApplication.application);
    this.count = analytics.topApplications.map(topApplication => topApplication.count);
    this.topTags = analytics.topTags;
    this.ticketRate = analytics.ticketRate;
    this.generateBarChart();
    this.generatePieChart();
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

  generatePieChart(){
    this.pieChartData = {
      labels: this.applications,
      datasets: [
        {
          backgroundColor: [
            "#555555",
            "#808080",
            "#A9A9A9",
            "#D3D3D3",
          ],
          data: this.count,
        },
      ]
    };

    this.pieChartOption = {
      plugins: {
        legend: {
          position: 'right',
          labels: {
            font:{
              family:"'Montserrat', sans-serif",
              size: 14,
            },
            color: '#000000',
            usePointStyle: true,
            padding: 16,
            boxWidth: 10,
            boxHeight: 10,
          }
        }
      },
    };
  }
}
