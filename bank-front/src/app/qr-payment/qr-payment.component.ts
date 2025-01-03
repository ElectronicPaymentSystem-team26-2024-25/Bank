import { Component } from '@angular/core';
import { BankService } from '../service/bank-service.service';
import { QRCodeResponse } from '../model/qr-code-response';
import { QRCodeRequest } from '../model/qr-code-request';
import { ActivatedRoute } from '@angular/router';

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

  constructor(private service: BankService, private route: ActivatedRoute){}
  ngOnInit(): void{
   const idString = this.route.snapshot.paramMap.get('paymentId')
   this.paymentId = idString != null ? idString : null
   this.service.getQrCode(this.paymentId!).subscribe({
          next: (response) => {
            this.qrCodeResponse =  `data:image/png;base64,${response.response}`;
            console.log(this.qrCodeResponse)
          },
          error: (error) => {
            console.log(error)
          }
    });
  }
}
