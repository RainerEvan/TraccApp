import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Notifications } from 'src/app/models/notifications';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notification-detail',
  templateUrl: './notification-detail.component.html',
  styleUrls: ['./notification-detail.component.css']
})
export class NotificationDetailComponent implements OnInit {

  loading: boolean;
  notification: Notifications;

  constructor(private route: ActivatedRoute, private notificationService: NotificationService) { }

  ngOnInit(): void {
    this.getNotification();
  }

  public getNotification():void{
    const notificationId = this.route.snapshot.paramMap.get('id');

    if(notificationId){
      this.loading = true;
      this.notificationService.getNotification(notificationId).subscribe({
        next: (notification: Notifications) => {
          this.loading = false;
          this.notification = notification;
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }
}
