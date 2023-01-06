import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TeamRoutingModule } from './team-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { TableModule } from 'primeng/table';
import { TeamListComponent } from './components/team/team-list/team-list.component';
import { AddTeamComponent } from './components/team/add-team/add-team.component';
import { MemberListComponent } from './components/member/member-list/member-list.component';
import { AddMemberComponent } from './components/member/add-member/add-member.component';
import { TeamShellComponent } from './components/team-shell/team-shell.component';
import { InputTextModule } from 'primeng/inputtext';
import { TeamDetailComponent } from './components/team/team-detail/team-detail.component';


@NgModule({
  declarations: [
    TeamListComponent,
    AddTeamComponent,
    MemberListComponent,
    AddMemberComponent,
    TeamShellComponent,
    TeamDetailComponent
  ],
  imports: [
    CommonModule,
    TeamRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    TableModule,
    InputTextModule,
  ]
})
export class TeamModule { }
