import { Component, OnInit, ViewChild } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Support } from 'src/app/models/support';

@Component({
  selector: 'app-my-task-list',
  templateUrl: './my-task-list.component.html',
  styleUrls: ['./my-task-list.component.css']
})
export class MyTaskListComponent implements OnInit {

  accountId: string;
  myTasks: Support[];
  loading: boolean;
  totalRecords: number;
  ref: DynamicDialogRef;
  @ViewChild('ticketTable') ticketTable: Table | undefined;

  constructor(public dialogService:DialogService, private ticketSupportService: TicketSupportService, private authService: AuthService) { }

  ngOnInit() {
    this.accountId = this.authService.accountValue.accountId;
    this.getAllTickets();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAllTickets(): void{
    this.loading = true;
    this.ticketSupportService.getAllSupportsForDeveloper(this.accountId).subscribe({
      next: (supports: Support[]) => {
        this.myTasks = cloneDeep(supports);
        this.loading = false;
        this.totalRecords = supports.length;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.ticketTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

}
