import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {ProjectComponent} from "./components/project/project.component";
import {SignInComponent} from "./components/sign-in/sign-in.component";
import {BoardComponent} from "./components/board/board.component";


const routes: Routes = [
  {path: 'aaa', component: SignInComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: 'project', component: ProjectComponent},
  {path: '', component: BoardComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
