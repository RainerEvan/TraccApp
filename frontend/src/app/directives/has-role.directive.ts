import { Directive, Input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { AuthService } from '../services/auth/auth.service';

@Directive({
  selector: '[appHasRole]'
})
export class HasRoleDirective {

  @Input('appHasRole') appHasRole: string[];

  isVisible = false;

  constructor(private viewContainerRef: ViewContainerRef, private templateRef: TemplateRef<any>, private authService: AuthService) {}

  ngOnInit():void {
    const roles: any[]= this.authService.accountValue.roles;

    if (!roles) {
      this.viewContainerRef.clear();
    }
    
    if (roles.some(role => this.appHasRole.includes(role))) {
      if (!this.isVisible) {
        this.isVisible = true;
        this.viewContainerRef.createEmbeddedView(this.templateRef);
      }
    } else {
      this.isVisible = false;
      this.viewContainerRef.clear();
    }
  }
}
