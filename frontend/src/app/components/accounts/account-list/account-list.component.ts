import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Table } from 'primeng/table';
import { AccountService } from 'src/app/services/account/account.service';
import { Account } from 'src/app/types/account';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  accounts: Account[];
  loading: boolean;
  totalRecords: number;

  @ViewChild('accountTable') accountTable: Table | undefined;

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {
    this.getAllAccounts();
  }

  getAllAccounts(): void{
    this.loading = true;

    this.accountService.getAllAccounts().subscribe({
      next: (accounts: Account[]) => {
        this.accounts = accounts;
        this.loading = false;
        this.totalRecords = accounts.length;
      },
      error: (err: HttpErrorResponse) => {
        alert(err.message);
      }
    });
  }

  refresh(){
    window.location.reload();
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.accountTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

}
