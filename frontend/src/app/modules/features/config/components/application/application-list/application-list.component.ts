import { Component, OnInit, ViewChild } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { Applications } from 'src/app/models/applications';
import { ApplicationService } from 'src/app/services/application/application.service';
import { AddApplicationComponent } from '../add-application/add-application.component';
import { ConfirmationDialogComponent } from 'src/app/modules/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { cloneDeep } from '@apollo/client/utilities';

@Component({
  selector: 'app-application-list',
  templateUrl: './application-list.component.html',
  styleUrls: ['./application-list.component.css']
})
export class ApplicationListComponent implements OnInit {

  applications: Applications[];
  loading: boolean;
  totalRecords: number;
  ref: DynamicDialogRef;
  @ViewChild('applicationTable') applicationTable: Table | undefined;

  constructor(public dialogService:DialogService, private applicationService: ApplicationService) { }

  ngOnInit() {
    this.getAllApplications();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAllApplications(): void{
    this.loading = true;

    this.applicationService.getAllApplications().subscribe({
      next: (applications: Applications[]) => {
        this.applications = cloneDeep(applications);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.applicationTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  public deleteApplication(applicationId: string):void{
    this.applicationService.deleteApplication(applicationId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.showResultDialog("Success","Application has been deleted successfully");
        this.getAllApplications();
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed","There was a problem, try again later");
      }
    });
  }

  showAddApplicationDialog(){
    this.ref = this.dialogService.open(AddApplicationComponent, {
      header: "Add Application",
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"550px", "max-width":"700px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAllApplications();
      } 
    });
  }

  showConfirmationDialog(title:string, message:string, action:string, applicationId:string){
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
            this.deleteApplication(applicationId);
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
