import { Component, HostListener, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { TagsService } from 'src/app/services/tags/tags.service';
import { TicketSupportService } from 'src/app/services/ticket-support/ticket-support.service';
import { Tags } from 'src/app/types/tags';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';

@Component({
  selector: 'app-solve-ticket',
  templateUrl: './solve-ticket.component.html',
  styleUrls: ['./solve-ticket.component.css']
})
export class SolveTicketComponent implements OnInit {

  solveForm: FormGroup;
  supportId: string;
  isSolveFormSubmitted: boolean = false;
  tags: Tags[];
  filteredTags: any;
  supportAttachments: File[]=[];
  fileDropArea:string;
  
  constructor(public dialogService:DialogService, public ref: DynamicDialogRef, public config: DynamicDialogConfig, private ticketSupportService: TicketSupportService, private tagsService:TagsService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generateSolveForm();
    this.supportId=this.config.data.supportId;
  }

  public getAllTags():void{
    this.tagsService.getAllTags().subscribe({
      next: (tags: Tags[]) => {
        this.tags = tags;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  public solveSupport():void{
    if(this.solveForm.valid){
      const formData = new FormData();
      const support = this.solveForm.value;
      console.log(JSON.stringify(support));

      for(var i=0;i<this.supportAttachments.length;i++){
        formData.append('files',this.supportAttachments[i]);
      }
      formData.append('supportId', new Blob([JSON.stringify(this.supportId)], {type:"application/json"}));
      formData.append('support', new Blob([JSON.stringify(support)], {type:"application/json"}));

      this.ticketSupportService.solveSupport(formData).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isSolveFormSubmitted = true;
          this.ref.close(this.isSolveFormSubmitted);
          this.showResultDialog("Success","Ticket has been updated successfully");
        },
        error: (error: any) => {
          console.log(error);
          this.isSolveFormSubmitted = false;
          this.ref.close(this.isSolveFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateSolveForm(){
    this.solveForm = this.formBuilder.group({
      result: [null, [Validators.required]],
      description: [null, [Validators.required]],
      tagsName: [null, [Validators.required]],
      devNote: [null, [Validators.required]],
    });
    this.getAllTags();
  }

  get result(){
    return this.solveForm.get('result');
  }

  get description(){
    return this.solveForm.get('description');
  }

  get tagsName(){
    return this.solveForm.get('tagsName');
  }

  get devNote(){
    return this.solveForm.get('devNote');
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
      this.supportAttachments.push(event.dataTransfer.files[0]);
    }
  }

  onSelectFile(event:any){
    for  (var i =  0; i <  event.target.files.length; i++)  {  
      this.supportAttachments.push(event.target.files[i]);
    }
  }

  removeFile(index:any){
    this.supportAttachments.splice(index,1);
  }

  filterTags(event:any) {
    let filtered : any[] = [];
    let query = event.query;

    for(let i = 0; i < this.tags.length; i++) {
        let tag = this.tags[i];
        if (tag.name.toLowerCase().indexOf(query.toLowerCase()) == 0) {
            filtered.push(tag.name);
        }
    }

    this.filteredTags = filtered;
  }

  resetForm(form: FormGroup){
    form.reset();
    this.supportAttachments = [];
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
