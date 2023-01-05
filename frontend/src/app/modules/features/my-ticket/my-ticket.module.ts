import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyTicketRoutingModule } from './my-ticket-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { TicketListComponent } from './components/ticket-list/ticket-list.component';
import { AddTicketComponent } from './components/add-ticket/add-ticket.component';
import { SharedModule } from '../../shared/shared.module';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DropdownModule } from 'primeng/dropdown';


@NgModule({
  declarations: [
    TicketListComponent,
    AddTicketComponent
  ],
  imports: [
    CommonModule,
    MyTicketRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    TableModule,
    InputTextModule,
    InputTextareaModule,
    DropdownModule
  ]
})
export class MyTicketModule { }
