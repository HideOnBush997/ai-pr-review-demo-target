export type PaymentResult = {
  paymentId: string;
  chargedCents: number;
};

export type PaymentGateway = {
  charge(orderId: string, amountCents: number): Promise<PaymentResult>;
};

export class InMemoryPaymentGateway implements PaymentGateway {
  async charge(orderId: string, amountCents: number): Promise<PaymentResult> {
    if (amountCents <= 0) {
      throw new Error("amount must be positive");
    }
    return {
      paymentId: `pay-${orderId}`,
      chargedCents: amountCents,
    };
  }
}
