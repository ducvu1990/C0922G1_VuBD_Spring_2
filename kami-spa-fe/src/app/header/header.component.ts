import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {ShareService} from '../security-authentication/service/share.service';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {ProductService} from '../service/product.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  username: string;
  currentUser: string;
  name: string;
  role: string;
  url: string;
  isLoggedIn = false;
  quantityOfCart: string;

  constructor(private tokenStorageService: TokenStorageService,
              private shareService: ShareService,
              private router: Router,
              private productService: ProductService) {
  }

  ngOnInit(): void {
    this.shareService.getClickEvent().subscribe(() => {
      this.loadHeader();
    });
    this.loadHeader();
    this.view();
  }

  view(): void {
    const element = document.getElementById('header');
    if (element) {
      element.scrollIntoView();
    }
  }

  async logOut() {
    this.tokenStorageService.signOut();
    this.shareService.sendClickEvent();
    await Swal.fire({
      text: 'Đăng xuất thành công',
      icon: 'success',
      showConfirmButton: false,
      timer: 1500
    });
    await this.router.navigateByUrl('/');
    this.name = undefined;
    this.isLoggedIn = false;
    this.role = undefined;
    this.url = undefined;
    this.currentUser = undefined;
    this.quantityOfCart = undefined;
  }

  loadHeader(): void {
    if (this.tokenStorageService.getToken()) {
      this.currentUser = this.tokenStorageService.getUser().username;
      this.role = this.tokenStorageService.getUser().roles[0];
      this.username = this.tokenStorageService.getUser().username;
      this.url = this.tokenStorageService.getUser().url;
      this.productService.quantityOfCart(this.username).subscribe(quantityes => {
        this.quantityOfCart = quantityes;
      }, error => {
        console.log('chưa có giỏ hàng');
      });
    }
    this.isLoggedIn = this.username != null;
    this.getUsernameAccount();
  }

  getUsernameAccount() {
    if (this.tokenStorageService.getToken()) {
      this.name = this.tokenStorageService.getUser().name;
    }
  }

  routers() {
    if (this.tokenStorageService.getToken()) {
      this.router.navigateByUrl('/cart');
      this.shareService.sendClickEvent();
    } else {
      this.router.navigateByUrl('/security/login');
    }
  }

  home() {
    const element = document.getElementById('home');
    if (element) {
      element.scrollIntoView();
    }
  }

  blog() {
    const element = document.getElementById('blog');
    if (element) {
      element.scrollIntoView();
    }
  }

  shop() {
    const element = document.getElementById('homePage');
    if (element) {
      element.scrollIntoView();
    }
  }
}
