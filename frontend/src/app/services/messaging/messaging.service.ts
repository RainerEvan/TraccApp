import { Injectable } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/compat/messaging';
import { BehaviorSubject, take } from 'rxjs';
import { FcmSubscriptions } from 'src/app/models/fcmsubscriptions';
import { Notifications } from 'src/app/models/notifications';
import { AuthService } from '../auth/auth.service';
import { FcmsubscriptionService } from '../fcmsubscription/fcmsubscription.service';

@Injectable({
  providedIn: 'root'
})
export class MessagingService {

  notification: Notifications;
  currentMessage = new BehaviorSubject(null);
  isTokenExist: boolean;

  constructor(private angularFireMessaging: AngularFireMessaging, private fcmSubscriptionService: FcmsubscriptionService){}

  requestPermission(fcmSubscriptions:FcmSubscriptions[]) {
    this.angularFireMessaging.requestToken.subscribe({
      next: (token:any) => {
        console.log("Notification permission granted!", token);
        this.checkToken(fcmSubscriptions,token);

        if(!this.isTokenExist){
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

  checkToken(fcmSubscriptions:any[], token:any){
    this.isTokenExist = false;

    console.log(fcmSubscriptions);
    if(fcmSubscriptions.some(fcmToken => token.includes(fcmToken.token))){
      this.isTokenExist = true;
    }
  }
 
}
