import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo,gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Support } from 'src/app/models/support';
import { Ticket } from 'src/app/models/ticket';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/tickets';

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

  public getAllTicketsForUser(accountId:string): Observable<Ticket[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllTicketsForUser($accountId:ID!){
          getAllTicketsForUser(accountId: $accountId){
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
      variables: {
        accountId: accountId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getAllTicketsForUser));
  }

  public getAllSupportsForDeveloper(accountId:string): Observable<Support[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllSupportsForDeveloper($accountId:ID!){
          getAllSupportsForDeveloper(accountId: $accountId){
            dateTaken
            developer{
              fullname
            }
            ticket{
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
              status{
                name
              }
            }
          }
        }
      `,
      variables: {
        accountId: accountId,
      },
    })
      .valueChanges.pipe(map((result)=>result.data.getAllSupportsForDeveloper));
  }

  public addTicket(formData: FormData): Observable<any>{
    return this.http.post(API_URL+'/add',formData);
  }

  public closeTicket(ticketId: string): Observable<any>{
    const params = new HttpParams().set('ticketId',ticketId);
    return this.http.put(API_URL+'/close',null,{params:params});
  }

  public addSupport(ticketId: string): Observable<any>{
    const params = new HttpParams().set('ticketId',ticketId);
    return this.http.post(API_URL+'/supports/add',null,{params:params});
  }

  public solveSupport(formData: FormData): Observable<any>{
    return this.http.put(API_URL+'/supports/solve',formData);
  }

}
