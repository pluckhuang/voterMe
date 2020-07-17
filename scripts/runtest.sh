#!/usr/bin/env sh

# run test

USER=root
PWD=""
DATABASE=voter

echo "run test..."
# create database vote
mysql -u $USER --password=$PWD -e "drop database if exists $DATABASE; create database $DATABASE"
# create tables and auth user data
mysql -u $USER --password=$PWD -D $DATABASE < apitest/data/create_tables_and_antu_2020-07-17.sql
# no data test 1
hrun apitest/testcases/no_backend_data_request.yml --html=apitest/reports/no_backend_data_request_report.html --self-contained-html
# insert test data
mysql -u $USER --password=$PWD -D $DATABASE < apitest/data/insert_vote_datas_2020-07-17.sql
# test 2
# mysql -u $USER --password=$PWD -e "truncate table \`$DATABASE\`.\`t_vote\`"
hrun apitest/testcases/with_backend_data_request.yml --html=apitest/reports/with_backend_data_request_report.html --self-contained-html

