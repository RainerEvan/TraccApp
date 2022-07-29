import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { DashBoardActivity } from 'src/app/models/dashboard';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private apollo: Apollo) { }

  public getDashboardActivity(): Observable<DashBoardActivity[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getDashboardActivity{
          getDashboardActivity{
            menu
            timeframe
            totalPending
            totalInProgress
            totalResolved
            totalDropped
            totalTickets
            label
            data
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getDashboardActivity));
  }
}
