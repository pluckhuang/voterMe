#!/usr/bin/env sh

# run test

echo "run test..."

mysql -u root -e "truncate table \`voter\`.\`t_vote\`"

hrun -s apitest/testcases/api1.yml