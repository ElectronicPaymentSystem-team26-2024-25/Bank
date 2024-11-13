import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Payment } from '../model/payment';
import { BankService } from '../service/bank-service.service';

@Component({
  selector: 'app-payment-form',
  templateUrl: './payment-form.component.html',
  styleUrls: ['./payment-form.component.css']
})
export class PaymentFormComponent {
  paymentRequest: Payment = {cardHolderName: '', expirationDate: new Date(), pan: 0, paymentId: 0, securityCode: 0}
  paymentId: number | null = null
  pan: number = 0
  security_code: number = 0
  card_holder_name: string = ""
  expiration_date: string = ""
  
  constructor(private route: ActivatedRoute, private service: BankService) {}

  ngOnInit(): void{
    const idString = this.route.snapshot.paramMap.get('paymentId')
    this.paymentId = idString != null ? Number(idString) : null
  }

  onSubmit(){
    this.paymentRequest.cardHolderName = this.card_holder_name
    this.paymentRequest.pan = this.pan
    this.paymentRequest.securityCode = this.security_code
    this.paymentRequest.paymentId = this.paymentId!
    this.paymentRequest.expirationDate = this.parseDate(this.expiration_date)
    console.log(this.paymentRequest)
    this.service.executePayment(this.paymentRequest).subscribe({
      next: (response) => {
        console.log(response);
      },
      error: (error) => {
        console.log(error);
      }
    });
  }

  parseDate(dateInput: string): Date{
    const [yy, mm] = dateInput.split('/')
    const year = 2000 + parseInt(yy, 10)
    const month = parseInt(mm, 10) - 1
    const date = new Date(year, month, 28)
    return date
  }
  
}
