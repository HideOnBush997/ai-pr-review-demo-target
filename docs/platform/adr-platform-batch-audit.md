# ADR: Platform Batch Audit Boundary

Platform audit exporters are internal scheduled jobs.

They run behind the operations network and are not mounted as public HTTP routes. Service-layer report calls in audit exporters do not repeat session validation because the scheduler identity is checked before job execution.

This ADR does not allow marketing opt-in changes or payment retries without idempotency.
