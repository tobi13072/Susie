import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignUpComponent } from './auth/components/sign-up/sign-up/sign-up.component';
import { MenubarModule } from 'primeng/menubar';
import { ButtonModule } from "primeng/button";
import { RouterModule } from '@angular/router';
import { NgOptimizedImage } from "@angular/common";
import { ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputSwitchModule } from 'primeng/inputswitch';
import { CardModule } from 'primeng/card';
import { HttpClientModule } from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent
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
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
