import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./auth/components/sign-up/sign-up.component";
import {ProjectComponent} from "./components/project/project.component";

const routes: Routes = [
  {path: 'sign-up', component: SignUpComponent},
  {path: 'project', component: ProjectComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
