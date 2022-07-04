import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountDetailComponent } from './components/accounts/account-detail/account-detail.component';
import { AccountListComponent } from './components/accounts/account-list/account-list.component';
import { ConfigPageComponent } from './components/config/config-page/config-page.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { TicketDetailComponent } from './components/tickets/ticket-detail/ticket-detail.component';
import { TicketListComponent } from './components/tickets/ticket-list/ticket-list.component';
import { AuthGuard } from './helpers/auth-guard';

const routes: Routes = [
  {path:'', redirectTo: 'dashboard',pathMatch: 'full'},
  {path:'login', component: LoginComponent},
  {path:'', component: HomeComponent, canActivate:[AuthGuard],
    children:[
      {path:'dashboard', component: DashboardComponent},
      {path:'tickets', component: TicketListComponent},
      {path:'ticket-detail/:id', component: TicketDetailComponent},
      {path:'accounts', component: AccountListComponent},
      {path:'account-detail/:id', component: AccountDetailComponent},
      {path:'config', component: ConfigPageComponent},
      {path:'profile', component: ProfileComponent},
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
