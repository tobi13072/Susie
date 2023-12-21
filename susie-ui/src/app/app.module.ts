import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SignUpComponent} from './auth/components/sign-up/sign-up.component';
import {MenubarModule} from 'primeng/menubar';
import {ButtonModule} from "primeng/button";
import {RouterModule} from '@angular/router';
import {NgOptimizedImage} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {InputSwitchModule} from 'primeng/inputswitch';
import {CardModule} from 'primeng/card';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ProjectFormComponent} from './project/components/project-form/project-form.component';
import {KeyFilterModule} from "primeng/keyfilter";
import {RippleModule} from "primeng/ripple";
import {SignInComponent} from './auth/components/sign-in/sign-in.component';
import {BoardComponent} from './board/components/board.component';
import {PanelModule} from "primeng/panel";
import {DragDropModule} from "primeng/dragdrop";
import {AuthInterceptor} from "./shared/interceptors/auth.interceptor";
import {ProjectListComponent} from './project/components/project-list/project-list.component';
import {TableModule} from "primeng/table";
import {StyleClassModule} from "primeng/styleclass";
import {DynamicDialogModule} from "primeng/dynamicdialog";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {PasswordModule} from "primeng/password";
import {MenuModule} from "primeng/menu";
import {SidebarModule} from "primeng/sidebar";
import { HomeComponent } from './home/components/home/home.component';
import { DashboardComponent } from './dashboard/components/dashboard/dashboard.component';
import { BacklogComponent } from './backlog/components/backlog/backlog.component';
import {AvatarModule} from "primeng/avatar";
import { IssueFormComponent } from './backlog/components/issue form/issue-form.component';
import {SliderModule} from "primeng/slider";
import {DropdownModule} from "primeng/dropdown";
import {TagModule} from "primeng/tag";
import {AccordionModule} from "primeng/accordion";
import { SprintFormComponent } from './backlog/components/sprint form/sprint-form.component';
import {CalendarModule} from "primeng/calendar";
import {BadgeModule} from "primeng/badge";
import { IssueDetailsComponent } from './backlog/components/issue details/issue-details.component';
import {FieldsetModule} from "primeng/fieldset";
import {TabViewModule} from "primeng/tabview";

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    ProjectFormComponent,
    SignInComponent,
    BoardComponent,
    ProjectListComponent,
    HomeComponent,
    DashboardComponent,
    BacklogComponent,
    IssueFormComponent,
    SprintFormComponent,
    IssueDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    MenubarModule,
    ButtonModule,
    NgOptimizedImage,
    ReactiveFormsModule,
    InputTextModule,
    InputSwitchModule,
    CardModule,
    HttpClientModule,
    KeyFilterModule,
    RippleModule,
    PanelModule,
    DragDropModule,
    TableModule,
    StyleClassModule,
    DynamicDialogModule,
    BrowserAnimationsModule,
    ConfirmDialogModule,
    PasswordModule,
    MenuModule,
    SidebarModule,
    AvatarModule,
    SliderModule,
    DropdownModule,
    TagModule,
    AccordionModule,
    CalendarModule,
    BadgeModule,
    FieldsetModule,
    FormsModule,
    TabViewModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
