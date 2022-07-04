import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Division } from 'src/app/models/division';

@Injectable({
  providedIn: 'root'
})
export class DivisionService {

  constructor(private apollo: Apollo) { }

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
}
