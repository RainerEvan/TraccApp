import { Component, OnInit, ViewChild } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { Applications } from 'src/app/models/applications';
import { ApplicationService } from 'src/app/services/application/application.service';
import { ConfirmationDialogComponent } from '../../modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';
import { AddApplicationComponent } from '../add-application/add-application.component';

@Component({
  selector: 'app-config-application',
  templateUrl: './config-application.component.html',
  styleUrls: ['./config-application.component.css']
})
export class ConfigApplicationComponent implements OnInit {

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
      contentStyle: {"max-height": "650px", "overflow": "auto"},
      width:'40vw',
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
      contentStyle: {"max-height": "500px", "overflow": "auto"},
      width:'30vw',
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
      contentStyle: {"max-height": "500px", "overflow": "auto"},
      width:'30vw'
    });
  }
}
