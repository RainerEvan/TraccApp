import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Roles } from 'src/app/models/roles';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private apollo: Apollo) { }

  public getAllRoles(): Observable<Roles[]>{
    return this.apollo.watchQuery<any>({
      query:gql`
        query getAllRoles{
          getAllRoles{
            id
            name
          }
        }
      `,
    })
      .valueChanges.pipe(map((result)=>result.data.getAllRoles));
  }
}
