import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo,gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Account } from 'src/app/models/account';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/accounts';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllAccounts(): Observable<Account[]>{
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
            isActive
            roles{
              name
            }           
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllAccounts));
  }

  public getAccount(accountId: string): Observable<Account>{
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
}
