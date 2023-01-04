import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { DivisionService } from 'src/app/services/division/division.service';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';

@Component({
  selector: 'app-add-division',
  templateUrl: './add-division.component.html',
  styleUrls: ['./add-division.component.css']
})
export class AddDivisionComponent implements OnInit {

  divisionForm: FormGroup;
  isDivisionFormSubmitted: boolean = false;
  
  constructor(public dialogService:DialogService, public ref: DynamicDialogRef, private divisionService: DivisionService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generateDivisionForm();
  }

  public addDivision(): void{
    if(this.divisionForm.valid){
      const name = this.divisionForm.value.name;

      this.divisionService.addDivision(name).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isDivisionFormSubmitted = true;
          this.ref.close(this.isDivisionFormSubmitted);
          this.showResultDialog("Success","Division has been added successfully");
        },
        error: (error: any) => {
          console.log(error);
          this.isDivisionFormSubmitted = false;
          this.ref.close(this.isDivisionFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateDivisionForm(){
    this.divisionForm = this.formBuilder.group({
      name: [null, [Validators.required]],
    });
  }

  get name(){
    return this.divisionForm.get('name');
  }

  showResultDialog(title:string, message:string){
    this.ref = this.dialogService.open(ResultDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });
  }
}
