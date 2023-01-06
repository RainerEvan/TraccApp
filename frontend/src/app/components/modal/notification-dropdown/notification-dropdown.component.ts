import { Component, OnInit } from '@angular/core';
import { cloneDeep } from '@apollo/client/utilities';
import { Notifications } from 'src/app/models/notifications';
import { AuthService } from 'src/app/services/auth/auth.service';
import { NotificationService } from 'src/app/services/notification/notification.service';

@Component({
  selector: 'app-notification-dropdown',
  templateUrl: './notification-dropdown.component.html',
  styleUrls: ['./notification-dropdown.component.css']
})
export class NotificationDropdownComponent implements OnInit {

  accountId: string;
  notifications: Notifications[];
  loading: boolean;

  constructor(private notificationService:NotificationService, private authService: AuthService) { }

  ngOnInit(): void {
    this.accountId = this.authService.accountValue.accountId;
    this.getTopNotifications();
  }

  public getTopNotifications(): void{
    this.loading = true;
    this.notificationService.getTopNotificationsForAccount(this.accountId).subscribe({
      next: (notifications: Notifications[]) => {
        this.notifications = cloneDeep(notifications);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

}
