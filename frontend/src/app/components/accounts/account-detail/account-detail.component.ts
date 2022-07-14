import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'src/app/services/account/account.service';
import { Account } from 'src/app/models/account';
import { environment } from 'src/environments/environment';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { EditAccountComponent } from '../edit-account/edit-account.component';
import { DomSanitizer } from '@angular/platform-browser';

const API_URL = environment.apiUrl+'/accounts';

@Component({
  selector: 'app-account-detail',
  templateUrl: './account-detail.component.html',
  styleUrls: ['./account-detail.component.css']
})
export class AccountDetailComponent implements OnInit {

  loading: boolean;
  account: Account;
  imageUrl: any;
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
      next: (account: Account) => {
        this.loading = false;
        this.account = account;
        this.imageUrl = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,'+account.profileImg);
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
      contentStyle: {"max-height": "650px", "overflow": "auto"},
      width:'40vw',
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAccount();
      } 
    });
  }
}
