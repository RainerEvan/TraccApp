import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountDetailComponent } from './components/accounts/account-detail/account-detail.component';
import { AccountListComponent } from './components/accounts/account-list/account-list.component';
import { ConfigApplicationComponent } from './components/config/config-application/config-application.component';
import { ConfigDivisionComponent } from './components/config/config-division/config-division.component';
import { ConfigPageComponent } from './components/config/config-page/config-page.component';
import { ConfigScoringComponent } from './components/config/config-scoring/config-scoring.component';
import { ConfigTagComponent } from './components/config/config-tag/config-tag.component';
import { DashboardPageComponent } from './components/dashboard/dashboard-page/dashboard-page.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { MyPerformanceComponent } from './components/my-performance/my-performance.component';
import { MyTaskListComponent } from './components/my-tasks/my-task-list/my-task-list.component';
import { MyTicketListComponent } from './components/my-tickets/my-ticket-list/my-ticket-list.component';
import { NotificationPageComponent } from './components/notifications/notification-page/notification-page.component';
import { ProfileDetailComponent } from './components/profile/profile-detail/profile-detail.component';
import { TicketDetailComponent } from './components/tickets/ticket-detail/ticket-detail.component';
import { TicketListComponent } from './components/tickets/ticket-list/ticket-list.component';
import { AuthGuard } from './helpers/auth-guard';

const routes: Routes = [
  {
    path:'',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path:'login', 
    component: LoginComponent,
  },
  {
    path:'', 
    component: HomeComponent, 
    canActivate:[AuthGuard],
    children:[
      {
        path:'dashboard', 
        component: DashboardPageComponent,
        canActivate:[AuthGuard]
      },
      {
        path:'tickets', 
        component: TicketListComponent,
        canActivate:[AuthGuard]
      },
      {
        path:'ticket-detail/:id', 
        component: TicketDetailComponent,
        canActivate:[AuthGuard]
      },
      {
        path:'accounts', 
        component: AccountListComponent,
        canActivate:[AuthGuard],
        data:{roles:'ADMIN'},
      },
      {
        path:'account-detail/:id', 
        component: AccountDetailComponent,
        canActivate:[AuthGuard]
      },
      {
        path:'config', 
        component: ConfigPageComponent,
        canActivate:[AuthGuard],
        data:{roles:'ADMIN'},
        children:[
          {
            path:'',
            redirectTo: 'division',
            pathMatch: 'full'
          },
          {
            path:'division',
            component: ConfigDivisionComponent,
            canActivate:[AuthGuard],
            data:{roles:'ADMIN'},
          },
          {
            path:'application',
            component: ConfigApplicationComponent,
            canActivate:[AuthGuard],
            data:{roles:'ADMIN'},
          },
          {
            path:'tag',
            component: ConfigTagComponent,
            canActivate:[AuthGuard],
            data:{roles:'ADMIN'},
          },
          {
            path:'scoring',
            component: ConfigScoringComponent,
            canActivate:[AuthGuard],
            data:{roles:'ADMIN'},
          },
        ]
      },
      {
        path:'profile', 
        component: ProfileDetailComponent,
        canActivate:[AuthGuard]
      },
      {
        path:'notifications', 
        component: NotificationPageComponent,
        canActivate:[AuthGuard],
      },
      {
        path:'my-ticket',
        component: MyTicketListComponent,
        canActivate:[AuthGuard],
        data:{roles:'USER'},
      },
      {
        path:'my-task', 
        component: MyTaskListComponent,
        canActivate:[AuthGuard],
        data:{roles:'DEVELOPER'},
      },
      {
        path:'my-performance', 
        component: MyPerformanceComponent,
        canActivate:[AuthGuard],
        data:{roles:'DEVELOPER'},
      },
    ]
  },
  {
    path:'**',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
