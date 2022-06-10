import { Component, OnInit } from '@angular/core';
import { Apollo, gql} from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Ticket } from 'src/app/types/ticket';

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css']
})
export class TicketsComponent implements OnInit {
  public tickets: Observable<Ticket[]>;

  constructor(private appolo: Apollo) { }

  ngOnInit() {
    this.tickets = this.appolo.watchQuery<any>({
      query:gql`
        query{
          getAllTickets{
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
      .valueChanges.pipe(map((result)=>result.data.getAllTickets));
  }
}
