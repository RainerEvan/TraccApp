import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TicketRoutingModule } from './ticket-routing.module';
import { TicketListComponent } from './components/ticket/ticket-list/ticket-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { DropdownModule } from 'primeng/dropdown';
import { EditorModule } from 'primeng/editor';
import { SharedModule } from '../../shared/shared.module';
import { AssignTicketComponent } from './components/ticket/assign-ticket/assign-ticket.component';
import { ReassignTicketComponent } from './components/ticket/reassign-ticket/reassign-ticket.component';
import { RequestDropTicketComponent } from './components/ticket/request-drop-ticket/request-drop-ticket.component';
import { SolveTicketComponent } from './components/ticket/solve-ticket/solve-ticket.component';
import { TicketDetailComponent } from './components/ticket/ticket-detail/ticket-detail.component';
import { CommentListComponent } from './components/comment/comment-list/comment-list.component';
import { EditCommentComponent } from './components/comment/edit-comment/edit-comment.component';


@NgModule({
  declarations: [
    TicketListComponent,
    AssignTicketComponent,
    ReassignTicketComponent,
    RequestDropTicketComponent,
    SolveTicketComponent,
    TicketDetailComponent,
    CommentListComponent,
    EditCommentComponent,
  ],
  imports: [
    CommonModule,
    TicketRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    TableModule,
    InputTextModule,
    InputTextareaModule,
    AutoCompleteModule,
    DropdownModule,
    EditorModule
  ]
})
export class TicketModule { }
