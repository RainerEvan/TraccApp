import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { cloneDeep } from '@apollo/client/utilities';
import { Table } from 'primeng/table';
import { ApplicationService } from 'src/app/services/application/application.service';
import { TicketService } from 'src/app/services/ticket/ticket.service';
import { Application } from 'src/app/types/application';
import { Ticket } from 'src/app/types/ticket';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {

  tickets: Ticket[];
  loading: boolean;
  totalRecords: number;
  displayAddTicketModal: boolean = false;
  displayAddTicketStatus: boolean = false;
  ticketForm: FormGroup;
  isTicketFormSubmitted: boolean = false;
  applications: Application[];
  ticketAttachments: File[]=[];
  @ViewChild('ticketTable') ticketTable: Table | undefined;

  constructor(private formBuilder: FormBuilder, private ticketService: TicketService, private applicationService: ApplicationService) { }

  ngOnInit() {
    this.getAllTickets();
    this.generateTicketForm();
  }

  public getAllTickets(): void{
    this.loading = true;

    this.ticketService.getAllTickets().subscribe({
      next: (tickets: Ticket[]) => {
        this.tickets = cloneDeep(tickets);
        this.loading = false;
        this.totalRecords = tickets.length;
      },
      error: (err: HttpErrorResponse) => {
        alert(err.message);
      }
    });
  }

  public getAllApplications(): void{
    this.applicationService.getAllApplications().subscribe({
      next: (applications: Application[]) => {
        this.applications = applications;
      },
      error: (err: HttpErrorResponse) => {
        alert(err.message);
      }
    });
  }

  public addTicket(): void{
    if(this.ticketForm.valid){
      const formData = new FormData();
      const ticket = this.ticketForm.value;

      for(var i=0;i<this.ticketAttachments.length;i++){
        formData.append('files',this.ticketAttachments[i]);
      }
      formData.append('ticket', new Blob([JSON.stringify(ticket)], {type:"application/json"}));
      
      this.ticketService.addTicket(formData).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isTicketFormSubmitted = true;
          this.displayAddTicketModal = false;
        },
        error: (error: any) => {
          console.log(error);
          this.isTicketFormSubmitted = false;
        }
      });
      this.displayAddTicketStatus = true;
    } else{
      return;
    }
    
  }

  refresh(){
    window.location.reload();
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.ticketTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  generateTicketForm(){
    this.ticketForm = this.formBuilder.group({
      applicationId: [null, [Validators.required]],
      title: [null, [Validators.required]],
      description: [null, [Validators.required]],
    });
    this.getAllApplications();
  }

  get applicationId(){
    return this.ticketForm.get('applicationId');
  }

  get title(){
    return this.ticketForm.get('title');
  }

  get description(){
    return this.ticketForm.get('description');
  }

  onSelectFile(event:any){
    for  (var i =  0; i <  event.target.files.length; i++)  {  
      this.ticketAttachments.push(event.target.files[i]);
    }
  }

  resetForm(form: FormGroup){
    form.reset();
    this.ticketAttachments = [];
  }

}
