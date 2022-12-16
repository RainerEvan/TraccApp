import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Comments } from 'src/app/models/comments';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/comments';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllCommentsForTicket(ticketId: string): Observable<Comments[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllCommentsForTicket($ticketId:ID!){
          getAllCommentsForTicket(ticketId: $ticketId){
            id
            author{
              id
              fullname
            }
            createdAt
            updatedAt
            body
          }
        }
      `,
      variables: {
        ticketId: ticketId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getAllCommentsForTicket));
  }

  public addComment(formData:any): Observable<any>{
    return this.http.post(API_URL+'/add',formData);
  }

  public editComment(commentId: string, body: string): Observable<any>{
    const params = new HttpParams().set('commentId',commentId);
    return this.http.put(API_URL+'/edit',body,{params:params});
  }

  public deleteComment(commentId: string): Observable<any>{
    const params = new HttpParams().set('commentId',commentId);
    return this.http.put(API_URL+'/delete',null,{params:params});
  }
}
