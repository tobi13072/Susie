import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./auth/components/sign-up/sign-up.component";
import {SignInComponent} from "./auth/components/sign-in/sign-in.component";
import {isLoggedInGuard} from "./shared/guards/is-logged-in.guard";
import {ProjectListComponent} from "./project/components/project-list/project-list.component";
import {BoardComponent} from "./board/components/board.component";


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
