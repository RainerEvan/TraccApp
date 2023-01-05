import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';

@Component({
  selector: 'app-request-drop-ticket',
  templateUrl: './request-drop-ticket.component.html',
  styleUrls: ['./request-drop-ticket.component.css']
})
export class RequestDropTicketComponent implements OnInit {

  requestDropForm: FormGroup;
  supportId: string;

  constructor(public dialogService:DialogService, public ref: DynamicDialogRef, public config: DynamicDialogConfig, private ticketSupportService: TicketSupportService, private formBuilder: FormBuilder) { }

   ngOnInit(): void {
    this.supportId=this.config.data.supportId;
    this.generateRequestDropForm();
  }

  public requestDropSupport():void{
    if(this.requestDropForm.valid){
      const supportId = this.supportId;
      const support = this.requestDropForm.value;

      this.ticketSupportService.requestDropTicket(supportId, support).subscribe({
        next: (result: any) => {
          console.log(result);
          this.ref.close(support);
          this.showResultDialog("Success","Drop ticket request has been sent to your supervisor");
        },
        error: (error: any) => {
          console.log(error);
          this.ref.close(null);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateRequestDropForm(){
    this.requestDropForm = this.formBuilder.group({
      result: [null, [Validators.required,Validators.maxLength(255)]],
      description: [null, [Validators.required]],
      devNote: [null, [Validators.required]],
    });
  }

  get result(){
    return this.requestDropForm.get('result');
  }
  get description(){
    return this.requestDropForm.get('description');
  }
  get devNote(){
    return this.requestDropForm.get('devNote');
  }

  resetForm(form: FormGroup){
    form.reset();
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
