import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Application } from 'src/app/types/application';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  constructor(private apollo: Apollo) { }

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
}
