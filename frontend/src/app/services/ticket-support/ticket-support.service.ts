import { HttpClient, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo,gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Supports } from 'src/app/models/supports';
import { Tickets } from 'src/app/models/tickets';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/tickets';

@Injectable({
  providedIn: 'root'
})
export class TicketSupportService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllTickets(): Observable<Tickets[]>{
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

  public getTicket(ticketId: string): Observable<Tickets>{
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
              id
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
                id
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

  public getAllTicketsForUser(accountId:string): Observable<Tickets[]>{
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

  public getAllSupportsForDeveloper(accountId:string): Observable<Supports[]>{
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

  public cancelTicket(ticketId: string): Observable<any>{
    const params = new HttpParams().set('ticketId',ticketId);
    return this.http.delete(API_URL+'/delete',{params:params});
  }

  public requestDropTicket(ticketId: string): Observable<any>{
    const params = new HttpParams().set('ticketId',ticketId);
    return this.http.put(API_URL+'/request-drop',null,{params:params});
  }

  public dropTicket(ticketId: string): Observable<any>{
    const params = new HttpParams().set('ticketId',ticketId);
    return this.http.put(API_URL+'/drop',null,{params:params});
  }

  public reassignTicket(ticketId: string): Observable<any>{
    const params = new HttpParams().set('ticketId',ticketId);
    return this.http.put(API_URL+'/reassign',null,{params:params});
  }

  public addSupport(ticketId: string): Observable<any>{
    const params = new HttpParams().set('ticketId',ticketId);
    return this.http.post(API_URL+'/supports/add',null,{params:params});
  }

  public solveSupport(formData: FormData): Observable<any>{
    return this.http.put(API_URL+'/supports/solve',formData);
  }

}
