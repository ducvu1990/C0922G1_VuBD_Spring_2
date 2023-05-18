import {Component, OnInit} from '@angular/core';
import {ProductService} from '../service/product.service';
import {OrderDetail} from '../model/order-detail';
import {TokenStorageService} from '../security-authentication/service/token-storage.service';
import {Images} from '../model/images';
import {OrderRequestDTO} from '../model/order-request-dto';
import Swal from 'sweetalert2';
import {ShareService} from '../security-authentication/service/share.service';
import {PayDto} from '../model/pay-dto';
import {error} from 'protractor';
import {render} from 'creditcardpayments/creditCardPayments';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  username: string;
  carts: OrderDetail[];
  images: Images[];
  total = 0;
  quantity = 0;
  productToatals = 0;
  totalAndQuantity: Array<any>;
  orderRequestDTO: OrderRequestDTO = {email: '', productId: 1, quantity: 1};
  // tslint:disable-next-line:new-parens
  payDto: PayDto = new class implements PayDto {
    email: string;
    total: number;
  };

  constructor(private productService: ProductService,
              private tokenStorageService: TokenStorageService,
              private shareService: ShareService) {
    this.images = [];
  }

  ngOnInit(): void {
    if (this.tokenStorageService.getToken()) {
      this.username = this.tokenStorageService.getUser().username;
    }
    this.getAll();
    this.view();
    this.getAllImage();
    this.getTotal();
    render(
      {
        id: '#payments',
        currency: 'USD',
        value: '100000.00',
        onApprove: (details) => {
          this.payDto.email = this.username;
          this.payDto.total = this.total;
          this.productService.pay(this.payDto).subscribe(next => {
            Swal.fire({
              text: 'Thanh toán thành công',
              icon: 'success',
              showConfirmButton: false,
              timer: 1500
            });
            this.getAll();
            this.getTotal();
            this.shareService.sendClickEvent();
          }, error => {
            Swal.fire({
              text: 'Thanh toán thất bại, có thể món hàng đã bị mua hết, mời kiểm tra lại số lượng còn lại trong giỏ hàng',
              icon: 'error',
              showConfirmButton: false,
              timer: 1500
            });
            this.getAll();
          });
        }
      }
    );
  }

  getTotal() {
    this.productService.getTotalAndQuantityCart(this.username).subscribe(totalAndQuantityes => {
      if (totalAndQuantityes == null) {
        this.total = 0;
        this.quantity = 0;
      } else {
        this.totalAndQuantity = totalAndQuantityes;
        this.total = this.totalAndQuantity[0];
        this.quantity = this.totalAndQuantity[1];
      }
    }, error => {
      console.log('chưa có giỏ hàng');
    });
  }

  productTotal(productId: number) {
    const carts = this.carts.find((cart) => cart.product.id === productId);

    if (!carts) {
      return 0;
    }
    this.productToatals = carts.product.price * carts.orderedQuantity;
    return this.productToatals ? this.productToatals : 0;
  }

  getAll() {
    this.productService.getAllCart(this.username).subscribe(cartes => {
      this.carts = cartes;
    }, error => {
      console.log('chưa có giỏ hàng.');
    });
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

  view(): void {
    const element = document.getElementById('site-header');
    if (element) {
      element.scrollIntoView();
    }
  }

  setQuantity(value: string, productId: number, id: number) {
    if (+value < 1) {
      this.delete(id);
    } else {
      this.orderRequestDTO.email = this.username;
      this.orderRequestDTO.quantity = +value;
      this.orderRequestDTO.productId = productId;
      this.productService.addCart(this.orderRequestDTO).subscribe(() => {
        this.getTotal();
        this.getAll();
      }, error => {
        Swal.fire({
          text: 'Số lượng bạn nhập không hợp lệ.',
          icon: 'error',
          showConfirmButton: false,
          timer: 1500
        });
      });
    }
  }

  increase(value: string, productId: number, id: number) {
    if (+value < 1) {
      this.delete(id);
    } else {
      this.orderRequestDTO.email = this.username;
      this.orderRequestDTO.quantity = +value + 1;
      this.orderRequestDTO.productId = productId;
      this.productService.addCart(this.orderRequestDTO).subscribe(() => {
        this.getTotal();
        this.getAll();
      }, error => {
        Swal.fire({
          text: 'Số lượng bạn nhập không hợp lệ.',
          icon: 'error',
          showConfirmButton: false,
          timer: 1500
        });
      });
    }
  }

  reduce(value: string, productId: number, id: number) {
    if (+value < 1) {
      this.delete(id);
    } else {
      this.orderRequestDTO.email = this.username;
      this.orderRequestDTO.quantity = +value - 1;
      this.orderRequestDTO.productId = productId;
      this.productService.addCart(this.orderRequestDTO).subscribe(() => {
        this.getTotal();
        this.getAll();
      }, error => {
        Swal.fire({
          text: 'Số lượng bạn nhập không hợp lệ.',
          icon: 'error',
          showConfirmButton: false,
          timer: 1500
        });
      });
    }
  }

  delete(id: number) {
    this.productService.deleteOrderDetail(id).subscribe(next => {
      this.getAll();
      this.shareService.sendClickEvent();
    }, error => {
      Swal.fire({
        text: 'Lỗi. xin hãy thử lại',
        icon: 'error',
        showConfirmButton: false,
        timer: 1500
      });
    });
  }

  pay() {
    this.payDto.email = this.username;
    this.payDto.total = this.total;
    this.productService.pay(this.payDto).subscribe(next => {
      Swal.fire({
        text: 'Thanh toán thành công',
        icon: 'success',
        showConfirmButton: false,
        timer: 1500
      });
      this.getAll();
    }, error => {
      Swal.fire({
        text: 'Thanh toán thất bại, có thể món hàng đã bị mua hết, mời kiểm tra lại số lượng còn lại trong giỏ hàng',
        icon: 'error',
        showConfirmButton: false,
        timer: 1500
      });
      this.getAll();
    });
  }
}
