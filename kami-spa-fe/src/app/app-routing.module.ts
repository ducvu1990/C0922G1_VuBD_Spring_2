import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserGuard} from './security-authentication/security-auth/user.guard';
import {HomePageComponent} from './home-page/home-page.component';


const routes: Routes = [
  // {
  //   path: 'homepage',
  //   loadChildren: () => import('./home-page/home-page.component').then(module => module.HomePageComponent),
  //   canActivate: [UserGuard]
  // },
  {
    path: '', component: HomePageComponent
  },
  {
    path: 'security',
    loadChildren: () => import('./security-authentication/security-authentication.module')
      .then(module => module.SecurityAuthenticationModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
