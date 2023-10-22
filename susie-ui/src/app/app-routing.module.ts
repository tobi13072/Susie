import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {SignInComponent} from "./components/sign-in/sign-in.component";
import {BoardComponent} from "./components/board/board.component";
import {isLoggedInGuard} from "./service/guards/is-logged-in.guard";
import {ProjectListComponent} from "./components/project/project-list/project-list.component";


const routes: Routes = [
  {path: '', component: SignInComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'project', component: ProjectListComponent, canActivate: [isLoggedInGuard]},
  {path: 'board', component: BoardComponent, canActivate: [isLoggedInGuard]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
