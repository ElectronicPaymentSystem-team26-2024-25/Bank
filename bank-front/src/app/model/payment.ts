export interface Payment{
    paymentId: string;
    pan: string;
    securityCode: number;
    cardHolderName: string;
    expirationDate: Date;
}