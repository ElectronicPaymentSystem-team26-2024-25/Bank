import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Payment } from '../model/payment';
import { environment } from '../env/env';
import { Observable } from 'rxjs';
import { BankPaymentResponse } from '../model/payment-response';
import { QRCodeResponse } from '../model/qr-code-response';
import { PaymentStatusResponse } from '../model/payment-status-response';

@Injectable({
  providedIn: 'root'
})
export class BankService {

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
  });

  constructor(private http: HttpClient) { }

  executePayment(payment: Payment): Observable<BankPaymentResponse> {
    return this.http.put<BankPaymentResponse>(environment.apiHost + 'cardpayment/executepayment', payment);
  }
  getAmount(paymentId: string): Observable<number> {
    return this.http.get<number>(environment.apiHost + 'cardpayment/amount/'+paymentId);
  }
  getQrCode(paymentId: string): Observable<QRCodeResponse> {
   return this.http.get<QRCodeResponse>(environment.apiHost + 'qrcode/'+paymentId);
  }
  getPaymentStatus(paymentId: string): Observable<PaymentStatusResponse> {
    return this.http.get<PaymentStatusResponse>(environment.apiHost + 'qrcode/payment-status/'+paymentId);
  }
}
