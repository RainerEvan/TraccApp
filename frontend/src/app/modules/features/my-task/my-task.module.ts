import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MyTaskRoutingModule } from './my-task-routing.module';
import { TaskListComponent } from './components/task-list/task-list.component';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';


@NgModule({
  declarations: [
    TaskListComponent
  ],
  imports: [
    CommonModule,
    MyTaskRoutingModule,
    TableModule,
    InputTextModule,
  ]
})
export class MyTaskModule { }
