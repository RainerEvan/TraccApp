import { Component, HostListener, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ApplicationService } from 'src/app/services/application/application.service';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { Application } from 'src/app/models/application';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';

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
  fileDropArea:string;
  
  constructor(public dialogService:DialogService, public ref: DynamicDialogRef, public config: DynamicDialogConfig, private ticketSupportService: TicketSupportService, private applicationService: ApplicationService, private formBuilder: FormBuilder) { }

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

  public addTicket(): void{
    if(this.ticketForm.valid){
      const formData = new FormData();
      const ticket = this.ticketForm.value;

      for(var i=0;i<this.ticketAttachments.length;i++){
        formData.append('files',this.ticketAttachments[i]);
      }
      formData.append('ticket', new Blob([JSON.stringify(ticket)], {type:"application/json"}));
      
      this.ticketSupportService.addTicket(formData).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isTicketFormSubmitted = true;
          this.ref.close(this.isTicketFormSubmitted);
          this.showResultDialog("Success","Ticket has been added successfully");
        },
        error: (error: any) => {
          console.log(error);
          this.isTicketFormSubmitted = false;
          this.ref.close(this.isTicketFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
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

  @HostListener("dragover", ["$event"]) onDragOver(event: any) {
    this.fileDropArea = "drag-area";
    event.preventDefault();
  }
  @HostListener("dragleave", ["$event"]) onDragLeave(event: any) {
    this.fileDropArea = "";
    event.preventDefault();
  }
  @HostListener("drop", ["$event"]) onDrop(event: any) {
    this.fileDropArea = "";
    event.preventDefault();
    event.stopPropagation();
    if (event.dataTransfer.files) {
      this.ticketAttachments.push(event.dataTransfer.files[0]);
    }
  }

  onSelectFile(event:any){
    for  (var i =  0; i <  event.target.files.length; i++)  {  
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
