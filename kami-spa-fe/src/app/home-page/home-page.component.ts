import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {Product} from '../model/product';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {Subject} from 'rxjs';
import {OrderRequestDTO} from '../model/order-request-dto';
import Swal from 'sweetalert2';
import {Router} from '@angular/router';
import {ShareService} from '../security-authentication/service/share.service';
import {Images} from '../model/images';
import {OrderDetail} from '../model/order-detail';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {
  products: Product[];
  images: Images[];
  carts: OrderDetail[];
  category = '';
  brands = '';
  name = '';
  minPrice = 0;
  maxPrice = 0;
  page = 0;
  size = 8;
  pageCount = 0;
  pageNumbers: number[] = [];
  username: string;
  quantity = 1;
  orderRequestDTO: OrderRequestDTO = {email: '', productId: 1, quantity: 1};

  constructor(private productService: ProductService,
              private tokenStorageService: TokenStorageService,
              private router: Router,
              private shareService: ShareService) {
    this.carts = [];
    this.images = [];
  }

  ngOnInit(): void {
    this.getAll();
    this.getAllImage();
    if (this.tokenStorageService.getToken()) {
      this.username = this.tokenStorageService.getUser().username;
    }
    this.getAllCart();
  }

  getAllImage() {
    this.productService.getAllImages().subscribe(imageses => {
      this.images = imageses;
    });
  }

  getImageUrl(productId: number): string {
    const image = this.images.find((img) => img.product.id === productId);

    if (!image) {
      return 'default-image-url';
    }

    return image ? image.url : '';
  }

  getAll() {
    this.productService.search(this.category, this.brands, this.name, this.minPrice, this.maxPrice, this.page, this.size)
      .subscribe(productes => {
        this.products = productes.content;
        this.pageCount = productes.totalPages;
        this.pageNumbers = Array.from({length: this.pageCount}, (v, k) => k + 1);
      });
  }

  get pageNumbersToDisplay() {
    const currentPageIndex = this.page;
    const totalPageCount = this.pageCount;
    const pagesToShow = 3;

    if (totalPageCount <= pagesToShow) {
      return Array.from({length: totalPageCount}, (_, i) => i + 1);
    }

    const startPage = Math.max(0, currentPageIndex - Math.floor(pagesToShow / 2));
    let endPage = startPage + pagesToShow - 1;

    if (endPage >= totalPageCount) {
      endPage = totalPageCount - 1;
    }

    let pageNumbersToDisplay: (number | string)[] = Array.from({length: endPage - startPage + 1}, (_, i) => i + startPage + 1);

    if (startPage > 0) {
      pageNumbersToDisplay = ['...', ...pageNumbersToDisplay];
    }

    if (endPage < totalPageCount - 1) {
      pageNumbersToDisplay = [...pageNumbersToDisplay, '...'];
    }

    return pageNumbersToDisplay;
  }

  previousPage() {
    if (this.page > 0) {
      this.page--;
    }
    this.getAll();
    this.view();
  }

  nextPage() {
    if (this.page < this.pageCount - 1) {
      this.page++;
    }
    this.getAll();
    this.view();
  }

  goToPage(pageNumber: number | string) {
    if (typeof pageNumber === 'number') {
      this.page = pageNumber - 1;
    }
    this.getAll();
    this.view();
  }

  view(): void {
    const element = document.getElementById('homePage');
    if (element) {
      element.scrollIntoView();
    }
  }

  getAllCart() {
    if (this.tokenStorageService.getToken()) {
      this.productService.getAllCart(this.username).subscribe(cartes => {
        this.carts = cartes;
      }, error => {
        console.log('chưa có giỏ hàng.');
      });
    }
    return 0;
  }

  productQuantity(productId: number) {
    if (this.tokenStorageService.getToken()) {
      if (this.carts) {
        const carts = this.carts.find((cart) => cart.product.id === productId);

        if (!carts) {
          return 0;
        }
        return carts.orderedQuantity ? carts.orderedQuantity : 0;
      }
    }
    return 0;
  }

  addCart(id: number) {
    if (this.tokenStorageService.getToken()) {
      if (this.productQuantity(id) !== 0) {
        this.orderRequestDTO.email = this.username;
        this.orderRequestDTO.quantity = this.productQuantity(id);
        this.orderRequestDTO.productId = id;
        this.productService.addCart(this.orderRequestDTO).subscribe(cartes => {
          Swal.fire({
            text: 'Thêm giỏ hàng thành công.',
            icon: 'success',
            showConfirmButton: false,
            timer: 1500
          });
          this.shareService.sendClickEvent();
          this.getAll();
          this.getAllCart();
        }, error => {
          if (error.status === 405) {
            Swal.fire('Xin lỗi vì sự bất tiện này. Hiện tại sản phẩm này đang hết hàng.');
          } else {
            Swal.fire({
              text: 'Thêm giỏ hàng không thành công.',
              icon: 'error',
              showConfirmButton: false,
              timer: 3000
            });
          }
        });
      } else {
        this.orderRequestDTO.email = this.username;
        this.orderRequestDTO.quantity = this.quantity;
        this.orderRequestDTO.productId = id;
        this.productService.addCart(this.orderRequestDTO).subscribe(cartes => {
          Swal.fire({
            text: 'Thêm giỏ hàng thành công.',
            icon: 'success',
            showConfirmButton: false,
            timer: 1500
          });
          this.shareService.sendClickEvent();
          this.getAll();
          this.getAllCart();
        }, error => {
          if (error.status === 405) {
            Swal.fire('Xin lỗi vì sự bất tiện này. Hiện tại sản phẩm này đang hết hàng.');
          } else {
            Swal.fire({
              text: 'Thêm giỏ hàng không thành công.',
              icon: 'error',
              showConfirmButton: false,
              timer: 3000
            });
          }
        });
      }
    } else {
      this.router.navigateByUrl('/security/login');
    }
  }
}
