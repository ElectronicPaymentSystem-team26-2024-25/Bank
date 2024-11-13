import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Payment } from '../model/payment';
import { environment } from '../env/env';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BankService {

  constructor(private http: HttpClient) { }

  executePayment(payment: Payment): Observable<PaymentResponse> {
    return this.http.put<PaymentResponse>(environment.apiHost + 'cardpayment/executepayment', payment);
  }
}
