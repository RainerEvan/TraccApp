import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Tags } from 'src/app/types/tags';

@Injectable({
  providedIn: 'root'
})
export class TagsService {

  constructor(private apollo: Apollo) { }

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
}
