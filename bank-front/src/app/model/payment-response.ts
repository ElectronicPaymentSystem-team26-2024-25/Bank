import { PaymentStatus } from "./payment-status";

export interface BankPaymentResponse{
    merchantOrderId: number;
    acquirerOrderId: number;
    acquirerTimestamp: Date;
    paymentId: number;
    status: PaymentStatus;
    redirectUrl: string;
    failReason: string;
}