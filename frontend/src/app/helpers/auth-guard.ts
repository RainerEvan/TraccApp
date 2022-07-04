import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private authService: AuthService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const account = this.authService.accountValue;

    if(account){
        // if (route.data.roles && route.data.roles.indexOf(account.roles) === -1) {
        //     this.router.navigate(['/']);
        //     return false;
        // }

        return true;
    }

    this.router.navigate(['/login'], {queryParams:{returnUrl: state.url}});
    return false;
  }
}
