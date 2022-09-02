import { Component, OnInit } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/compat/messaging';
import { MessageService } from 'primeng/api';
import { BehaviorSubject } from 'rxjs';
import { AuthDetails } from 'src/app/models/authdetails';
import { FcmSubscriptions } from 'src/app/models/fcmsubscriptions';
import { AuthService } from 'src/app/services/auth/auth.service';
import { FcmsubscriptionService } from 'src/app/services/fcmsubscription/fcmsubscription.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  account: AuthDetails;
  currentMessage = new BehaviorSubject(null);
  message:any;
  isTokenExist: boolean;

  constructor(private angularFireMessaging: AngularFireMessaging,private authService:AuthService, private fcmSubscriptionService: FcmsubscriptionService, private messageService:MessageService){}

  ngOnInit(): void {
    this.account = this.authService.accountValue;
    
    if (this.account){
      this.getAccountFcmToken(this.account.accountId);
      this.receiveMessage();
      this.message = this.currentMessage;
    }
  }
  
  requestPermission(fcmSubscriptions:FcmSubscriptions[]) {
    this.angularFireMessaging.requestToken.subscribe({
      next: (token:any) => {
        console.log("Notification permission granted!", token);

        if(!this.checkToken(fcmSubscriptions,token)){
          this.saveToken(token);
        }
      },
      error: (error:any) => {
        console.error('Unable to get permission',error);
      }
    });
  }

  receiveMessage(){
    this.angularFireMessaging.messages.subscribe({
      next:(payload:any) => {
        console.log("New message received ", payload);
        this.currentMessage.next(payload);
      }
    })
  }

  saveToken(token:any){
    this.fcmSubscriptionService.addFcmSubscriptions(token).subscribe({
      next:(response:any) => {
        console.log("Fcm token saved successfully", response);
      },
      error: (error:any) => {
        console.log(error);
      }
    })
  }

  checkToken(fcmSubscriptions:any[], token:any):boolean{
    console.log(fcmSubscriptions);
    if(token){
      if(fcmSubscriptions.some(fcmToken => token.includes(fcmToken.token))){
        return true;
      } else{
        return false;
      }
    }

    return true;
  }

  getAccountFcmToken(accountId:string){
    this.fcmSubscriptionService.getAllFcmSubscriptionsForAccount(accountId).subscribe({
      next: (fcm:FcmSubscriptions[]) => {
        this.requestPermission(fcm);
        console.log("Fcm Token Retrieved!");
      },
      error: (error:any) => {
        console.log(error);
      }
    });
  }
}
