import {Person} from './person';

export interface Orders {
    id: number;
    orderCode: string;
    order_date: Date;
    total: number;
    employeeId: number;
    shipping_address: string;
    person: Person;
}
