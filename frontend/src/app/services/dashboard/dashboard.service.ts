import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { DashboardActivity, DashboardAnalytics } from 'src/app/models/dashboard';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private apollo: Apollo) { }

  public getDashboardActivity(): Observable<DashboardActivity[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getDashboardActivity{
          getDashboardActivity{
            menu
            period
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

  public getDashboardAnalytics(): Observable<DashboardAnalytics[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getDashboardAnalytics{
          getDashboardAnalytics{
            period
            minTickets
            maxTickets
            avgTickets
            totalTickets
            label
            data
            topApplications{
              application
              count
            }
            topTags{
              tag
              count
            }
            ticketRate{
              label
              rate
            }
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getDashboardAnalytics));
  }
}
