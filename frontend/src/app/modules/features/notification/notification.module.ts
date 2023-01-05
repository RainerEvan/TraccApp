import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NotificationRoutingModule } from './notification-routing.module';
import { TableModule } from 'primeng/table';
import { NotificationShellComponent } from './components/notification-shell/notification-shell.component';
import { NotificationListComponent } from './components/notification-list/notification-list.component';
import { NotificationDetailComponent } from './components/notification-detail/notification-detail.component';


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
  ]
})
export class NotificationModule { }
