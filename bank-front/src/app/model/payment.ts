export interface Payment{
    paymentId: number;
    pan: number;
    securityCode: number;
    cardHolderName: string;
    expirationDate: Date;
}