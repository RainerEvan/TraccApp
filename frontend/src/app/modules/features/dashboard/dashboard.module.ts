import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardShellComponent } from './components/dashboard-shell/dashboard-shell.component';
import { DashboardActivityComponent } from './components/dashboard-activity/dashboard-activity.component';
import { DashboardAnalyticsComponent } from './components/dashboard-analytics/dashboard-analytics.component';
import { SkeletonModule } from 'primeng/skeleton';
import { ChartModule } from 'primeng/chart';
import { DropdownModule } from 'primeng/dropdown';

@NgModule({
  declarations: [
    DashboardShellComponent,
    DashboardActivityComponent,
    DashboardAnalyticsComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    SkeletonModule,
    ChartModule,
    DropdownModule
  ]
})
export class DashboardModule { }
