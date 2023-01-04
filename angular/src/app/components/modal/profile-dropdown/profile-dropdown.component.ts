import { Component, OnDestroy, OnInit } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { AuthDetails } from 'src/app/models/authdetails';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-profile-dropdown',
  templateUrl: './profile-dropdown.component.html',
  styleUrls: ['./profile-dropdown.component.css']
})
export class ProfileDropdownComponent implements OnInit, OnDestroy {
  username: String;
  ref: DynamicDialogRef;

  constructor(public dialogService:DialogService, private authService: AuthService) { }

  ngOnInit(): void {
    this.username = this.authService.accountValue.username;
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  showConfirmationDialog(title:string, message:string, action:string){
    this.ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((confirm:boolean) =>{
        if (confirm) {
          if(action == 'logout'){
            this.authService.logout();
          }
        }
    });
  }
}
