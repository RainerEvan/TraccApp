import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Notifications } from 'src/app/models/notifications';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/notifications';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllNotificationsForAccount(accountId: string): Observable<Notifications[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllNotificationsForAccount($accountId:ID!){
          getAllNotificationsForAccount(accountId: $accountId){
            id
            receiver{
              id
              fullname
            }
            createdAt
            readAt
            title
          }
        }
      `,
      variables: {
        accountId: accountId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getAllNotificationsForAccount));
  }

  public getNotification(notificationId: string): Observable<Notifications>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getNotification($notificationId:ID!){
          getNotification(notificationId: $notificationId){
            id
            receiver{
              id
              fullname
            }
            createdAt
            readAt
            title
            body
          }
        }
      `,
      variables: {
        notificationId: notificationId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getNotification));
  }

  public addNotification(formData:FormData): Observable<any>{
    return this.http.post(API_URL+'/add',formData);
  }
}
