import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShellComponent } from './components/shell/shell.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './utils/auth-guard/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
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
        path: 'dashboard',
        loadChildren: () => import('./modules/features/dashboard/dashboard.module').then((m) => m.DashboardModule),
        canActivate:[AuthGuard],
      },
      {
        path: 'ticket',
        loadChildren: () => import('./modules/features/ticket/ticket.module').then((m) => m.TicketModule),
        canActivate:[AuthGuard],
      },
      {
        path: 'config',
        loadChildren: () => import('./modules/features/config/config.module').then((m) => m.ConfigModule),
        canActivate:[AuthGuard],
        data:{roles:'ADMIN'},
      },
      {
        path: 'account',
        loadChildren: () => import('./modules/features/account/account.module').then((m) => m.AccountModule),
        canActivate:[AuthGuard],
      },
      {
        path: 'my-ticket',
        loadChildren: () => import('./modules/features/my-ticket/my-ticket.module').then((m) => m.MyTicketModule),
        canActivate:[AuthGuard],
        data:{roles:'USER'},
      },
      {
        path: 'my-task',
        loadChildren: () => import('./modules/features/my-task/my-task.module').then((m) => m.MyTaskModule),
        canActivate:[AuthGuard],
        data:{roles:'DEVELOPER'},
      },
      {
        path: 'my-performance',
        loadChildren: () => import('./modules/features/my-performance/my-performance.module').then((m) => m.MyPerformanceModule),
        canActivate:[AuthGuard],
        data:{roles:'DEVELOPER'},
      },
      {
        path: 'team',
        loadChildren: () => import('./modules/features/team/team.module').then((m) => m.TeamModule),
        canActivate:[AuthGuard],
        data:{roles:'SUPERVISOR'},
      },
      {
        path: 'notification',
        loadChildren: () => import('./modules/features/notification/notification.module').then((m) => m.NotificationModule),
        canActivate:[AuthGuard],
      },
      {
        path: 'profile',
        loadChildren: () => import('./modules/features/profile/profile.module').then((m) => m.ProfileModule),
        canActivate:[AuthGuard],
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
  imports: [RouterModule.forRoot(routes,{scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
