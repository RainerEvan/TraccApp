import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShellComponent } from './components/shell/shell.component';
import { LoginComponent } from './components/login/login.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'ticket',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '',
    component:ShellComponent,
    children:[
      {
        path: 'ticket',
        loadChildren: () => import('./modules/features/ticket/ticket.module').then((m) => m.TicketModule),
      },
    ]
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
