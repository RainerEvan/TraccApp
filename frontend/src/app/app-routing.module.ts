import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountDetailComponent } from './components/accounts/account-detail/account-detail.component';
import { AccountListComponent } from './components/accounts/account-list/account-list.component';
import { AccountPageComponent } from './components/accounts/account-page/account-page.component';
import { ConfigPageComponent } from './components/config/config-page/config-page.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { TicketDetailComponent } from './components/tickets/ticket-detail/ticket-detail.component';
import { TicketListComponent } from './components/tickets/ticket-list/ticket-list.component';
import { TicketPageComponent } from './components/tickets/ticket-page/ticket-page.component';

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'dashboard', component: DashboardComponent},
  {path:'tickets', component: TicketPageComponent,
    children:[
      {path:'', component: TicketListComponent},
      {path:'ticket-detail/:id', component: TicketDetailComponent}
    ]
  },
  {path:'accounts', component: AccountPageComponent,
    children:[
      {path:'', component: AccountListComponent},
      {path:'account-detail/:id', component: AccountDetailComponent}
    ]
  },
  {path:'config', component: ConfigPageComponent,
    children:[
      {path:'', component: ConfigPageComponent},
  ]
},
  {path:'', redirectTo: 'dashboard',pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
