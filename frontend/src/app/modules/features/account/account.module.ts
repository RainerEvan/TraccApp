import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountRoutingModule } from './account-routing.module';
import { AccountListComponent } from './components/account-list/account-list.component';
import { AccountDetailComponent } from './components/account-detail/account-detail.component';
import { AddAccountComponent } from './components/add-account/add-account.component';
import { EditAccountComponent } from './components/edit-account/edit-account.component';
import { SharedModule } from '../../shared/shared.module';
import { TableModule } from 'primeng/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InputTextModule } from 'primeng/inputtext';
import { SkeletonModule } from 'primeng/skeleton';

@NgModule({
  declarations: [
    AccountListComponent,
    AccountDetailComponent,
    AddAccountComponent,
    EditAccountComponent
  ],
  imports: [
    CommonModule,
    AccountRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    TableModule,
    InputTextModule,
    DropdownModule,
    InputSwitchModule,
    SkeletonModule
  ]
})
export class AccountModule { }
