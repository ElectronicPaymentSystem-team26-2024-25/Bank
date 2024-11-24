import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Payment } from '../model/payment';
import { environment } from '../env/env';
import { Observable } from 'rxjs';
import { BankPaymentResponse } from '../model/payment-response';

@Injectable({
  providedIn: 'root'
})
export class BankService {

  constructor(private http: HttpClient) { }

  executePayment(payment: Payment): Observable<BankPaymentResponse> {
    return this.http.put<BankPaymentResponse>(environment.apiHost + 'cardpayment/executepayment', payment);
  }
  getAmount(paymentId: string): Observable<number> {
    return this.http.get<number>(environment.apiHost + 'cardpayment/amount/'+paymentId);
  }
}
