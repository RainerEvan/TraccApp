import { Injectable } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { AngularFireDatabase } from '@angular/fire/compat/database';
import { AngularFireMessaging } from '@angular/fire/compat/messaging';
import { BehaviorSubject, take } from 'rxjs';
import { Notifications } from 'src/app/models/notifications';

@Injectable({
  providedIn: 'root'
})
export class MessagingService {

  notification: Notifications;
  currentMessage = new BehaviorSubject(null);
  userToken: any = [];
  token: any;

  constructor(private angularFireMessaging: AngularFireMessaging, private angularFireAuth: AngularFireAuth, private angularFireDatabase: AngularFireDatabase){}

  requestPermission(accountId:string){
    this.angularFireMessaging.requestToken.subscribe({
      next: (token:any) => {
        console.log('Permission granted! Save to the server!',token);
        this.token = token;
        this.updateToken(accountId, token);
      },
      error: (error:any) => {
        console.error('Unable to get permission',error);
      }
    });
  }

  updateToken(accountId:string, token:string){
    this.angularFireAuth.authState.pipe(take(1)).subscribe(
      () => {
        const data = {};
        data[accountId] = token;
        this.angularFireDatabase.object('fcmTokens/').update(data);
      }
    )
  }

  receiveMessage(){
    this.angularFireMessaging.messages.subscribe({
      next:(payload) => {
        console.log("New message received ", payload);
        this.currentMessage.next(payload);
      }
    })
  }
}
