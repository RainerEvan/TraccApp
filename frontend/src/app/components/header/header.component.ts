import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { MessagingService } from 'src/app/services/messaging/messaging.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  accountId:string;

  constructor(private messagingService:MessagingService, private authService: AuthService) { }

  ngOnInit(): void {
    this.accountId = this.authService.accountValue.accountId;
    this.messagingService.requestPermission(this.accountId);
    this.messagingService.receiveMessage();
  }
}
