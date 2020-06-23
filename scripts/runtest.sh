#!/usr/bin/env sh

# run test

USER=root
PWD=""
DATABASE=voter

reset_db() {
    # create database vote
    mysql -u $USER --password=$PWD -e "drop database if exists $DATABASE; create database $DATABASE"
    # create tables and auth user data
    mysql -u $USER --password=$PWD -D $DATABASE < apitest/data/create_tables_and_auth.sql
}

apitest() {
    # no data test 1
    hrun apitest/testcases/no_backend_data_request.yml --html=apitest/reports/no_backend_data_request_report.html --self-contained-html
    # insert vote data
    mysql -u $USER --password=$PWD -D $DATABASE < apitest/data/insert_vote_datas.sql
    # test 2
    # mysql -u $USER --password=$PWD -e "truncate table \`$DATABASE\`.\`t_vote\`"
    hrun apitest/testcases/with_backend_data_request.yml --html=apitest/reports/with_backend_data_request_report.html --self-contained-html
}

uitest() {
    # insert vote data
    mysql -u $USER --password=$PWD -D $DATABASE < apitest/data/insert_vote_datas.sql

    java -jar uitest/target/uitest-0.0.1-SNAPSHOT.jar
}


echo "init test..."
reset_db

echo "run apitest..."
apitest

echo "reset db..."
reset_db

echo "run uitest..."
uitest
