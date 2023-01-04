import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { TeamService } from 'src/app/services/team/team.service';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';

@Component({
  selector: 'app-add-team',
  templateUrl: './add-team.component.html',
  styleUrls: ['./add-team.component.css']
})
export class AddTeamComponent implements OnInit {

  teamForm: FormGroup;
  isTeamFormSubmitted: boolean = false;
  
  constructor(public dialogService:DialogService, public ref: DynamicDialogRef, private teamService:TeamService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generateTeamForm();
  }

  public addTeam(): void{
    if(this.teamForm.valid){
      const name = this.teamForm.value.name;

      this.teamService.addTeam(name).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isTeamFormSubmitted = true;
          this.ref.close(this.isTeamFormSubmitted);
          this.showResultDialog("Success","Team has been added successfully");
        },
        error: (error: any) => {
          console.log(error);
          this.isTeamFormSubmitted = false;
          this.ref.close(this.isTeamFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateTeamForm(){
    this.teamForm = this.formBuilder.group({
      name: [null, [Validators.required]],
    });
  }

  get name(){
    return this.teamForm.get('name');
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
