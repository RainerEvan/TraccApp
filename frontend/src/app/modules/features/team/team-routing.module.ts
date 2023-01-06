import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TeamShellComponent } from './components/team-shell/team-shell.component';
import { TeamDetailComponent } from './components/team/team-detail/team-detail.component';

const routes: Routes = [
  {
    path: '',
    component: TeamShellComponent,
    children:[
      {
        path: '',
        component: TeamDetailComponent,
      },
      {
        path: ':id',
        component: TeamDetailComponent,
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TeamRoutingModule { }
