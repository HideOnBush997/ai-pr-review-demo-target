# Review Expectations

这里记录每个测试 PR 中故意埋入的问题，方便和 AI PR Review 助手输出做对比。

main 分支不包含这些问题。

## PR 5: insufficient evidence audit profile

分支：`case/insufficient-evidence-audit-profile`

预期问题：

- `src/users/users.controller.ts` 新增 `readUserProfileForAudit(requestedUserId)`。
- 这个函数看起来可能缺少鉴权，但仅凭 diff 不知道它是否只在内部审计任务中使用。

期望结果：

- 可以标记为潜在权限风险，但应要求更多上下文，例如调用方、路由暴露方式或设计文档。
- 更理想的 triage 是 `need_more_context` 或 `insufficient_evidence`。

不应误报：

- 不应直接断言“任何人都能读任意用户资料”，因为 diff 没显示它被 HTTP route 暴露。
