import { Injectable } from '@angular/core';
import { Apollo, gql } from 'apollo-angular';
import { map, Observable } from 'rxjs';
import { Role } from 'src/app/models/role';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private apollo: Apollo) { }

  public getAllRoles(): Observable<Role[]>{
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
