export interface Payment{
    paymentId: number;
    pan: string;
    securityCode: number;
    cardHolderName: string;
    expirationDate: Date;
}