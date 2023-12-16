import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./auth/components/sign-up/sign-up.component";
import {SignInComponent} from "./auth/components/sign-in/sign-in.component";
import {isLoggedInGuard} from "./shared/guards/is-logged-in.guard";
import {ProjectListComponent} from "./project/components/project-list/project-list.component";
import {BoardComponent} from "./board/components/board.component";
import {HomeComponent} from "./home/components/home/home.component";
import {DashboardComponent} from "./dashboard/components/dashboard/dashboard.component";
import {BacklogComponent} from "./backlog/components/backlog/backlog.component";


const routes: Routes = [
  {path: '', component: SignInComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'project', component: ProjectListComponent, canActivate: [isLoggedInGuard]},
  {path: 'boards',component: BoardComponent ,canActivate: [isLoggedInGuard]},
  {path: 'home', component: HomeComponent,canActivate: [isLoggedInGuard]},
  {path: 'backlog', component: BacklogComponent,canActivate: [isLoggedInGuard]},
  {path: 'dashboard', component: DashboardComponent,canActivate: [isLoggedInGuard]},
  {path: '**', redirectTo: 'project', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
