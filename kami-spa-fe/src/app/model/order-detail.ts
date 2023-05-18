import {Product} from './product';
import {Orders} from './orders';

export interface OrderDetail {
  id: number;
  orderedQuantity: number;
  product: Product;
  orders: Orders;
}
