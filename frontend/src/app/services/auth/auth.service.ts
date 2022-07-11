import { HttpClient, HttpHeaderResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { AuthDetails } from 'src/app/models/authdetails';
import { Role } from 'src/app/models/role';
import { environment } from 'src/environments/environment';

const API_URL = environment.apiUrl+'/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private accountSubject: BehaviorSubject<AuthDetails>;
  public account: Observable<AuthDetails>;

  constructor(private router: Router, private http: HttpClient) { 
    this.accountSubject = new BehaviorSubject<AuthDetails>(JSON.parse(sessionStorage.getItem('account')));
    this.account = this.accountSubject.asObservable();
  }

  public get accountValue(): AuthDetails {
    return this.accountSubject.value;
  }

  public login(username:string,password:string): Observable<any>{
    return this.http.post(API_URL+'/login',{username,password}).pipe(
      map((account:any) => {
        sessionStorage.setItem('account', JSON.stringify(account));
        this.accountSubject.next(account);
      })
    );
  }

  public logout(){
    sessionStorage.removeItem('account');
    this.accountSubject.next(null);
    this.router.navigate(['/login']);
  }
}
