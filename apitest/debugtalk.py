import time
import re
import requests

from httprunner import __version__


def get_httprunner_version():
    return __version__


def sum_two(m, n):
    return m + n


def sleep(n_secs):
    time.sleep(n_secs)


def _get_csrf_and_session():
    url = "http://test:8083/login"
    response = requests.get(url)
    return {
        "csrf": response.headers.get("X-CSRF-TOKEN"),
        "session": response.cookies['SESSION']
    }


def get_login_session():
    url = "http://test:8083/index.html"
    prework = _get_csrf_and_session()
    headers = {
        "Content-Type": "application/x-www-form-urlencoded",
        "Cookie": "SESSION=" + prework["session"],
    }
    payload = {"_csrf": prework["csrf"], "password": "123321", "username": "huang"}
    response = requests.post(url, headers=headers, data=payload)
    # 302
    history = response.history[0]
    return {"login_session": history.cookies.get('SESSION')}


def check_bytes(content):
    return content.decode() == 'ok'