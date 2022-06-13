import { Component, OnInit, ViewChild } from '@angular/core';
import { Apollo, gql} from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Ticket } from 'src/app/types/ticket';
import { Table } from 'primeng/table';

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrls: ['./tickets.component.css']
})
export class TicketsComponent implements OnInit {
  public tickets!: Observable<Ticket[]>;

  @ViewChild('ticketTable') ticketTable: Table | undefined;

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

  applyFilterGlobal($event:any, stringVal:any) {
    this.ticketTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }
}
