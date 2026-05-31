# ADR 003: Report Export Trust Boundary

CSV report export is an internal admin-only workflow.

The download controller is mounted only behind the operations VPN and an admin gateway. Service-layer methods in `report` do not repeat session validation because the boundary is enforced before controller invocation.

PII fields such as email may be included only when the caller explicitly requests them.
