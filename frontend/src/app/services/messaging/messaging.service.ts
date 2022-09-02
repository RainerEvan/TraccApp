import { Injectable } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/compat/messaging';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessagingService {
  currentMessage = new BehaviorSubject(null);

  constructor(private angularFireMessaging: AngularFireMessaging) { }

  requestPermission() {
    this.angularFireMessaging.requestToken.subscribe(
    (token) => {
    console.log(token);
    },
    (err) => {
    console.error('Unable to get permission to notify.', err);
    }
    );
    }
    receiveMessage() {
    this.angularFireMessaging.messages.subscribe(
    (payload:any) => {
    console.log("new message received. ", payload);
    this.currentMessage.next(payload);
    })
    }
}
