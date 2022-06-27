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
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { AccountListComponent } from './components/accounts/account-list/account-list.component';
import { AccountDetailComponent } from './components/accounts/account-detail/account-detail.component';
import { ConfigPageComponent } from './components/config/config-page/config-page.component';
import { DropdownModule } from 'primeng/dropdown';
import { HomeComponent } from './components/home/home.component';
import { ConfirmationDialogComponent } from './components/modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from './components/modal/result-dialog/result-dialog.component';
import { AddTicketComponent } from './components/tickets/add-ticket/add-ticket.component';

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
    ConfirmationDialogComponent,
    ResultDialogComponent,
    AddTicketComponent,
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
    DynamicDialogModule,
    DropdownModule,
    OverlayPanelModule,
    ImageModule,
    BadgeModule,
    ApolloModule
  ],
  providers: [
    {
      provide: APOLLO_OPTIONS,
      useFactory: (httpLink: HttpLink) => {
        return {
          cache: new InMemoryCache(),
          defaultOptions: {
            watchQuery: {
              fetchPolicy: 'cache-and-network',
              errorPolicy: 'ignore',
            },
            query: {
              fetchPolicy: 'network-only',
              errorPolicy: 'all',
            },
            mutate: {
              errorPolicy: 'all'
            }
          },
          link: httpLink.create({
            uri: 'http://localhost:8080/graphql',
          }),
        };
      },
      deps: [HttpLink],
    },
    DialogService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
``