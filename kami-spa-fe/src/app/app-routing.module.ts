import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserGuard} from './security-authentication/security-auth/user.guard';
import {HomePageComponent} from './home-page/home-page.component';
import {DetailComponent} from './detail/detail.component';
import {CartComponent} from './cart/cart.component';


const routes: Routes = [
  {
    path: '', component: HomePageComponent
  },
  {
    path: 'detail/:id', component: DetailComponent
  },
  {
    path: 'cart', component: CartComponent
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
