import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { AccountService } from 'src/app/services/account/account.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  passwordForm: FormGroup;
  
  constructor(public dialogService:DialogService, public ref: DynamicDialogRef, public config: DynamicDialogConfig, private accountService: AccountService, private authService: AuthService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generatePasswordForm();
  }

  public changePassword(): void{
    if(this.passwordForm.valid){
      const formData = new FormData();
      const currentPassword = this.passwordForm.value.currentPassword;
      const newPassword = this.passwordForm.value.newPassword;

      formData.append('currentPassword', currentPassword);
      formData.append('newPassword', newPassword);

      this.accountService.changePassword(formData).subscribe({
        next: (result: any) => {
          console.log(result);
          this.showResultDialog("Success","Password has been updated successfully, please login again","logout");
        },
        error: (error: any) => {
          console.log(error);
          this.showResultDialog("Failed",error,null);
        }
      });
    } 
  }

  generatePasswordForm(){
    this.passwordForm = this.formBuilder.group({
      currentPassword: [null, [Validators.required, Validators.minLength(6)]],
      newPassword: [null, [Validators.required, Validators.minLength(6)]],
    });
  }

  get currentPassword(){
    return this.passwordForm.get('currentPassword');
  }

  get newPassword(){
    return this.passwordForm.get('newPassword');
  }

  resetForm(form: FormGroup){
    form.reset();
  }

  showResultDialog(title:string, message:string, action: string){
    this.ref = this.dialogService.open(ResultDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "500px", "overflow": "auto"},
      width:'30vw'
    });

     this.ref.onClose.subscribe(() =>{
      if (action == 'logout') {
        this.authService.logout();
      } 
    });
  }
}
