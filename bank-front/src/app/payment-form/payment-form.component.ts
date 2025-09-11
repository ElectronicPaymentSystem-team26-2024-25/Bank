import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Payment } from '../model/payment';
import { BankService } from '../service/bank-service.service';
import { PaymentStatus } from '../model/payment-status';

@Component({
  selector: 'app-payment-form',
  templateUrl: './payment-form.component.html',
  styleUrls: ['./payment-form.component.css']
})
export class PaymentFormComponent {
  paymentRequest: Payment = {cardHolderName: '', expirationDate: new Date(), pan: "", paymentId: '0', securityCode: 0}
  paymentId: string | null = null
  pan: string = ""
  security_code: number = 0
  card_holder_name: string = ""
  expiration_date: string = ""
  amount: number = 0
  
  constructor(private route: ActivatedRoute, private service: BankService, private router: Router) {}

  ngOnInit(): void{
    const idString = this.route.snapshot.paramMap.get('paymentId')
    this.paymentId = idString != null ? idString : null
    this.getPaymentAmount()
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
        if(response.status === PaymentStatus.SUCCESS){
            window.location.href = 'https://localhost:4200/success/'+response.merchantOrderId
        }else if(response.status = PaymentStatus.ERROR){
          window.location.href = 'https://localhost:4200/error/'+response.merchantOrderId
        }else{
          window.location.href = 'https://localhost:4200/fail/'+response.merchantOrderId
        }
      },
      error: (error) => {
        window.location.href = 'https://localhost:4200/error/-1'
      }
    });
  }

  payWithQrCode(){
    this.router.navigate(['/qrpayment', this.paymentId]);
  }

  parseDate(dateInput: string): Date{
    const [yy, mm] = dateInput.split('/')
    const year = 2000 + parseInt(yy, 10)
    const month = parseInt(mm, 10) - 1
    const date = new Date(year, month, 28)
    return date
  }

  getPaymentAmount(){
    this.service.getAmount(this.paymentId!).subscribe({
      next: (response) => {
        this.amount = response
      }
    });
  }
}
