import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { cloneDeep } from '@apollo/client/utilities';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Comments } from 'src/app/models/comments';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CommentService } from 'src/app/services/comment/comment.service';
import { environment } from 'src/environments/environment';
import { ConfirmationDialogComponent } from '../../modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from '../../modal/result-dialog/result-dialog.component';
import { EditCommentComponent } from '../edit-comment/edit-comment.component';

@Component({
  selector: 'app-comment-list',
  templateUrl: './comment-list.component.html',
  styleUrls: ['./comment-list.component.css']
})
export class CommentListComponent implements OnInit {

  ticketId = this.route.snapshot.paramMap.get('id');
  imageUrl = environment.apiUrl+'/accounts/profileImg/';
  currAuthorId: string;
  comments: Comments[];
  commentForm: FormGroup;
  isCommentFormSubmitted: boolean = false;
  loading: boolean;
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
    this.loading = true;
    this.commentService.getAllCommentsForTicket(this.ticketId).subscribe({
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
    if(this.commentForm.valid){
      const body = this.commentForm.value.body;

      this.commentService.addComment(this.ticketId,body).subscribe({
        next: (result: any) => {
          this.isCommentFormSubmitted = true;
          this.commentForm.reset();
          this.getAllComments();
        },
        error: (error: any) => {
          console.log(error);
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
    this.commentForm = this.formBuilder.group({
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
      contentStyle: {"max-height": "650px", "overflow": "auto"},
      width:'40vw',
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
      contentStyle: {"max-height": "500px", "overflow": "auto"},
      width:'30vw',
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
      contentStyle: {"max-height": "500px", "overflow": "auto"},
      width:'30vw'
    });
  }

}
