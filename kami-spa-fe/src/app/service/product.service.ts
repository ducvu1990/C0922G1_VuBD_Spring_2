import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../model/product';
import {OrderRequestDTO} from '../model/order-request-dto';
import {Images} from '../model/images';
import {OrderDetail} from '../model/order-detail';
import {PayDto} from '../model/pay-dto';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) {
  }

  search(category: string, brands: string, name: string, minPrice: number, maxPrice: number, page: number, size: number): Observable<Page<Product>> {
    return this.http.get<Page<Product>>('http://localhost:8080/api/public/1/list-product?category=' +
      category + '&brands=' + brands + '&name=' + '&minPrice=' + minPrice + '&maxPrice=' + maxPrice + '&page=' + page + '&size=' + size);
  }

  addCart(orderRequestDTO: OrderRequestDTO): Observable<string> {
    return this.http.post<string>('http://localhost:8080/api/user/order/save', orderRequestDTO);
  }

  quantityOfCart(username: string): Observable<string> {
    return this.http.get<string>('http://localhost:8080/api/user/order/quantity?username=' + username);
  }

  getAllImages(): Observable<Images[]> {
    return this.http.get<Images[]>('http://localhost:8080/api/public/1/images');
  }

  getProduct(id: number): Observable<Product> {
    return this.http.get<Product>('http://localhost:8080/api/public/1/detail?id=' + id);
  }

  getAllCart(username: string): Observable<OrderDetail[]> {
    return this.http.get<OrderDetail[]>('http://localhost:8080/api/user/order-detail/cart?username=' + username);
  }

  getTotalAndQuantityCart(username: string): Observable<Array<any>> {
    return this.http.get<Array<any>>('http://localhost:8080/api/user/order-detail/total?username=' + username);
  }

  deleteOrderDetail(id: number): Observable<string> {
    return this.http.delete<string>('http://localhost:8080/api/user/order-detail/delete?id=' + id);
  }

  pay(pay: PayDto): Observable<string> {
    return this.http.post<string>('http://localhost:8080/api/user/order/pay', pay);
  }
}

export interface Page<T> {
  content: T[];
  pageable: {
    sort: {
      sorted: boolean;
      unsorted: boolean;
    };
    pageNumber: number;
    pageSize: number;
    offset: number;
    unpaged: boolean;
  };
  totalPages: number;
  totalElements: number;
  last: boolean;
  size: number;
  number: number;
  sort: {
    sorted: boolean;
    unsorted: boolean;
  };
  numberOfElements: number;
  first: boolean;
  empty: boolean;
}
