import { Directive, Input, OnInit, TemplateRef, ViewContainerRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

@Directive({
  selector: '[appTicketStatus]'
})
export class TicketStatusDirective implements OnInit{
  @Input('appTicketStatus') appTicketStatus: string;
  @Input('appTicketStatusNow') appTicketStatusNow: any;

  isVisible = false;

  constructor(private route: ActivatedRoute, private viewContainerRef: ViewContainerRef, private templateRef: TemplateRef<any>) {}

  ngOnInit():void {
    console.log(this.appTicketStatus);
    console.log(this.appTicketStatusNow);

    if (!this.appTicketStatusNow) {
      this.viewContainerRef.clear();
    }
    
    if (this.appTicketStatus.includes(this.appTicketStatusNow)) {
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
