import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HeaderComponent } from './components/header/header.component';
import { ApolloModule, APOLLO_OPTIONS } from 'apollo-angular';
import { HttpLink } from 'apollo-angular/http';
import { InMemoryCache } from '@apollo/client/core';
import { TicketListComponent } from './components/tickets/ticket-list/ticket-list.component';
import { TicketDetailComponent } from './components/tickets/ticket-detail/ticket-detail.component';
import { AccountListComponent } from './components/accounts/account-list/account-list.component';
import { AccountDetailComponent } from './components/accounts/account-detail/account-detail.component';
import { ConfigPageComponent } from './components/config/config-page/config-page.component';
import { HomeComponent } from './components/home/home.component';
import { ConfirmationDialogComponent } from './components/modal/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from './components/modal/result-dialog/result-dialog.component';
import { AddTicketComponent } from './components/tickets/add-ticket/add-ticket.component';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputSwitchModule } from 'primeng/inputswitch';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { DropdownModule } from 'primeng/dropdown';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { TableModule } from 'primeng/table';
import { EditorModule } from 'primeng/editor';
import { ChartModule } from 'primeng/chart';
import { SkeletonModule } from 'primeng/skeleton';
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';
import { SolveTicketComponent } from './components/tickets/solve-ticket/solve-ticket.component';
import { JwtInterceptor } from './helpers/jwt-interceptor';
import { ErrorInterceptor } from './helpers/error-interceptor';
import { AddAccountComponent } from './components/accounts/add-account/add-account.component';
import { EditAccountComponent } from './components/accounts/edit-account/edit-account.component';
import { MyTicketListComponent } from './components/my-tickets/my-ticket-list/my-ticket-list.component';
import { MyTaskListComponent } from './components/my-tasks/my-task-list/my-task-list.component';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { HasRoleDirective } from './directives/has-role.directive';
import { ConfigDivisionComponent } from './components/config/config-division/config-division.component';
import { ConfigApplicationComponent } from './components/config/config-application/config-application.component';
import { ConfigTagComponent } from './components/config/config-tag/config-tag.component';
import { ConfigScoringComponent } from './components/config/config-scoring/config-scoring.component';
import { AddDivisionComponent } from './components/config/add-division/add-division.component';
import { AddTagComponent } from './components/config/add-tag/add-tag.component';
import { AddApplicationComponent } from './components/config/add-application/add-application.component';
import { EditCommentComponent } from './components/comments/edit-comment/edit-comment.component';
import { ProfileDetailComponent } from './components/profile/profile-detail/profile-detail.component';
import { EditProfileComponent } from './components/profile/edit-profile/edit-profile.component';
import { ChangePasswordComponent } from './components/profile/change-password/change-password.component';
import { CommentListComponent } from './components/comments/comment-list/comment-list.component';
import { NotificationPageComponent } from './components/notifications/notification-page/notification-page.component';
import { ProfileDropdownComponent } from './components/modal/profile-dropdown/profile-dropdown.component';
import { NotificationDropdownComponent } from './components/modal/notification-dropdown/notification-dropdown.component';

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
    SolveTicketComponent,
    AddAccountComponent,
    EditAccountComponent,
    MyTicketListComponent,
    MyTaskListComponent,
    HasRoleDirective,
    ConfigDivisionComponent,
    ConfigApplicationComponent,
    ConfigTagComponent,
    ConfigScoringComponent,
    AddDivisionComponent,
    AddTagComponent,
    AddApplicationComponent,
    CommentListComponent,
    EditCommentComponent,
    ProfileDetailComponent,
    EditProfileComponent,
    ChangePasswordComponent,
    NotificationPageComponent,
    ProfileDropdownComponent,
    NotificationDropdownComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    TableModule,
    DynamicDialogModule,
    AutoCompleteModule,
    InputTextModule,
    InputTextareaModule,
    InputSwitchModule,
    DropdownModule,
    OverlayPanelModule,
    MessagesModule,
    SkeletonModule,
    EditorModule,
    ChartModule,
    ApolloModule,
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
    { 
      provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true 
    },
    { 
      provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true 
    },
    DialogService,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
``