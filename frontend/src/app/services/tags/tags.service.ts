import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Tags } from 'src/app/models/tags';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/tags';

@Injectable({
  providedIn: 'root'
})
export class TagsService {

  constructor(private apollo: Apollo, private http: HttpClient) { }

  public getAllTags(): Observable<Tags[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllTags{
          getAllTags{
            id
            name
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllTags));
  }

  public addTag(name: string): Observable<any>{
    return this.http.post(API_URL+'/add',name);
  }

  public deleteTag(tagId: string): Observable<any>{
    const params = new HttpParams().set('tagId',tagId);
    return this.http.delete(API_URL+'/delete',{params:params});
  }
}
