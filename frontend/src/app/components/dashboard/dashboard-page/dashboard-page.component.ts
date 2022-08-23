import { Component, OnInit } from '@angular/core';
import { MessagingService } from 'src/app/services/messaging/messaging.service';

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.css']
})
export class DashboardPageComponent implements OnInit {

  message: any;

  constructor(private messagingService:MessagingService) { }

  ngOnInit(): void {
    this.messagingService.requestPermission();
    this.messagingService.receiveMessage();
    this.message = this.messagingService.currentMessage;
  }

}
