import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo,gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Ticket } from 'src/app/types/ticket';

const apiServerUrl = 'http://localhost:8080/api/tickets';
const headers = new HttpHeaders({
  'Authorization': `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYW1hbiIsImlhdCI6MTY1NTg2ODQ5NSwiZXhwIjoxNjU1OTU0ODk1fQ.5DzXJUij-W2HcTFYkLGSdFKdEf2SDl4pVR5XIkUSDUHwTyY8h4plnXyXm-arjW3cxxiodhjbYOaF8N4EhiyzOA`
});

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllTickets(): Observable<Ticket[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllTickets{
          getAllTickets{
            ticketId
            ticketNo
            application{
              name
            }
            title
            reporter{
              fullname
            }
            dateAdded
            support{
              dateTaken
              developer{
                fullname
              }
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

  public getTicket(ticketId: string): Observable<Ticket>{
    return this.apollo.watchQuery<any>({
      query: gql`
        query getTicket($ticketId:ID!){
          getTicket(ticketId: $ticketId){
            ticketId
            ticketNo
            application{
              name
            }
            dateAdded
            reporter{
              fullname
            }
            title
            description
            dateClosed
            status{
              name
            }
            support{
              dateTaken
              developer{
                fullname
              }
              result
              description
              tags{
                name
              }
              devNote
              isActive
              attachments{
                fileBase64
                fileName
              }
            }
            attachments{
              fileBase64
              fileName
            }
          }
        }
      `, 
      variables: {
        ticketId: ticketId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getTicket));
  }

  public addTicket(formData: FormData): Observable<any>{
    return this.http.post<any>(apiServerUrl+'/add',formData,{headers: headers});
  }

}
