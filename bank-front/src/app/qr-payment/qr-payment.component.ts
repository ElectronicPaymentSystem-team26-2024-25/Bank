import { Component } from '@angular/core';
import { BankService } from '../service/bank-service.service';
import { QRCodeResponse } from '../model/qr-code-response';
import { QRCodeRequest } from '../model/qr-code-request';
import { ActivatedRoute } from '@angular/router';
import { interval, Subject, switchMap, takeUntil } from 'rxjs';

@Component({
  selector: 'app-qr-payment',
  templateUrl: './qr-payment.component.html',
  styleUrls: ['./qr-payment.component.css']
})
export class QrPaymentComponent {
  // R ce mi biti racun primaoca
  // RO ce mi biti merchant id
  // SF sifra placanja
  // N ce biti naziv merchanta
  // I ce biti valuta plus iznos
  // S ce biti svrha uplate
  // C paymentId
  // ostalo dummy podaci
  qrCodeResponse?: string
  paymentId: string | null = null

  private destroy$ = new Subject<void>();

  constructor(private service: BankService, private route: ActivatedRoute){}
  ngOnInit(): void{
   const idString = this.route.snapshot.paramMap.get('paymentId')
   this.paymentId = idString != null ? idString : null

   if (!this.paymentId) {
      console.error('No paymentId provided');
      return;
   }

   this.service.getQrCode(this.paymentId!).subscribe({
          next: (response) => {
            this.qrCodeResponse =  `data:image/png;base64,${response.response}`;
            console.log(this.qrCodeResponse)
          },
          error: (error) => {
            console.log(error)
          }
    });

    interval(5000)
      .pipe(
        takeUntil(this.destroy$),
        switchMap(() => this.service.getPaymentStatus(this.paymentId!))
      )
      .subscribe({
        next: (response) => {
          console.log('Payment status:', response);
          if (response.status === 'SUCCESS') {
            window.location.href = `https://localhost:4200/success/${response.merchantOrderId}`;
            this.destroy$.next();
          } else if (response.status === 'ERROR') {
            window.location.href = `https://localhost:4200/error/${response.merchantOrderId}`;
            this.destroy$.next();
          } else if (response.status === 'FAILURE') {
            window.location.href = `https://localhost:4200/fail/${response.merchantOrderId}`;
            this.destroy$.next();
          }
        },
        error: (err) => {
          console.error('Polling error:', err);
        }
      });
  }
}
