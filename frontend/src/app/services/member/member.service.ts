import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Members } from 'src/app/models/Members';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/members';

@Injectable({
  providedIn: 'root'
})
export class MemberService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllMembersForTeam(teamId:string): Observable<Members[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllMembersForTeam($teamId:ID!){
          getAllMembersForTeam(teamId: $teamId){
            id
            team{
              id
              name
            }
            developer{
              id
              fullname
            }
          }
        }
      `,
      variables: {
        teamId: teamId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getAllMembersForTeam));
  }

  public getFirstMemberForDeveloper(accountId:string): Observable<Members>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getFirstMemberForDeveloper($accountId:ID!){
          getFirstMemberForDeveloper(accountId: $accountId){
            id
            team{
              id
              name
            }
            developer{
              id
              fullname
            }
          }
        }
      `,
      variables: {
        accountId: accountId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getFirstMemberForDeveloper));
  }

  public getMember(memberId: string): Observable<Members>{
    return this.apollo.watchQuery<any>({
      query: gql`
        query getMember($memberId:ID!){
          getMember(memberId: $memberId){
            id
            team{
              id
              name
            }
            developer{
              id
              fullname
            }
          }
        }
      `, 
      variables: {
        memberId: memberId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getMember));
  }

  public addMember(formData: FormData): Observable<any>{
    return this.http.post(API_URL+'/add',formData);
  }

  public deleteMember(memberId: string): Observable<any>{
    const params = new HttpParams().set('memberId',memberId);
    return this.http.delete(API_URL+'/delete',{params:params});
  }
}
