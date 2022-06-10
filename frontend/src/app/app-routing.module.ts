import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { TicketsComponent } from './components/tickets/tickets.component';

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'dashboard', component: DashboardComponent},
  {path:'tickets', component: TicketsComponent},
  {path:'', redirectTo: 'dashboard',pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
