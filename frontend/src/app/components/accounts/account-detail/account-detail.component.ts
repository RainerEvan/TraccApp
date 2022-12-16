import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'src/app/services/account/account.service';
import { Accounts } from 'src/app/models/accounts';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { EditAccountComponent } from '../edit-account/edit-account.component';
import { DomSanitizer } from '@angular/platform-browser';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit {

  loading: boolean;
  account: Accounts;
  imageUrl = environment.apiUrl+'/accounts/profileImg/';
  ref: DynamicDialogRef;

  constructor(public dialogService:DialogService, private route:ActivatedRoute, private accountService:AccountService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.getAccount();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  getAccount():void{
    const accountId = this.route.snapshot.paramMap.get('id');

    this.loading = true;
    this.accountService.getAccount(accountId).subscribe({
      next: (account: Accounts) => {
        this.loading = false;
        this.account = account;
      },
      error: (error: any) => {
        console.log(error);
      }
    })
  }

  showEditAccountDialog(){
    this.ref = this.dialogService.open(EditAccountComponent, {
      header: "Edit Account",
      footer: " ",
      data:{
        accountData:this.account,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"550px", "max-width":"700px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAccount();
      } 
    });
  }
}
