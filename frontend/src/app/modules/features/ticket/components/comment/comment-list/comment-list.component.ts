import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Comments } from 'src/app/models/comments';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CommentService } from 'src/app/services/comment/comment.service';
import { environment } from 'src/environments/environment';
import { EditCommentComponent } from '../edit-comment/edit-comment.component';
import { ConfirmationDialogComponent } from 'src/app/modules/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.css']
})
export class CommentListComponent implements OnInit {

  imageUrl = environment.apiUrl+'/accounts/profileImg/';
  currAuthorId: string;
  comments: Comments[];
  commentForm: FormGroup;
  isCommentFormSubmitted: boolean = false;
  loading: boolean;
  loadingPost: boolean = false;
  totalRecords: number = 0;
  ref: DynamicDialogRef;

  constructor(public dialogService:DialogService, private route:ActivatedRoute, private commentService:CommentService, private authService: AuthService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.currAuthorId = this.authService.accountValue.accountId;
    this.getAllComments();
    this.generateCommentForm();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAllComments(){
    const ticketId = this.route.snapshot.paramMap.get('id');
    
    this.loading = true;
    this.commentService.getAllCommentsForTicket(ticketId).subscribe({
      next: (comments: Comments[]) => {
        this.comments = cloneDeep(comments);
        this.loading = false;
        this.totalRecords = comments.length;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  public addComment(): void{
    this.loadingPost = true;

    if(this.commentForm.valid){
      const formData = this.commentForm.value;

      this.commentService.addComment(formData).subscribe({
        next: (result: any) => {
          this.loadingPost = false;
          this.isCommentFormSubmitted = true;
          this.generateCommentForm();
          this.getAllComments();
        },
        error: (error: any) => {
          console.log(error);
          this.loadingPost = false;
          this.isCommentFormSubmitted = false;
        }
      });
    } 
  }

  public deleteComment(commentId:string): void{
    this.commentService.deleteComment(commentId).subscribe({
      next: (result: any) => {
        this.showResultDialog("Success","Comment has been deleted successfully");
        this.getAllComments();
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed","There was a problem, try again later");
      }
    });
  }

  generateCommentForm(){
    const ticketId = this.route.snapshot.paramMap.get('id');

    this.commentForm = this.formBuilder.group({
      accountId: [this.authService.accountValue.accountId, [Validators.required]],
      ticketId: [ticketId, [Validators.required]],
      body: [null, [Validators.required]],
    });
  }

  get body(){
    return this.commentForm.get('body');
  }

  showEditCommentDialog(comment:Comments){
    this.ref = this.dialogService.open(EditCommentComponent, {
      header: "Edit Comment",
      footer: " ",
      data:{
        commentData:comment,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"550px", "max-width":"700px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAllComments();
      } 
    });
  }

  showConfirmationDialog(title:string, message:string, action:string, commentId:string){
    this.ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((confirm:boolean) =>{
        if (confirm) {
          if(action == 'delete'){
            this.deleteComment(commentId);
          }
        }
    });
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
