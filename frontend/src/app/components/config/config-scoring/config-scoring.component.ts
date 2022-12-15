import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Scorings } from 'src/app/models/scorings';
import { ScoringService } from 'src/app/services/scoring/scoring.service';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';

@Component({
  selector: 'app-config-scoring',
  templateUrl: './config-scoring.component.html',
  styleUrls: ['./config-scoring.component.css']
})
export class ConfigScoringComponent implements OnInit {

  scoring:Scorings;
  loading:boolean;
  scoringForm:FormGroup;
  isScoringFormSubmitted: boolean = false;
  ref: DynamicDialogRef;

  constructor(public dialogService:DialogService, private scoringService:ScoringService, private formBuilder:FormBuilder) { }

  ngOnInit(): void {
    this.getScoring();
    this.generateScoringForm();
  }

  public getScoring(){
    this.loading = true;
    this.scoringService.getScoring().subscribe({
      next: (scoring: Scorings) => {
        this.loading = false;
        this.scoring = scoring;
      },
      error: (error: any) => {
        console.log(error);
      }
    })
  }

  generateScoringForm(){
    this.scoringForm = this.formBuilder.group({
      ticketPoint: [0],
      ticketSLA: [0],
      maxTicketSLA: [0],
      ticketReassignedDeduction: [0],
    });
  }

  editScoring(){
    const scoringId = this.scoring.id;
    const scoring = this.scoringForm.value;

    this.scoringService.editSCoring(scoringId,scoring).subscribe({
      next: (result: any) => {
        console.log(result);
        this.isScoringFormSubmitted = true;
        this.getScoring();
        this.showResultDialog("Success","Scoring has been updated successfully");
      },
      error: (error: any) => {
        console.log(error);
        this.isScoringFormSubmitted = false;
        this.showResultDialog("Failed","There was a problem, try again later");
      }
    });
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
