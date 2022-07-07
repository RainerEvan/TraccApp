import { Component, OnInit, ViewChild } from '@angular/core';
import { Table } from 'primeng/table';
import { AccountService } from 'src/app/services/account/account.service';
import { Account } from 'src/app/models/account';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { AddAccountComponent } from '../add-account/add-account.component';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  accounts: Account[];
  loading: boolean;
  totalRecords: number;
  ref: DynamicDialogRef;
  @ViewChild('accountTable') accountTable: Table | undefined;

  constructor(public dialogService:DialogService, private accountService: AccountService) { }

  ngOnInit(): void {
    this.getAllAccounts();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  getAllAccounts(): void{
    this.loading = true;

    this.accountService.getAllAccounts().subscribe({
      next: (accounts: Account[]) => {
        this.accounts = accounts;
        this.loading = false;
        this.totalRecords = accounts.length;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.accountTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  showAddAccountDialog(){
    this.ref = this.dialogService.open(AddAccountComponent, {
      header: "Add Account",
      footer: " ",
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px", "overflow": "auto"},
      width:'40vw',
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAllAccounts();
      } 
    });
  }
}
