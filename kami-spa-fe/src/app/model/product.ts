import {Category} from './category';
import {Brands} from './brands';

export interface Product {
    id?: number;

    name?: string;

    price?: number;

    description?: string;

    releaseDate?: string;

    productQuantity?: number;

    category?: Category;

    brands?: Brands;
}
