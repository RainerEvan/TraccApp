import { Component, OnInit } from '@angular/core';
import { Notifications } from 'src/app/models/notifications';
import { AuthService } from 'src/app/services/auth/auth.service';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notification-list',
  templateUrl: './notification-list.component.html',
  styleUrls: ['./notification-list.component.css']
})
export class NotificationListComponent implements OnInit {

  accountId: string;
  notifications: Notifications[];
  selected: Notifications;
  notificationData:any;
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
        this.notifications = notifications;
        this.loading = false;
        this.totalRecords = notifications.length;
        this.totalUnread = notifications.filter(notification => !notification.readAt).length;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  onSelect(notification:Notifications){
    if(!notification.readAt){
      this.notificationService.readNotification(notification.id).subscribe({
        next: () => {
          this.getAllNotifications();
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }

}
