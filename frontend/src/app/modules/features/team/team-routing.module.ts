import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TeamShellComponent } from './components/team-shell/team-shell.component';

const routes: Routes = [
  {
    path: '',
    component: TeamShellComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TeamRoutingModule { }
