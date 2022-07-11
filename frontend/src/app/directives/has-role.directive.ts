import { Directive, Input, OnDestroy, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from '../services/auth/auth.service';

@Directive({
  selector: '[appHasRole]'
})
export class HasRoleDirective implements OnInit, OnDestroy {
  private subscription: Subscription[] = [];
  @Input() public appHasRole: Array<string>;

 /**
   * @param {ViewContainerRef} viewContainerRef 
   * 	-- the location where we need to render the templateRef
   * @param {TemplateRef<any>} templateRef 
   *   -- the templateRef to be potentially rendered
   * @param {AuthService} authService 
   *   -- will give us access to the roles a user has
   */

  constructor(private viewContainerRef: ViewContainerRef, private templateRef: TemplateRef<any>, private authService: AuthService) {}

  ngOnInit():void {
    // this.subscription.push(
    //   this.authService.account.subscribe(acc => {
    //     if (!acc.roles) {
    //       this.viewContainerRef.clear();
    //     }
    //     const idx = acc.roles.findIndex((role) => this.appHasRole.indexOf(role.name) !== -1);
    //     if (idx < 0) {
    //       this.viewContainerRef.clear();
    //     } else {
    //       this.viewContainerRef.createEmbeddedView(this.templateRef);
    //     }
    //   })
    // );
  }
  
  ngOnDestroy():void {
    this.subscription.forEach((subscription: Subscription) => subscription.unsubscribe());
  }

}
