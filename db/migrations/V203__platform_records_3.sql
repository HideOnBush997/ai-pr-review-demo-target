create table platform_records_3 (
  id varchar(64) primary key,
  tenant_id varchar(64) not null,
  owner_id varchar(64) not null,
  status varchar(32) not null,
  updated_at timestamp not null
);
