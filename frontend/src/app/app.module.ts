import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HeaderComponent } from './components/header/header.component';
import { ApolloModule, APOLLO_OPTIONS } from 'apollo-angular';
import { HttpLink } from 'apollo-angular/http';
import { InMemoryCache } from '@apollo/client/core';
import { TicketListComponent } from './components/tickets/ticket-list/ticket-list.component';
import { TicketDetailComponent } from './components/tickets/ticket-detail/ticket-detail.component';
import { ButtonModule } from 'primeng/button';
import { ImageModule } from 'primeng/image';
import { BadgeModule } from 'primeng/badge';
import { ChipModule } from 'primeng/chip';
import { DialogModule } from 'primeng/dialog';
import { AccountListComponent } from './components/accounts/account-list/account-list.component';
import { AccountDetailComponent } from './components/accounts/account-detail/account-detail.component';
import { ConfigPageComponent } from './components/config/config-page/config-page.component';
import { DropdownModule } from 'primeng/dropdown';
import { HomeComponent } from './components/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    HeaderComponent,
    TicketListComponent,
    TicketDetailComponent,
    AccountListComponent,
    AccountDetailComponent,
    ConfigPageComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    TableModule,
    ButtonModule,
    ChipModule,
    DialogModule,
    DropdownModule,
    OverlayPanelModule,
    ImageModule,
    BadgeModule,
    ApolloModule
  ], 
  providers: [
    {
      provide: APOLLO_OPTIONS,
      useFactory: (httpLink: HttpLink)=>{
        return {
          cache: new InMemoryCache(),
          link: httpLink.create({
            uri:'http://localhost:8080/graphql',
          }),
        };
      },
      deps:[HttpLink],
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
``