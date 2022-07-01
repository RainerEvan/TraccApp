import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo,gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Ticket } from 'src/app/types/ticket';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/tickets';
const token = `eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYW1hbiIsImlhdCI6MTY1NjY0MzcyMSwiZXhwIjoxNjU2NzMwMTIxfQ.DX3QxBk0oN_aRUtbqKkYuBjpC7MBSnkB1N9gN_4VZRvVRWG8incUoFGl2Q7HVSI8Rktsk3V7N4m8E3aEz9JN9w`;
const headers = new HttpHeaders({
  'Authorization': `Bearer `+token,
});

@Injectable({
  providedIn: 'root'
})
export class TicketSupportService {

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
              id
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
    return this.http.post(API_URL+'/add',formData,{headers: headers});
  }

  public closeTicket(ticketId: string): Observable<any>{
    const params = new HttpParams().set('ticketId',ticketId);
    return this.http.put(API_URL+'/close',null,{headers: headers, params:params});
  }

  public addSupport(ticketId: string): Observable<any>{
    const params = new HttpParams().set('ticketId',ticketId);
    return this.http.post(API_URL+'/supports/add',null,{headers: headers, params:params});
  }

  public solveSupport(formData: FormData): Observable<any>{
    return this.http.put(API_URL+'/supports/solve',formData,{headers: headers});
  }

}
