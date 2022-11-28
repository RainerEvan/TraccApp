import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Performance } from 'src/app/models/performance';

@Injectable({
  providedIn: 'root'
})
export class PerformanceService {

  constructor(private apollo:Apollo) { }

  public getPerformanceForDeveloper(accountId:string): Observable<Performance[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getPerformanceForDeveloper($accountId:ID!){
          getPerformanceForDeveloper(accountId: $accountId){
            menu
            period
            totalPending
            totalInProgress
            totalResolved
            totalDropped
            totalTickets
            rate
            label
            data
          }
        }
      `,
      variables: {
        accountId: accountId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getPerformanceForDeveloper));
  }
}
