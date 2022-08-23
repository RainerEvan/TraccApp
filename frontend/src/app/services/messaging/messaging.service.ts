import { Injectable } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/compat/messaging';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessagingService {

  currentMessage = new BehaviorSubject(null);

  constructor(private afMessaging: AngularFireMessaging){}

  requestPermission(){
    this.afMessaging.requestToken.subscribe({
      next: (token:any) => {
        console.log('Permission granted! Save to the server!',token);
      },
      error: (error:any) => {
        console.error(error);
      }
    });
  }

  receiveMessage(){
    this.afMessaging.messages.subscribe({
      next:(payload) => {
        console.log("New message received ", payload);
        this.currentMessage.next(payload);
      }
    })
  }
}
