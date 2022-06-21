import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  ticketAttachments: File[] = [];

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
        this.tickets = tickets;
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

  showAddTicketModal() {
    this.displayAddTicketModal = true;
  }

  showAddTicketStatus() {
    this.displayAddTicketStatus = true;
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
      attachments: [null]
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

  onSubmitTicket(){
    this.isTicketFormSubmitted = false;
    if(this.ticketForm.valid){
      this.isTicketFormSubmitted = true;
      this.displayAddTicketModal = false;
    } 
    else{
      return;
    }
    
    const formData = new FormData();
    const ticket = this.ticketForm.value;
    console.log(ticket);

    formData.append('files', null);
    formData.append('ticket', JSON.stringify(ticket));
    this.ticketService.addTicket(formData).subscribe({
      next: (data: any) => {
        console.log(data.ticketId);
      },
      error: (error: any) => {
        console.log(error);
      }
    });
    this.displayAddTicketStatus = true;
    this.resetForm(this.ticketForm);
  }

  resetForm(form: FormGroup){
    form.reset();
  }

}
