import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Division } from 'src/app/models/division';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/divisions';

@Injectable({
  providedIn: 'root'
})
export class DivisionService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllDivisions(): Observable<Division[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllDivisions{
          getAllDivisions{
            id
            name
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllDivisions));
  }

  public addDivision(name: string): Observable<any>{
    return this.http.post(API_URL+'/add',name);
  }

  public deleteDivision(divisionId: string): Observable<any>{
    const params = new HttpParams().set('divisionId',divisionId);
    return this.http.delete(API_URL+'/delete',{params:params});
  }
}
