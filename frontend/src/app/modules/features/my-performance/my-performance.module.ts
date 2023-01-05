import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyPerformanceRoutingModule } from './my-performance-routing.module';
import { PerformanceComponent } from './components/performance/performance.component';
import { SkeletonModule } from 'primeng/skeleton';
import { ChartModule } from 'primeng/chart';


@NgModule({
  declarations: [
    PerformanceComponent
  ],
  imports: [
    CommonModule,
    MyPerformanceRoutingModule,
    SkeletonModule,
    ChartModule,
  ]
})
export class MyPerformanceModule { }
