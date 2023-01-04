import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { FcmSubscriptions } from 'src/app/models/fcmsubscriptions';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/fcmsubscriptions';

@Injectable({
  providedIn: 'root'
})
export class FcmsubscriptionService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllFcmSubscriptionsForAccount(accountId: string): Observable<FcmSubscriptions[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllFcmSubscriptionsForAccount($accountId:ID!){
          getAllFcmSubscriptionsForAccount(accountId: $accountId){
            id
            token
            timestamp
          }
        }
      `,
      variables: {
        accountId: accountId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getAllFcmSubscriptionsForAccount));
  }

  public addFcmSubscriptions(token:string): Observable<any>{
    const params = new HttpParams().set('token',token);
    return this.http.post(API_URL+'/add',null,{params:params});
  }

}
