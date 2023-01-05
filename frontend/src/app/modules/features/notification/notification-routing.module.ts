import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotificationShellComponent } from './components/notification-shell/notification-shell.component';
import { NotificationDetailComponent } from './components/notification-detail/notification-detail.component';

const routes: Routes = [
  {
    path: '',
    component: NotificationShellComponent,
    children:[
      {
        path: '',
        component: NotificationDetailComponent,
      },
      {
        path: ':id',
        component: NotificationDetailComponent,
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NotificationRoutingModule { }
