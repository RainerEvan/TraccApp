import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Accounts } from 'src/app/models/accounts';
import { AccountService } from 'src/app/services/account/account.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ChangePasswordComponent } from '../change-password/change-password.component';
import { EditProfileComponent } from '../edit-profile/edit-profile.component';

@Component({
  selector: 'app-profile-detail',
  templateUrl: './profile-detail.component.html',
  styleUrls: ['./profile-detail.component.css']
})
export class ProfileDetailComponent implements OnInit {

  loading: boolean;
  account: Accounts;
  imageUrl: any;
  ref: DynamicDialogRef;

  constructor(public dialogService:DialogService, private accountService:AccountService, private sanitizer: DomSanitizer, private authService: AuthService) { }

  ngOnInit(): void {
    this.getAccount();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAccount():void{
    const accountId = this.authService.accountValue.accountId;

    this.loading = true;
    this.accountService.getAccount(accountId).subscribe({
      next: (account: Accounts) => {
        this.loading = false;
        this.account = account;
        this.imageUrl = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,'+account.profileImg);
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  showEditProfileDialog(){
    this.ref = this.dialogService.open(EditProfileComponent, {
      header: "Edit Profile",
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

  showChangePasswordDialog(){
    this.ref = this.dialogService.open(ChangePasswordComponent, {
      header: "Change Password",
      footer: " ",
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px", "overflow": "auto"},
      width:'40vw',
    });
  }
}
