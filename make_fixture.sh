#!/bin/bash

pg_dump dspace --insert --use-set-session-authorization > tmp_fixture

sed -e '/^SET/ d' -e '/^REVOKE/ d' -e '/^GRANT/ d' tmp_fixture > src/test/resources/fixtures/$1.sql
rm tmp_fixture
