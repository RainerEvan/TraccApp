import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { Observable, map } from 'rxjs';
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
            isActive
            contactNo
            role{
              name
            }           
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllAccounts));
  }

  public getAllDevelopers(): Observable<Accounts[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllDevelopers{
          getAllDevelopers{
            id
            fullname
            division{
              name
            }
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllDevelopers));
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
            isActive
            role{
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
