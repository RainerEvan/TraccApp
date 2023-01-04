import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Scorings } from 'src/app/models/scorings';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/scorings';

@Injectable({
  providedIn: 'root'
})
export class ScoringService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getScoring(): Observable<Scorings>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getScoring{
          getScoring{
            id
            ticketPoint
            ticketSLA
            maxTicketSLA
            ticketReassignedDeduction
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getScoring));
  }

  public editSCoring(scoringId: string, formData: FormData): Observable<any>{
    const params = new HttpParams().set('scoringId',scoringId);
    return this.http.put(API_URL+'/edit',formData,{params:params});
  }
}
