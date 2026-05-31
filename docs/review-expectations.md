# Review Expectations

这里记录每个测试 PR 中故意埋入的问题，方便和 AI PR Review 助手输出做对比。

main 分支不包含这些问题。

## PR 4: low signal profile display name

分支：`case/low-signal-profile-field`

预期问题：

- 这个 PR 只是在 `readMyProfile` 响应中增加 `displayName`。
- 没有明显安全、事务、并发或可靠性风险。

期望结果：

- 应输出 `no_issue` 或非常低风险总结。
- 不应强行生成“建议加测试”“建议优化命名”这类低信号评论。

不应误报：

- 不应说这是敏感信息泄露，`displayName` 是由 `userId` 构造的展示字段。
