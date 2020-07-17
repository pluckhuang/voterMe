# NOTE: Generated By HttpRunner v3.1.3
# FROM: performance_test/post_data_request.yml


import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent.parent))


from httprunner import HttpRunner, Config, Step, RunRequest, RunTestCase

from performance_test.get_login_session_test import (
    TestCaseGetLoginSession as GetLoginSession,
)


class TestCasePostDataRequest(HttpRunner):

    config = Config("with_backend_data_request").base_url("${base_url()}").verify(False)

    teststeps = [
        Step(
            RunTestCase("get login session")
            .call(GetLoginSession)
            .export(*["login_session"])
        ),
        Step(
            RunRequest("/index.html")
            .get("/index.html")
            .with_headers(**{"Cookie": "SESSION=$login_session"})
            .validate()
            .assert_equal("status_code", 200)
        ),
        Step(
            RunRequest("get vote display")
            .get("/voter/display")
            .with_headers(**{"Cookie": "SESSION=$login_session"})
            .extract()
            .with_jmespath('headers."X-CSRF-TOKEN"', "csrf_token")
            .validate()
            .assert_equal("status_code", 200)
            .assert_not_equal("body.data", "[]")
        ),
        Step(
            RunRequest("post vote play")
            .post("/voter/play")
            .with_headers(
                **{
                    "Cookie": "SESSION=$login_session",
                    "Content-Type": "application/x-www-form-urlencoded",
                    "X-CSRF-TOKEN": "$csrf_token",
                }
            )
            .with_data(
                {
                    "form": '[{"name":"1","value":"1"},{"name":"1","value":"2"},{"name":"2","value":"4"}]'
                }
            )
            .validate()
            .assert_equal("status_code", 200)
        ),
    ]


if __name__ == "__main__":
    TestCasePostDataRequest().test_start()
