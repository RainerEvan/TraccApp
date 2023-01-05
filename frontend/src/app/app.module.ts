import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AngularFireMessagingModule } from '@angular/fire/compat/messaging';
import { AngularFireModule } from '@angular/fire/compat/';
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { GraphQLModule } from './graphql.module';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { JwtInterceptor } from './utils/jwt-interceptor/jwt.interceptor';
import { ErrorInterceptor } from './utils/error-interceptor/error.interceptor';
import { ShellComponent } from './components/shell/shell.component';
import { LoginComponent } from './components/login/login.component';
import { HeaderComponent } from './components/header/header.component';
import { ProfileDropdownComponent } from './components/modal/profile-dropdown/profile-dropdown.component';
import { NotificationDropdownComponent } from './components/modal/notification-dropdown/notification-dropdown.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { InputTextModule } from 'primeng/inputtext';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { DialogService } from 'primeng/dynamicdialog';
import { SharedModule } from './modules/shared/shared.module';

@NgModule({
  declarations: [
    AppComponent,
    ShellComponent,
    LoginComponent,
    HeaderComponent,
    ProfileDropdownComponent,
    NotificationDropdownComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    GraphQLModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    TableModule,
    ToastModule,
    InputTextModule,
    OverlayPanelModule,
    AngularFireMessagingModule,
    AngularFireModule.initializeApp(environment.firebase),
    ServiceWorkerModule.register('ngsw-worker.js', {
      enabled: environment.production,
      // Register the ServiceWorker as soon as the application is stable
      // or after 30 seconds (whichever comes first).
      registrationStrategy: 'registerWhenStable:30000'
    })
  ],
  providers: [
    { 
      provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true 
    },
    { 
      provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true 
    },
    {
      provide: LocationStrategy, useClass:HashLocationStrategy
    },
    MessageService,
    DialogService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
