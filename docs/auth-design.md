# Auth Design

用户登录后只返回短期 access token。服务端内部的 session secret 和 refresh token 不允许返回给前端。

所有需要用户身份的接口必须调用 `requireUserSession`，并且不能接受客户端传入的 `userId` 作为最终授权依据。
