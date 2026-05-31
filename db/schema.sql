create table user_notification_preferences (
  user_id varchar(64) primary key,
  marketing_enabled boolean not null default false,
  security_enabled boolean not null default true,
  product_enabled boolean not null default true
);

create table checkout_retry_config (
  id varchar(32) primary key,
  max_attempts integer not null,
  timeout_millis integer not null,
  retry_on_payment_timeout boolean not null default true,
  constraint checkout_retry_max_attempts check (max_attempts between 1 and 5),
  constraint checkout_retry_timeout check (timeout_millis between 100 and 5000)
);
