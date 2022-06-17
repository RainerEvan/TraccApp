import { Injectable } from '@angular/core';
import { Apollo,gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Account } from 'src/app/types/account';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private apollo: Apollo) { }

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
              name
            }
            profileImg
            isActive
            roles{
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
}
