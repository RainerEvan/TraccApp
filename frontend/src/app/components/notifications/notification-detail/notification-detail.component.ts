import { Component, Input, OnInit } from '@angular/core';
import { Notifications } from 'src/app/models/notifications';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notification-detail',
  templateUrl: './notification-detail.component.html',
  styleUrls: ['./notification-detail.component.css']
})
export class NotificationDetailComponent implements OnInit {

  @Input() notification?: Notifications;

  constructor() { }

  ngOnInit(): void {
  }

}
