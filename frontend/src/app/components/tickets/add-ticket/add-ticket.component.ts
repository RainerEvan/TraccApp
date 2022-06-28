import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ApplicationService } from 'src/app/services/application/application.service';
import { TicketService } from 'src/app/services/ticket/ticket.service';
import { Application } from 'src/app/types/application';

@Component({
  selector: 'app-add-ticket',
  templateUrl: './add-ticket.component.html',
  styleUrls: ['./add-ticket.component.css']
})
export class AddTicketComponent implements OnInit {

  ticketForm: FormGroup;
  isTicketFormSubmitted: boolean = false;
  applications: Application[];
  ticketAttachments: File[]=[];
  
  constructor(public ref: DynamicDialogRef, public config: DynamicDialogConfig, private ticketService: TicketService, private applicationService: ApplicationService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generateTicketForm();
  }

  public getAllApplications(): void{
    this.applicationService.getAllApplications().subscribe({
      next: (applications: Application[]) => {
        this.applications = applications;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  generateTicketForm(){
    this.ticketForm = this.formBuilder.group({
      applicationId: [null, [Validators.required]],
      title: [null, [Validators.required]],
      description: [null, [Validators.required]],
    });
    this.getAllApplications();
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
          this.ref.close(this.isTicketFormSubmitted);
        },
        error: (error: any) => {
          console.log(error);
          this.isTicketFormSubmitted = false;
          this.ref.close(this.isTicketFormSubmitted);
        }
      });
    } 
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
      console.log(event.target.files[i])
      this.ticketAttachments.push(event.target.files[i]);
    }
  }

  removeFile(index:any){
    this.ticketAttachments.splice(index,1);
  }

  resetForm(form: FormGroup){
    form.reset();
    this.ticketAttachments = [];
  }
}
