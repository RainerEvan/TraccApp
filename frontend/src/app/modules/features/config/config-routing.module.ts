import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConfigShellComponent } from './components/config-shell/config-shell.component';
import { DivisionListComponent } from './components/division/division-list/division-list.component';
import { ApplicationListComponent } from './components/application/application-list/application-list.component';
import { TagsListComponent } from './components/tags/tags-list/tags-list.component';
import { ScoringComponent } from './components/scoring/scoring.component';

const routes: Routes = [
  {
    path:'', 
    component: ConfigShellComponent,
    children:[
      {
        path:'',
        redirectTo: 'division',
        pathMatch: 'full'
      },
      {
        path:'division',
        component: DivisionListComponent,
      },
      {
        path:'application',
        component: ApplicationListComponent,
      },
      {
        path:'tags',
        component: TagsListComponent,
      },
      {
        path:'scoring',
        component: ScoringComponent,
      },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConfigRoutingModule { }
