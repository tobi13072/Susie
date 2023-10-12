import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {ProjectComponent} from "./components/project/project.component";
import {SignInComponent} from "./components/sign-in/sign-in.component";
import {BoardComponent} from "./components/board/board.component";
import {authGuard} from "./service/auth/auth.guard";


const routes: Routes = [
  {path: '', component: SignInComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'project', component: ProjectComponent,canActivate: [authGuard]},
  {path: 'board', component: BoardComponent, canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
