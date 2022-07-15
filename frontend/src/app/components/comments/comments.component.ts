import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Comments } from 'src/app/models/comments';
import { CommentService } from 'src/app/services/comment/comment.service';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {

  ticketId = this.route.snapshot.paramMap.get('id');
  comments: Comments[];
  commentForm: FormGroup;
  isCommentFormSubmitted: boolean = false;
  loading: boolean;
  totalRecords: number = 0;

  constructor(private route:ActivatedRoute, private commentService:CommentService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.getAllComments();
    this.generateCommentForm();
  }

  public getAllComments(){
    this.loading = true;
    this.commentService.getAllCommentsForTicket(this.ticketId).subscribe({
      next: (comments: Comments[]) => {
        this.comments = comments;
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

  generateCommentForm(){
    this.commentForm = this.formBuilder.group({
      body: [null, [Validators.required]],
    });
  }

  get body(){
    return this.commentForm.get('body');
  }
}
