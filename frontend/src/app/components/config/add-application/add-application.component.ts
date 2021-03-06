import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ApplicationService } from 'src/app/services/application/application.service';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';

@Component({
  selector: 'app-add-application',
  templateUrl: './add-application.component.html',
  styleUrls: ['./add-application.component.css']
})
export class AddApplicationComponent implements OnInit {

  applicationForm: FormGroup;
  isApplicationFormSubmitted: boolean = false;
  
  constructor(public dialogService:DialogService, public ref: DynamicDialogRef, public config: DynamicDialogConfig, private applicationService: ApplicationService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generateApplicationForm();
  }

  public addApplication(): void{
    if(this.applicationForm.valid){
      const name = this.applicationForm.value.name;

      this.applicationService.addApplication(name).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isApplicationFormSubmitted = true;
          this.ref.close(this.isApplicationFormSubmitted);
          this.showResultDialog("Success","Application has been added successfully");
        },
        error: (error: any) => {
          console.log(error);
          this.isApplicationFormSubmitted = false;
          this.ref.close(this.isApplicationFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateApplicationForm(){
    this.applicationForm = this.formBuilder.group({
      name: [null, [Validators.required]],
    });
  }

  get name(){
    return this.applicationForm.get('name');
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
