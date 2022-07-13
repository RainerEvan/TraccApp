import { Component, OnInit, ViewChild } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { AddTicketComponent } from '../../tickets/add-ticket/add-ticket.component';
import { Division } from 'src/app/models/division';
import { DivisionService } from 'src/app/services/division/division.service';

@Component({
  selector: 'app-config-division',
  templateUrl: './config-division.component.html',
  styleUrls: ['./config-division.component.css']
})
export class ConfigDivisionComponent implements OnInit {

  divisions: Division[];
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
    this.divisionService.getAllDivisions().subscribe({
      next: (divisions: Division[]) => {
        this.divisions = divisions;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.divisionTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  showAddTicketDialog(){
    this.ref = this.dialogService.open(AddTicketComponent, {
      header: "Add Ticket",
      footer: " ",
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px", "overflow": "auto"},
      width:'40vw',
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAllDivisions();
      } 
    });
  }

}
