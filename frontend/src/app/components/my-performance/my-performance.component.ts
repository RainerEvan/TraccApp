import { Component, OnInit } from '@angular/core';
import { Performance } from 'src/app/models/performance';
import { AuthService } from 'src/app/services/auth/auth.service';
import { PerformanceService } from 'src/app/services/performance/performance.service';

@Component({
  selector: 'app-my-performance',
  templateUrl: './my-performance.component.html',
  styleUrls: ['./my-performance.component.css']
})
export class MyPerformanceComponent implements OnInit {

  basicData: any;
  basicOption: any;
  loading: boolean;
  
  performances: Performance[];
  period: string;
  totalInProgress: number;
  totalResolved: number;
  totalDropped: number;
  totalReassigned: number;
  totalTickets: number;
  rate: string;
  label: string[];
  data: number[];

  constructor(private performanceService:PerformanceService, private authService:AuthService) { }

  ngOnInit(): void {
    this.getPerformance();
  }

  public getPerformance(){
    const accountId = this.authService.accountValue.accountId;

    this.loading = true;

    this.performanceService.getPerformanceForDeveloper(accountId).subscribe({
      next: (performances: Performance[]) => {
        this.performances = performances;
        this.getPerformanceData(performances[0]);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  getPerformanceData(performance:Performance){
    this.period = performance.period;
    this.totalInProgress = performance.totalInProgress;
    this.totalResolved = performance.totalResolved;
    this.totalDropped = performance.totalDropped;
    this.totalReassigned = performance.totalReassigned;
    this.totalTickets = performance.totalTickets;
    this.rate = performance.rate;
    this.label = performance.label;
    this.data = performance.data
    this.generateChart();
  }

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
          display:false,
        },
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
            color: 'rgba(0,0,0,0.1)',
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
            color: 'rgba(0,0,0,0.1)'
          }
        }
      }
    };
  }
}
