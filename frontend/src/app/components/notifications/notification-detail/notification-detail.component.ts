import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { cloneDeep } from '@apollo/client/utilities';
import { Notifications } from 'src/app/models/notifications';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notification-detail',
  templateUrl: './notification-detail.component.html',
  styleUrls: ['./notification-detail.component.css']
})
export class NotificationDetailComponent implements OnInit {

  notification:Notifications;
  loading:boolean;
  data:any;

  constructor(private route:ActivatedRoute, private notificationService:NotificationService) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.getNotification(params['id']);
    });
  }

  public getNotification(notificationId:string): void{
    if(notificationId){
      this.loading = true;
      this.notificationService.getNotification(notificationId).subscribe({
        next: (notification: Notifications) => {
          this.notification = notification;
          this.data = JSON.parse(this.notification.data);
          this.loading = false;
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }

}
