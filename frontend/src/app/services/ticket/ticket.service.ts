import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo,gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Ticket } from 'src/app/types/ticket';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/tickets';
const headers = new HttpHeaders({
  'Authorization': `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYW1hbiIsImlhdCI6MTY1NTk2MDM0NiwiZXhwIjoxNjU2MDQ2NzQ2fQ.YlL_TAtK0ZgcxxdLnoIxDFBAkzA0WdCHyVnv-hCmKI6Q7rVUoKptxYpO1JLbOQRBuiX-T45joGCMKFm_AQbbCA`
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
    return this.http.post(API_URL+'/add',formData,{headers: headers, responseType:"text"});
  }

  public addSupport(ticketId: string): Observable<any>{
    
    return this.http.post(API_URL+'/supports/add',{"ticketId":ticketId},{headers: headers, responseType:"text"});
  }

}
