import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NotificationRoutingModule } from './notification-routing.module';
import { TableModule } from 'primeng/table';
import { NotificationShellComponent } from './components/notification-shell/notification-shell.component';
import { NotificationListComponent } from './components/notification-list/notification-list.component';
import { NotificationDetailComponent } from './components/notification-detail/notification-detail.component';
import { SkeletonModule } from 'primeng/skeleton';


@NgModule({
  declarations: [
    NotificationShellComponent,
    NotificationListComponent,
    NotificationDetailComponent
  ],
  imports: [
    CommonModule,
    NotificationRoutingModule,
    TableModule,
    SkeletonModule
  ]
})
export class NotificationModule { }
