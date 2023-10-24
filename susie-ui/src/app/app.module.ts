import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SignUpComponent} from './components/sign-up/sign-up.component';
import {MenubarModule} from 'primeng/menubar';
import {ButtonModule} from "primeng/button";
import {RouterModule} from '@angular/router';
import {NgOptimizedImage} from "@angular/common";
import {ReactiveFormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {InputSwitchModule} from 'primeng/inputswitch';
import {CardModule} from 'primeng/card';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ProjectFormComponent} from './components/project/project-form/project-form.component';
import {KeyFilterModule} from "primeng/keyfilter";
import {RippleModule} from "primeng/ripple";
import {SignInComponent} from './components/sign-in/sign-in.component';
import {BoardComponent} from './components/board/board.component';
import {PanelModule} from "primeng/panel";
import {DragDropModule} from "primeng/dragdrop";
import {AuthInterceptor} from "./service/interceptors/auth.interceptor";
import {ProjectListComponent} from './components/project/project-list/project-list.component';
import {TableModule} from "primeng/table";
import {StyleClassModule} from "primeng/styleclass";
import {DynamicDialogModule} from "primeng/dynamicdialog";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {PasswordModule} from "primeng/password";

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    ProjectFormComponent,
    SignInComponent,
    BoardComponent,
    ProjectListComponent
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
    PasswordModule
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
