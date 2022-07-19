import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo,gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Accounts } from 'src/app/models/accounts';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/accounts';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllAccounts(): Observable<Accounts[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllAccounts{
          getAllAccounts{
            id
            username
            fullname
            email
            division{
              name
            }
            profileImg
            isActive
            contactNo
            roles{
              name
            }           
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllAccounts));
  }

  public getAccount(accountId: string): Observable<Accounts>{
    return this.apollo.watchQuery<any>({
      query: gql`
        query getAccount($accountId:ID!){
          getAccount(accountId: $accountId){
            id
            username
            fullname
            email
            contactNo
            division{
              id
              name
            }
            profileImg
            isActive
            roles{
              id
              name
            }           
          }
        }
      `, 
      variables: {
        accountId: accountId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getAccount));
  }

  public addAccount(formData: FormData): Observable<any>{
    return this.http.post(API_URL+'/add',formData);
  }

  public editAccount(formData: FormData): Observable<any>{
    return this.http.put(API_URL+'/edit',formData);
  }

  public changePassword(formData: FormData): Observable<any>{
    return this.http.put(API_URL+'/changePassword',formData);
  }
}
