import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'src/app/services/account/account.service';
import { Account } from 'src/app/models/account';

@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit {

  account: Account;
  profileImg: any;

  constructor(private route:ActivatedRoute, private sanitizer:DomSanitizer, private accountService:AccountService) { }

  ngOnInit(): void {
    this.getAccount();
  }

  getAccount():void{
    const accountId = this.route.snapshot.paramMap.get('id');

    this.accountService.getAccount(accountId).subscribe({
      next: (account: Account) => {
        this.account = account;
        this.profileImg = this.sanitizer.bypassSecurityTrustResourceUrl(`data:image/png;base64, ${account.profileImg}`);
      },
      error: (err: HttpErrorResponse) => {
        alert(err.message);
      }
    })
  }
}
