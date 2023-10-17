import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {ProjectFormComponent} from "./components/project-form/project-form.component";
import {SignInComponent} from "./components/sign-in/sign-in.component";
import {BoardComponent} from "./components/board/board.component";
import {authGuard} from "./service/auth/auth.guard";
import {ProjectListComponent} from "./components/project-list/project-list.component";


const routes: Routes = [
  {path: '', component: SignInComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'project', component: ProjectFormComponent,canActivate: [authGuard]},
  {path: 'board', component: BoardComponent, canActivate: [authGuard]},
  {path: 'project-list', component: ProjectListComponent, canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
