import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {SignInComponent} from "./components/sign-in/sign-in.component";
import {isLoggedInGuard} from "./service/guards/is-logged-in.guard";
import {ProjectListComponent} from "./components/project/project-list/project-list.component";
import {BoardComponent} from "./components/board/board.component";


const routes: Routes = [
  {path: '', component: SignInComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'project', component: ProjectListComponent, canActivate: [isLoggedInGuard]},
  {path: 'board/:projectId', component: BoardComponent, canActivate: [isLoggedInGuard]}
  //{path: '**', redirectTo: '', pathMatch: 'full'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
