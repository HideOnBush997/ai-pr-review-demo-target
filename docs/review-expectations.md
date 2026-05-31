# Review Expectations

这里记录每个测试 PR 中故意埋入的问题，方便和 AI PR Review 助手输出做对比。

main 分支不包含这些问题。

## PR 3: cache refresh race

分支：`case/cache-refresh-race`

预期问题：

- `src/cache/cache.service.ts` 中 `getOrRefresh` 创建了 `pending`，但没有复用它。
- 相同 key 并发 miss 时会重复调用 `refresh()`，还会把第二次结果覆盖第一次。
- `this.refreshes` 写入后没有在读路径使用，属于并发控制失效。

期望结果：

- 指出重复调用 `refresh()` 或竞态风险。
- 最好提示应先检查 `this.refreshes.get(key)` 并复用 pending promise。

不应误报：

- 不应把普通 `get/set` TTL 逻辑当成问题。
