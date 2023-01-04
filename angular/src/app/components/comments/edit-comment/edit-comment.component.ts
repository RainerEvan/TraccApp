import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DialogService, DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Comments } from 'src/app/models/comments';
import { CommentService } from 'src/app/services/comment/comment.service';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';

@Component({
  selector: 'app-edit-comment',
  templateUrl: './edit-comment.component.html',
  styleUrls: ['./edit-comment.component.css']
})
export class EditCommentComponent implements OnInit {

  commentData:Comments;
  commentForm:FormGroup;
  isCommentFormSubmitted: boolean = false;

  constructor(public dialogService:DialogService, public ref: DynamicDialogRef, public config: DynamicDialogConfig, private commentService:CommentService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.commentData=this.config.data.commentData;
    this.generateCommentForm();
  }

  public editComment(): void{
    if(this.commentForm.valid){
      const body = this.commentForm.value.body;

      this.commentService.editComment(this.commentData.id,body).subscribe({
        next: (result: any) => {
          this.isCommentFormSubmitted = true;
          this.ref.close(this.isCommentFormSubmitted);
          this.showResultDialog("Success","Comment has been updated successfully");
        },
        error: (error: any) => {
          console.log(error);
          this.isCommentFormSubmitted = false;
          this.ref.close(this.isCommentFormSubmitted);
          this.showResultDialog("Failed","There was a problem, try again later");
        }
      });
    } 
  }

  generateCommentForm(){
    this.commentForm = this.formBuilder.group({
      body: [this.commentData.body, [Validators.required]],
    });
  }

  get body(){
    return this.commentForm.get('body');
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
