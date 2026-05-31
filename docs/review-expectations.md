# Review Expectations

这里记录每个测试 PR 中故意埋入的问题，方便和 AI PR Review 助手输出做对比。

main 分支不包含这些问题。

## PR 2: payment rollback missing

分支：`case/payment-rollback-missing`

预期问题：

- `src/order/order.service.ts` 删除了支付失败时的 `inventory.release(...)` 回滚逻辑。
- 一旦 `gateway.charge(...)` 抛错，库存会保持预留状态，违反 `docs/payment-flow.md`。

期望结果：

- 指出事务/一致性风险。
- 应引用 `gateway.charge` 失败路径和 `inventory.release` 被删除这一事实。

不应误报：

- 不应要求所有支付都使用数据库事务；这个 demo 用的是抽象 `InventoryStore`。
