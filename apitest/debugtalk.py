import time
import json
import os

from httprunner import __version__
from data.vote_play_db import data


def get_httprunner_version():
    return __version__


def sum_two(m, n):
    return m + n


def sleep(n_secs):
    time.sleep(n_secs)


def base_url():
    HOST = "http://test"
    PORT = 8080
    return f"{HOST}:{PORT}"


def check_bytes(content):
    return content.decode() == 'ok'


def read_vote_db(index):
    return json.dumps(data[index]["content"])
