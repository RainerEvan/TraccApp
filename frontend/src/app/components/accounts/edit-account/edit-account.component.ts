import { Component, HostListener, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Accounts } from 'src/app/models/accounts';
import { Divisions } from 'src/app/models/divisions';
import { Roles } from 'src/app/models/roles';
import { AccountService } from 'src/app/services/account/account.service';
import { DivisionService } from 'src/app/services/division/division.service';
import { RoleService } from 'src/app/services/role/role.service';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';

@Component({
  selector: 'app-edit-account',
  templateUrl: './edit-account.component.html',
  styleUrls: ['./edit-account.component.css']
})
export class EditAccountComponent implements OnInit {

  accountData:Accounts;
  accountForm: FormGroup;
  isAccountFormSubmitted: boolean = false;
  divisions: Divisions[];
  roles: Roles[];
  profileImg: File;
  imageUrl:any;
  fileDropArea:string;

  constructor(public dialogService:DialogService, public ref: DynamicDialogRef, public config: DynamicDialogConfig, private accountService: AccountService, private divisionService: DivisionService, private roleService: RoleService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.accountData=this.config.data.accountData;
    this.generateAccountForm();
  }

  public getAllDivisions(): void{
    this.divisionService.getAllDivisions().subscribe({
      next: (divisions: Divisions[]) => {
        this.divisions = divisions;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  public getAllRoles(): void{
    this.roleService.getAllRoles().subscribe({
      next: (roles: Roles[]) => {
        this.roles = roles;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  public editAccount(): void{
    if(this.accountForm.valid){
      const formData = new FormData();
      const account = this.accountForm.value;

      formData.append('image',this.profileImg);
      formData.append('accountId', new Blob([JSON.stringify(this.accountData.id)], {type:"application/json"}));
      formData.append('account', new Blob([JSON.stringify(account)], {type:"application/json"}));

      this.accountService.editAccount(formData).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isAccountFormSubmitted = true;
          this.ref.close(this.isAccountFormSubmitted);
          this.showResultDialog("Success","Account has been updated successfully");
        },
        error: (error: any) => {
          console.log(error);
          this.isAccountFormSubmitted = false;
          this.ref.close(this.isAccountFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateAccountForm(){
    this.accountForm = this.formBuilder.group({
      username: [{value:this.accountData.username, disabled:true}],
      fullname: [this.accountData.fullname],
      email: [this.accountData.email],
      contactNo: [this.accountData.contactNo],
      divisionId: [this.accountData.division.id],
      isActive: [this.accountData.isActive],
      rolesName: [this.accountData.roles[0].name],
    });
    this.getAllDivisions();
    this.getAllRoles();
  }

  get username(){
    return this.accountForm.get('username');
  }
  get fullname(){
    return this.accountForm.get('fullname');
  }
  get email(){
    return this.accountForm.get('email');
  }
  get contactNo(){
    return this.accountForm.get('contactNo');
  }
  get divisionId(){
    return this.accountForm.get('divisionId');
  }
  get isActive(){
    return this.accountForm.get('isActive');
  }
  get rolesName(){
    return this.accountForm.get('rolesName');
  }

  @HostListener("dragover", ["$event"]) onDragOver(event: any) {
    this.fileDropArea = "drag-area";
    event.preventDefault();
  }
  @HostListener("dragleave", ["$event"]) onDragLeave(event: any) {
    this.fileDropArea = "";
    event.preventDefault();
  }
  @HostListener("drop", ["$event"]) onDrop(event: any) {
    this.fileDropArea = "";
    event.preventDefault();
    event.stopPropagation();
    if (event.dataTransfer.files) {
      this.profileImg = event.dataTransfer.files[0];
      this.previewImage(this.profileImg);
    }
  }

  onSelectFile(event:any){
    if(event.target.files.length > 0){
      this.profileImg = event.target.files[0];
      this.previewImage(this.profileImg);
    }
  }

  previewImage(image:any){
    var reader = new FileReader();
    reader.readAsDataURL(image);
    reader.onload = (_event) => {
      this.imageUrl = reader.result;
    }
  }

  removeFile(){
    this.profileImg = null;
  }

  resetForm(form: FormGroup){
    this.generateAccountForm();
    this.profileImg = null;
  }

  showResultDialog(title:string, message:string){
    this.ref = this.dialogService.open(ResultDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "500px", "overflow": "auto"},
      width:'30vw'
    });
  }
}
