import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountDetailComponent } from './components/accounts/account-detail/account-detail.component';
import { AccountListComponent } from './components/accounts/account-list/account-list.component';
import { ConfigPageComponent } from './components/config/config-page/config-page.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { MyTaskListComponent } from './components/my-tasks/my-task-list/my-task-list.component';
import { MyTicketListComponent } from './components/my-tickets/my-ticket-list/my-ticket-list.component';
import { ProfileComponent } from './components/profile/profile.component';
import { TicketDetailComponent } from './components/tickets/ticket-detail/ticket-detail.component';
import { TicketListComponent } from './components/tickets/ticket-list/ticket-list.component';
import { AuthGuard } from './helpers/auth-guard';

const routes: Routes = [
  {
    path:'', redirectTo: 'dashboard',pathMatch: 'full'
  },
  {
    path:'login', 
    component: LoginComponent
  },
  {
    path:'', 
    component: HomeComponent, 
    canActivate:[AuthGuard],
    children:[
      {
        path:'dashboard', 
        component: DashboardComponent,
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
      },
      {
        path:'profile', 
        component: ProfileComponent,
        canActivate:[AuthGuard]
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
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
