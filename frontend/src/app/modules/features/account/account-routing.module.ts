import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountListComponent } from './components/account-list/account-list.component';
import { AccountDetailComponent } from './components/account-detail/account-detail.component';

const routes: Routes = [
  {
    path: '',
    component: AccountListComponent,
  },
  {
    path: ':id',
    component: AccountDetailComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountRoutingModule { }
