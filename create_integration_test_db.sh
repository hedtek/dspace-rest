#!/bin/bash
echo "Please use 'dspace' as the password"
createuser dspace-integ -D -S -R -P
createdb dspace-integ -O dspace-integ
