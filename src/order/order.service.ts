import type { PaymentGateway } from "../payment/payment.service.js";

export type Order = {
  id: string;
  sku: string;
  quantity: number;
  amountCents: number;
  status: "pending" | "paid";
};

export type InventoryStore = {
  reserve(sku: string, quantity: number): Promise<void>;
  release(sku: string, quantity: number): Promise<void>;
};

export class OrderService {
  constructor(
    private readonly gateway: PaymentGateway,
    private readonly inventory: InventoryStore,
  ) {}

  async pay(order: Order): Promise<Order> {
    await this.inventory.reserve(order.sku, order.quantity);
    try {
      await this.gateway.charge(order.id, order.amountCents);
      return {
        ...order,
        status: "paid",
      };
    } catch (error) {
      await this.inventory.release(order.sku, order.quantity);
      throw error;
    }
  }
}
