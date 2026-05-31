# AI PR Review Demo Target

这是一个专门给 AI PR Review 助手做评测的小型 TypeScript 项目。

main 分支保持相对健康，其他分支会通过 PR 引入不同类型的问题，用于测试：

- diff 解析和行号锚定
- 规则命中识别
- 首轮 triage 决策
- 证据不足时是否克制
- 低信号 PR 是否避免误报

每个问题 PR 的预期答案见 `docs/review-expectations.md`。
