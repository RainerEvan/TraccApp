import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ConfigRoutingModule } from './config-routing.module';
import { ConfigShellComponent } from './components/config-shell/config-shell.component';
import { ApplicationListComponent } from './components/application/application-list/application-list.component';
import { AddApplicationComponent } from './components/application/add-application/add-application.component';
import { DivisionListComponent } from './components/division/division-list/division-list.component';
import { AddDivisionComponent } from './components/division/add-division/add-division.component';
import { TagsListComponent } from './components/tags/tags-list/tags-list.component';
import { AddTagsComponent } from './components/tags/add-tags/add-tags.component';
import { ScoringComponent } from './components/scoring/scoring.component';
import { SharedModule } from '../../shared/shared.module';
import { TableModule } from 'primeng/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';


@NgModule({
  declarations: [
    ConfigShellComponent,
    ApplicationListComponent,
    AddApplicationComponent,
    DivisionListComponent,
    AddDivisionComponent,
    TagsListComponent,
    AddTagsComponent,
    ScoringComponent
  ],
  imports: [
    CommonModule,
    ConfigRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    TableModule,
    InputTextModule,
  ]
})
export class ConfigModule { }
