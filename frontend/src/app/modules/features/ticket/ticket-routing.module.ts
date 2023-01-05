import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TicketListComponent } from './components/ticket/ticket-list/ticket-list.component';
import { TicketDetailComponent } from './components/ticket/ticket-detail/ticket-detail.component';

const routes: Routes = [
  {
    path: '',
    component: TicketListComponent,
  },
  {
    path:':id', 
    component: TicketDetailComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TicketRoutingModule { }
