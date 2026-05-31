# AI PR Review 测试用例总表

本文档是人工对照用答案表，放在 `main` 分支中，方便比较 AI PR Review 助手的输出质量。

注意：

- `main` 分支代码本身是健康基线。
- 每个测试 PR 只引入一个主要问题类型。
- `docs/review-expectations.md` 在每个 PR 分支里只记录当前 PR 的期望结果。
- 本文件汇总所有 PR 的答案，不建议把它作为模型审查当前 PR 时的输入上下文，否则会泄露答案。

## 总览

| PR | 分支 | 场景 | 理想 triage | 主要检查点 |
| --- | --- | --- | --- | --- |
| [#1](https://github.com/HideOnBush997/ai-pr-review-demo-target/pull/1) | `case/security-auth-bypass` | 鉴权绕过和 secret 泄露 | `need_more_context` 或 `final_review` | 是否发现 admin session 可被任意创建，以及 secret 被返回 |
| [#2](https://github.com/HideOnBush997/ai-pr-review-demo-target/pull/2) | `case/payment-rollback-missing` | 支付失败回滚缺失 | `final_review` 或 `need_more_context` | 是否发现失败路径中库存不释放 |
| [#3](https://github.com/HideOnBush997/ai-pr-review-demo-target/pull/3) | `case/cache-refresh-race` | 缓存刷新并发竞态 | `final_review` 或 `need_more_context` | 是否发现 `refresh()` 重复调用和 pending promise 未复用 |
| [#4](https://github.com/HideOnBush997/ai-pr-review-demo-target/pull/4) | `case/low-signal-profile-field` | 低风险字段新增 | `no_issue` | 是否避免硬造低价值评论 |
| [#5](https://github.com/HideOnBush997/ai-pr-review-demo-target/pull/5) | `case/insufficient-evidence-audit-profile` | 证据不足的潜在权限风险 | `need_more_context` 或 `insufficient_evidence` | 是否克制，不直接断言外部可访问 |

## PR #1: 鉴权绕过和 secret 泄露

链接：[PR #1](https://github.com/HideOnBush997/ai-pr-review-demo-target/pull/1)

分支：`case/security-auth-bypass`

变更文件：

- `src/auth/auth.service.ts`
- `docs/review-expectations.md`

关键定位：

- `src/auth/auth.service.ts`
  - 新增函数：`debugLogin(userId: string)`
  - 核心问题语句：`createSession(userId, "admin")`
  - 核心问题语句：`sessionSecret: \`secret-${userId}\``

问题说明：

1. `debugLogin` 接收任意 `userId`，直接创建 `admin` session。
2. 这绕过了正常登录、身份校验和权限来源验证。
3. 返回值中包含 `sessionSecret`，违反 `docs/auth-design.md` 中“session secret 不允许返回给前端”的设计约束。

期望 AI Review 输出：

- 指出“任意 userId 可获得 admin session”。
- 指出“sessionSecret 被返回，存在敏感信息泄露”。
- 严重级别建议为 `HIGH`。
- 类别建议为 `security`。
- 合理建议：
  - 删除 `debugLogin`。
  - 如果确实是测试辅助函数，应仅在测试环境编译或受内部鉴权保护。
  - 不要返回 `sessionSecret`。

不应误报：

- 不应把 main 分支已有的 `createSession` 基线逻辑当成问题。
- 不应只泛泛说“建议增强鉴权”，必须指出具体绕过点。

## PR #2: 支付失败回滚缺失

链接：[PR #2](https://github.com/HideOnBush997/ai-pr-review-demo-target/pull/2)

分支：`case/payment-rollback-missing`

变更文件：

- `src/order/order.service.ts`
- `docs/review-expectations.md`

关键定位：

- `src/order/order.service.ts`
  - 仍保留：`await this.inventory.reserve(order.sku, order.quantity)`
  - 仍保留：`await this.gateway.charge(order.id, order.amountCents)`
  - 被删除：`catch (error) { await this.inventory.release(...); throw error; }`

问题说明：

1. 支付前会预留库存。
2. 新代码删除了 `try/catch` 回滚逻辑。
3. 如果 `gateway.charge(...)` 抛错，库存不会释放。
4. 这违反 `docs/payment-flow.md` 中“任一步失败必须释放库存预留”的业务约束。

期望 AI Review 输出：

- 指出支付失败路径中缺少 `inventory.release`。
- 指出影响是库存被永久预留或库存数量不一致。
- 严重级别建议为 `HIGH` 或 `MEDIUM`。
- 类别建议为 `bug` 或 `concurrency`。
- 合理建议：
  - 恢复 `try/catch`。
  - 在 `catch` 中调用 `inventory.release(order.sku, order.quantity)`。
  - 保持原始错误继续抛出。

不应误报：

- 不应强行要求数据库事务，因为 demo 使用的是抽象 `InventoryStore`。
- 不应只说“建议增加日志”，这不是核心问题。

## PR #3: 缓存刷新并发竞态

链接：[PR #3](https://github.com/HideOnBush997/ai-pr-review-demo-target/pull/3)

分支：`case/cache-refresh-race`

变更文件：

- `src/cache/cache.service.ts`
- `docs/review-expectations.md`

关键定位：

- `src/cache/cache.service.ts`
  - 新增字段：`private readonly refreshes = new Map<string, Promise<T>>()`
  - 新增函数：`getOrRefresh(...)`
  - 核心问题语句：`const pending = refresh()`
  - 核心问题语句：`this.refreshes.set(key, pending)`
  - 核心问题语句：`const value = await refresh()`

问题说明：

1. 代码创建了 `pending = refresh()`，但后续没有 `await pending`。
2. 紧接着又执行了一次 `await refresh()`，导致一次 cache miss 调用两次 refresh。
3. `this.refreshes` 只写入不读取，无法合并并发请求。
4. 多个请求同时 miss 同一 key 时，会重复打后端，甚至互相覆盖结果。

期望 AI Review 输出：

- 指出 `refresh()` 被调用两次。
- 指出 `pending` promise 没有被复用。
- 指出 `this.refreshes` 缺少读取路径，不能防止并发重复刷新。
- 严重级别建议为 `MEDIUM`。
- 类别建议为 `concurrency` 或 `performance`。
- 合理建议：
  - 先检查 `this.refreshes.get(key)`。
  - 如果已有 pending，直接返回它。
  - 新建 pending 时应 `await pending`，不要第二次调用 `refresh()`。
  - 用 `finally` 删除 pending，避免失败时残留。

不应误报：

- 不应把 `get` / `set` 的 TTL 基础逻辑当成问题。
- 不应只说“Map 可能内存泄漏”，真正问题是 pending promise 未复用。

## PR #4: 低风险字段新增

链接：[PR #4](https://github.com/HideOnBush997/ai-pr-review-demo-target/pull/4)

分支：`case/low-signal-profile-field`

变更文件：

- `src/users/users.controller.ts`
- `docs/review-expectations.md`

关键定位：

- `src/users/users.controller.ts`
  - 在 `readMyProfile` 返回值中新增：`displayName: \`User ${session.userId}\``

问题说明：

1. 这是低风险展示字段新增。
2. 它基于当前已鉴权 session 的 `userId` 构造。
3. 没有明显安全、事务、并发或可靠性风险。

期望 AI Review 输出：

- 理想 triage 为 `no_issue`。
- 可以生成简短 PR summary，但不应生成 inline comment。
- 如果系统必须输出风险，应为 `LOW` 或 `INFO`，并说明无阻塞问题。

不应误报：

- 不应说这是敏感信息泄露。
- 不应说需要管理员权限。
- 不应输出“建议增加测试”“建议优化命名”这类低信号评论。

## PR #5: 证据不足的潜在权限风险

链接：[PR #5](https://github.com/HideOnBush997/ai-pr-review-demo-target/pull/5)

分支：`case/insufficient-evidence-audit-profile`

变更文件：

- `src/users/users.controller.ts`
- `docs/review-expectations.md`

关键定位：

- `src/users/users.controller.ts`
  - 新增函数：`readUserProfileForAudit(requestedUserId: string)`
  - 核心可疑点：函数接收任意 `requestedUserId`
  - 核心可疑点：函数内部没有调用 `requireUserSession` 或 `requireAdminSession`

问题说明：

1. 仅从 diff 看，这个函数可能缺少鉴权。
2. 但函数名包含 `ForAudit`，它也可能只被内部审计任务调用。
3. diff 没有显示路由绑定、调用方或外部暴露方式。
4. 因此不能直接断言“外部用户可读取任意用户资料”。

期望 AI Review 输出：

- 可以指出“潜在权限风险”。
- 应要求更多上下文：
  - 是否被 HTTP route 暴露。
  - 调用方是否已经做了 admin/session 校验。
  - 审计任务是否只在可信内部环境运行。
- 理想 triage 为 `need_more_context` 或 `insufficient_evidence`。
- 严重级别如果输出，应不高于 `MEDIUM`，除非拿到调用方证据。

不应误报：

- 不应直接断言“任何人都可以读取任意用户资料”。
- 不应在没有调用方证据时给出确定性 `HIGH security` 评论。
- 不应要求删除函数本身，而应建议补鉴权或补调用方证据。

## 建议评分方式

可以用下面维度人工评估 AI PR Review 助手输出：

1. 是否找到目标问题。
2. 是否定位到正确文件和 diff 行。
3. 是否说明故障条件。
4. 是否说明实际影响。
5. 是否给出可执行修复建议。
6. 是否避免低信号评论。
7. 是否在证据不足时保持克制。

简单评分建议：

- 2 分：准确指出核心问题，定位和影响都清楚。
- 1 分：方向正确，但定位、影响或建议不够具体。
- 0 分：漏报、误报或输出泛泛建议。
