import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { Notifications } from 'src/app/models/notifications';
import { AuthService } from 'src/app/services/auth/auth.service';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notification-page',
  templateUrl: './notification-page.component.html',
  styleUrls: ['./notification-page.component.css']
})
export class NotificationPageComponent implements OnInit {

  accountId: string;
  notifications: Notifications[];
  notification: Notifications;
  loading: boolean;
  totalRecords: number = 0;
  totalUnread: number = 0;

  constructor(private notificationService:NotificationService, private authService: AuthService) { }

  ngOnInit(): void {
    this.accountId = this.authService.accountValue.accountId;
    this.getAllNotifications();
  }

  public getAllNotifications(): void{
    this.loading = true;
    this.notificationService.getAllNotificationsForAccount(this.accountId).subscribe({
      next: (notifications: Notifications[]) => {
        this.notifications = cloneDeep(notifications);
        this.loading = false;
        this.totalRecords = notifications.length;
        this.totalUnread = notifications.filter(notification => !notification.readAt).length;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  public getNotification(notificationId:string):void{
    if(notificationId){
      this.loading = true;
      this.notificationService.getNotification(notificationId).subscribe({
        next: (notification: Notifications) => {
          this.loading = false;
          this.notification = notification;
          if(notification.readAt == null){
            this.notificationService.readNotification(notification.id).subscribe({
              next: () => {
                this.getAllNotifications();
              },
              error: (error: any) => {
                console.log(error);
              }
            });
          }
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }
}
