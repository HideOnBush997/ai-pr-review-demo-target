# Review Expectations

这里记录每个测试 PR 中故意埋入的问题，方便和 AI PR Review 助手输出做对比。

main 分支不包含这些问题。

## PR 1: security auth bypass

分支：`case/security-auth-bypass`

预期问题：

- `src/auth/auth.service.ts` 中新增的 `debugLogin` 允许任意 `userId` 直接创建 admin session。
- `debugLogin` 把 `sessionSecret` 返回给调用方，违反 `docs/auth-design.md`。

期望结果：

- 至少指出鉴权绕过或敏感 secret 泄露。
- 最好输出 `need_more_context`，因为需要结合 `docs/auth-design.md` 或调用方确认是否暴露到外部。

不应误报：

- 不应把 `createSession` 基线逻辑当成新增问题。
