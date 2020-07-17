# NOTE: Generated By HttpRunner v3.1.3
# FROM: testcases/with_backend_data_request.yml


import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).parent.parent))


import pytest
from httprunner import Parameters


from httprunner import HttpRunner, Config, Step, RunRequest, RunTestCase

from testcases.get_login_session_test import TestCaseGetLoginSession as GetLoginSession


class TestCaseWithBackendDataRequest(HttpRunner):
    @pytest.mark.parametrize(
        "param",
        Parameters({"username-password": "${parameterize(testcases/account.csv)}"}),
    )
    def test_start(self, param):
        super().test_start(param)

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
            .assert_equal("body.code", "0")
            .assert_not_equal("body.data", "[]")
        ),
        Step(
            RunRequest("bad post vote play with error form values")
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
                    "form": '[{"name1":"1","value1":"1"},{"name":"1","value1":"2"},{"name1":"2","value":"4"}]'
                }
            )
            .validate()
            .assert_equal("status_code", 200)
            .assert_equal("body.code", "-2")
        ),
        Step(
            RunRequest("bad post vote play with no exist item")
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
                    "form": '[{"name":"5","value":"1"},{"name":"6","value":"2"},{"name":"2","value":"1"}]'
                }
            )
            .validate()
            .assert_equal("status_code", 200)
            .assert_equal("body.code", "-2")
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
            .assert_equal("body.code", "0")
        ),
        Step(
            RunRequest("post vote play again")
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
            .assert_equal("body.code", "-3")
        ),
        Step(
            RunRequest("get vote display after voter play")
            .get("/voter/display")
            .with_headers(**{"Cookie": "SESSION=$login_session"})
            .extract()
            .with_jmespath('headers."X-CSRF-TOKEN"', "csrf_token")
            .validate()
            .assert_equal("status_code", 200)
            .assert_equal("body.code", "-3")
        ),
        Step(
            RunRequest("get vote play")
            .get("/voter/play")
            .with_headers(
                **{"Cookie": "SESSION=$login_session", "X-CSRF-TOKEN": "$csrf_token"}
            )
            .validate()
            .assert_equal("status_code", 200)
            .assert_equal("body.code", "0")
            .assert_not_equal("body.data", "[]")
        ),
        Step(
            RunRequest("get csrf used by ajax")
            .get("/voter/csrftoken")
            .with_headers(**{"Cookie": "SESSION=$login_session"})
            .extract()
            .with_jmespath("body", "data")
            .validate()
            .assert_equal("status_code", 200)
            .assert_not_equal('headers."X-CSRF-TOKEN"', "None")
            .assert_equal("${check_bytes($data)}", True)
        ),
    ]


if __name__ == "__main__":
    TestCaseWithBackendDataRequest().test_start()