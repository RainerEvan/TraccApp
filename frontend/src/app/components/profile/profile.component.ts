import { Component, OnInit } from '@angular/core';
import { Account } from 'src/app/models/account';
import { AccountService } from 'src/app/services/account/account.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/accounts';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  account: Account;
  loading: boolean = false;
  imageUrl: string;

  constructor(private accountService:AccountService, private authService: AuthService) { }

  ngOnInit(): void {
    this.getAccount();
  }

  public getAccount():void{
    const accountId = this.authService.accountValue.accountId;

    this.loading = true;
    this.accountService.getAccount(accountId).subscribe({
      next: (account: Account) => {
        this.loading = false;
        this.account = account;
        this.imageUrl = API_URL + '/profileImg/' + this.account.id;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

}
