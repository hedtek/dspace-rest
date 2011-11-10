#!/bin/bash

pg_dump dspace --insert --use-set-session-authorization > tmp_fixture

sed -e '/^SET/ d' -e '/^REVOKE/ d' -e '/^GRANT/ d' -e "s/^\(INSERT INTO bitstream VALUES.*'\).*\(\/upload.*$\)/\1\$\{resource.test.base\}\2/" tmp_fixture > src/test/resources-1.5.2/fixtures/$1.sql
rm tmp_fixture
