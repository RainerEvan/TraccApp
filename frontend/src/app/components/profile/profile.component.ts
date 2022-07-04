import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account/account.service';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  account: Account;

  constructor(private accountService:AccountService, private authService: AuthService) { }

  ngOnInit(): void {
    this.getAccount();
  }

  public getAccount():void{
    const accountId = this.authService.accountValue.accountId;

    this.accountService.getAccount(accountId).subscribe({
      next: (account: Account) => {
        this.account = account;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

}
