import { Injectable } from '@angular/core';
import { Apollo,gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Ticket } from 'src/app/types/ticket';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private apollo: Apollo) { }

  public getAllTickets(): Observable<Ticket[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query{
          getAllTickets{
            ticketId
            ticketNo
            application{
              name
            }
            title
            reporter{
              username
            }
            dateAdded
            support{
              developer{
                username
              }
              dateTaken
            }
            status{
              name
            }
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllTickets),);
  }
}
