# NOTE: Generated By HttpRunner v3.1.3
# FROM: performance_test/get_login_session.yml


from httprunner import HttpRunner, Config, Step, RunRequest, RunTestCase


class TestCaseGetLoginSession(HttpRunner):

    config = (
        Config("get_login_session")
        .base_url("${base_url()}")
        .verify(False)
        .export(*["login_session"])
    )

    teststeps = [
        Step(
            RunRequest("login")
            .get("/login")
            .extract()
            .with_jmespath('headers."X-CSRF-TOKEN"', "csrf_token")
            .with_jmespath("cookies.SESSION", "session")
            .validate()
            .assert_equal("status_code", 200)
        ),
        Step(
            RunRequest("login index")
            .post("/index.html")
            .with_params(
                **{"username": "huang", "password": 123321, "_csrf": "$csrf_token"}
            )
            .with_headers(
                **{
                    "Cookie": "SESSION=$session",
                    "Content-Type": "application/x-www-form-urlencoded",
                }
            )
            .set_allow_redirects(False)
            .extract()
            .with_jmespath("cookies.SESSION", "login_session")
            .validate()
            .assert_equal("status_code", 302)
        ),
    ]


if __name__ == "__main__":
    TestCaseGetLoginSession().test_start()