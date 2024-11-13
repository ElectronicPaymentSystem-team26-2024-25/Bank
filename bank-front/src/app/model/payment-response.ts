export interface PaymentResponse{
    merchantOrderId: number;
    acquirerOrderId: number;
    acquirerTimestamp: Date;
    paymentId: number;
    status: PaymentStatus;
    redirectUrl: string;
}