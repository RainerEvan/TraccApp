import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Application } from 'src/app/models/application';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/applications';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllApplications(): Observable<Application[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllApplications{
          getAllApplications{
            id
            name
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllApplications));
  }

  public addApplication(name: string): Observable<any>{
    return this.http.post(API_URL+'/add',name);
  }

  public deleteApplication(applicationId: string): Observable<any>{
    const params = new HttpParams().set('applicationId',applicationId);
    return this.http.delete(API_URL+'/delete',{params:params});
  }
}
