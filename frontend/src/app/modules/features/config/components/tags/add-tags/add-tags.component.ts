import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { TagsService } from 'src/app/services/tags/tags.service';

@Component({
  selector: 'app-add-tags',
  templateUrl: './add-tags.component.html',
  styleUrls: ['./add-tags.component.css']
})
export class AddTagsComponent implements OnInit {

  tagForm: FormGroup;
  isTagFormSubmitted: boolean = false;
  
  constructor(public dialogService:DialogService, public ref: DynamicDialogRef, private tagService: TagsService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.generateTagForm();
  }

  public addTag(): void{
    if(this.tagForm.valid){
      const name = this.tagForm.value.name;

      this.tagService.addTag(name).subscribe({
        next: (result: any) => {
          console.log(result);
          this.isTagFormSubmitted = true;
          this.ref.close(this.isTagFormSubmitted);
          this.showResultDialog("Success","Tag has been added successfully");
        },
        error: (error: any) => {
          console.log(error);
          this.isTagFormSubmitted = false;
          this.ref.close(this.isTagFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateTagForm(){
    this.tagForm = this.formBuilder.group({
      name: [null, [Validators.required]],
    });
  }

  get name(){
    return this.tagForm.get('name');
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
