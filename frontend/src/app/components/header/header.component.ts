import { Component, OnInit } from '@angular/core';
import { AuthDetails } from 'src/app/models/authdetails';
import { FcmSubscriptions } from 'src/app/models/fcmsubscriptions';
import { AuthService } from 'src/app/services/auth/auth.service';
import { FcmsubscriptionService } from 'src/app/services/fcmsubscription/fcmsubscription.service';
import { MessagingService } from 'src/app/services/messaging/messaging.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  account: AuthDetails;

  constructor(private messagingService:MessagingService, private authService:AuthService, private fcmSubscriptionService: FcmsubscriptionService) { }

  ngOnInit(): void {
    this.account = this.authService.accountValue;
    
    if (this.account){
      this.getAccountFcmToken(this.account.accountId);
      this.messagingService.receiveMessage();
    }
  }

  getAccountFcmToken(accountId:string){
    this.fcmSubscriptionService.getAllFcmSubscriptionsForAccount(accountId).subscribe({
      next: (fcm:FcmSubscriptions[]) => {
        this.messagingService.requestPermission(fcm);
        console.log("Fcm Token Retrieved!");
      },
      error: (error:any) => {
        console.log(error);
      }
    });
  }
}
