import { Component, OnInit, ViewChild } from '@angular/core';
import { ConfirmationDialogComponent } from 'src/app/modules/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { AddDivisionComponent } from '../add-division/add-division.component';
import { Divisions } from 'src/app/models/divisions';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { DivisionService } from 'src/app/services/division/division.service';
import { cloneDeep } from '@apollo/client/utilities';

@Component({
  selector: 'app-division-list',
  templateUrl: './division-list.component.html',
  styleUrls: ['./division-list.component.css']
})
export class DivisionListComponent implements OnInit {

  divisions: Divisions[];
  loading: boolean;
  totalRecords: number;
  ref: DynamicDialogRef;
  @ViewChild('divisionTable') divisionTable: Table | undefined;

  constructor(public dialogService:DialogService, private divisionService: DivisionService) { }

  ngOnInit() {
    this.getAllDivisions();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAllDivisions(): void{
    this.loading = true;

    this.divisionService.getAllDivisions().subscribe({
      next: (divisions: Divisions[]) => {
        this.divisions = cloneDeep(divisions);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.divisionTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  public deleteDivision(divisionId: string):void{
    this.divisionService.deleteDivision(divisionId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.showResultDialog("Success","Division has been deleted successfully");
        this.getAllDivisions();
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed","There was a problem, try again later");
      }
    });
  }

  showAddDivisionDialog(){
    this.ref = this.dialogService.open(AddDivisionComponent, {
      header: "Add Division",
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"550px", "max-width":"700px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAllDivisions();
      } 
    });
  }

  showConfirmationDialog(title:string, message:string, action:string, divisionId:string){
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
          if(action == 'delete'){
            this.deleteDivision(divisionId);
          }
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
