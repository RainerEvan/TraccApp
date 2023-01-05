import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Teams } from 'src/app/models/teams';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/teams';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllTeamsForSupervisor(accountId:string): Observable<Teams[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllTeamsForSupervisor($accountId:ID!){
          getAllTeamsForSupervisor(accountId: $accountId){
            id
            name
            supervisor{
              id
              fullname
            }
            members{
              id
            }
          }
        }
      `,
      variables: {
        accountId: accountId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getAllTeamsForSupervisor));
  }

  public getTeam(teamId: string): Observable<Teams>{
    return this.apollo.watchQuery<any>({
      query: gql`
        query getTeam($teamId:ID!){
          getTeam(teamId: $teamId){
            id
            name
            supervisor{
              id
              fullname
            }
            members{
              id
            }
          }
        }
      `, 
      variables: {
        teamId: teamId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getTeam));
  }

  public addTeam(name: string): Observable<any>{
    return this.http.post(API_URL+'/add',name);
  }

  public deleteTeam(teamId: string): Observable<any>{
    const params = new HttpParams().set('teamId',teamId);
    return this.http.delete(API_URL+'/delete',{params:params});
  }
}
