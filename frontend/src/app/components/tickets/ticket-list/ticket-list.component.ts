import { Component, OnInit, ViewChild } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { Ticket } from 'src/app/models/ticket';
import { AddTicketComponent } from '../add-ticket/add-ticket.component';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  tickets: Ticket[];
  loading: boolean;
  totalRecords: number;
  ref: DynamicDialogRef;
  @ViewChild('ticketTable') ticketTable: Table | undefined;

  constructor(public dialogService:DialogService, private ticketSupportService: TicketSupportService) { }

  ngOnInit() {
    this.getAllTickets();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAllTickets(): void{
    this.loading = true;

    this.ticketSupportService.getAllTickets().subscribe({
      next: (tickets: Ticket[]) => {
        this.tickets = cloneDeep(tickets);
        this.loading = false;
        this.totalRecords = tickets.length;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.ticketTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
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
        this.getAllTickets();
      } 
    });
  }
  
}
