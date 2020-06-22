let timer = [];

let voteDisplay = {
    onReady: function () {
        $.when(
            $.ajax({
                // The URL for the request
                url: "/voter/display",

                // The data to send (will be converted to a query string)
                //data: {},

                // Whether this is a POST or GET request
                type: "GET",

                // The type of data we expect back
                dataType: "json",
            })
                // Code to run if the request succeeds (is done);
                // The response is passed to the function
                .done(function (json) {
                    let displayContent = "";
                    let validSec = "";
                    let createTime = "";
                    if (json.code === '0') {
                        for (val in json.data) {
                            let title = json.data[val].title;
                            let itemList = json.data[val].itemList;
                            validSec = json.data[val].validSec;
                            createTime = json.data[val].createTime;
                            displayContent += voteDisplay.inputCheckBox(title, itemList);
                        }
                        let distance = voteDisplay.countdown(createTime, validSec);
                        console.log(distance);
                        if (parseInt(distance, 10) > 0) {
                            displayContent += voteDisplay.submitBtn();
                            $("#content_display").html(displayContent);
                        }
                    }
                    else if (json.code === "-3") {

                        voteDisplay.intervalVoteRet();
                    }
                })
                // Code to run if the request fails; the raw request and
                // status codes are passed to the function
                .fail(function (xhr, status, errorThrown) {
                })

        ).then(voteDisplay.successFunc, voteDisplay.failureFunc)
        // Code to run regardless of success or failure;
        // .always(function (xhr, status) {
        //     alert("The request is complete!");
        // });
        $("#vote").on("submit", voteDisplay.onFormSubmit);
    },

    successFunc: function () {
    },

    failureFunc: function () {
        console.log(timer);
        for (el in timer) {
            clearInterval(timer[el]);
        }
    },

    inputCheckBox: function (title, itemlist) {
        let head = `<div class="row">`;
        let label = `<legend class="col-form-label col-12 pt-0">${title}</legend>`;
        let checkStart = `<div class="col-12">`;
        let checkEnd = `</div>`;
        let bottom = `</div>`;
        let checkboxs = '';

        for (item in itemlist) {
            let questId = itemlist[item].questId;
            let id = itemlist[item].id;
            let content = itemlist[item].content;
            let checkbox =
                `<div class="form-check">` +
                `<input class="form-check-input" type="checkbox" name=${questId} id=${questId}_${id} value=${id}>` +
                `<label class="form-check-label" for=${questId}_${id}>${content}</label>` +
                `</div>`
            checkboxs += checkbox;
        }

        let ret = head + label + checkStart + checkboxs + checkEnd + bottom + "<br/>";
        return ret;
    },

    submitBtn: function () {
        let submitBtn = `<div class="form-group row">` +
            `<div class="col-3">` +
            `<button type="submit" class="btn btn-primary">submit</button>` +
            `</div>` +
            `</div>`
        return submitBtn;
    },

    onFormSubmit: function (event) {

        event.preventDefault(); // avoid to execute the actual submit of the form.

        let form = $(this);

        $.when($.get('/voter/csrftoken')).then(
            function (data, textStatus, jqXHR) {
                if (jqXHR.status == "200") {
                    let token = jqXHR.getResponseHeader('X-CSRF-TOKEN');
                    let headers = {
                        'X-CSRF-TOKEN': token
                    };
                    $.ajax({
                        type: 'POST',
                        url: '/voter/play',
                        data: {
                            'form': JSON.stringify(form.serializeArray()),
                        }, // serializes the form's elements.
                        dataType: 'json',
                        headers: headers
                    })
                        .done(function (json) {

                            if (json.code === '-3') {
                                console.log(voteDisplay.alreadyVote());
                            }
                            voteDisplay.intervalVoteRet();

                        })
                        // Code to run if the request fails; the raw request and
                        // status codes are passed to the function
                        .fail(function (xhr, status, errorThrown) {
                            //console.log(xhr, status, errorThrown);
                            //alert('Sorry, there was a problem!');
                        })
                    // .always(function (xhr, status) {
                    //     alert('The request is complete!');
                    // });

                }
            },
            this.failureFunc
        );
    },

    intervalVoteRet: function () {
        let x = setInterval(function () {
            voteDisplay.getVoteResult();
        }, 1000);
        timer.push(x);
    },

    getVoteResult: function () {
        let displayContent = "";
        $.when($.get('/voter/csrftoken')).then(
            function (data, textStatus, jqXHR) {
                let token = jqXHR.getResponseHeader('X-CSRF-TOKEN');
                let headers = {
                    'X-CSRF-TOKEN': token
                };
                $.ajax({
                    // The URL for the request
                    url: "/voter/play",

                    // Whether this is a POST or GET request
                    type: "GET",

                    // The type of data we expect back
                    dataType: "json",
                    headers: headers
                })
                    // Code to run if the request succeeds (is done);
                    // The response is passed to the function
                    .done(function (json) {
                        voteDisplay.voteDisplayChart(json);
                    })
                    // Code to run if the request fails; the raw request and
                    // status codes are passed to the function
                    .fail(function (xhr, status, errorThrown) {
                        // alert("Sorry, there was a problem!");
                    })
            },
            this.failureFunc
        )
    },

    voteContent: function (id, title, itemList) {
        let head = `<div class="row">`;
        let displayContent = `<canvas id="vote${id}" width="col-12" height="50"></canvas>` +
            `<canvas id="vote_${id}" width="col-12"></canvas>`;
        let displayStart = `<div class="col-12">`;
        let displayEnd = `</div>`;
        let bottom = `</div>`;

        let ret = head + displayStart + displayContent + displayEnd + bottom + "<br/>";
        return ret;
    },

    voteDisplayChart: function (json) {

        let etag = $("#etag");
        let jsonStr = JSON.stringify(json.data);
        let dataHash = md5.create().update(jsonStr).hex();

        if (etag.val() != dataHash) {

            let displayContent = "";
            for (val in json.data) {
                let id = json.data[val].id;
                let title = json.data[val].title;
                let itemList = json.data[val].itemList;
                displayContent += voteDisplay.voteContent(id, title, itemList);
            }
            $("#container").html(displayContent);

            for (val in json.data) {
                let id = json.data[val].id;
                let title = json.data[val].title;
                let itemList = json.data[val].itemList;
                let chartLabel = [];
                let chartData = [];

                for (item in itemList) {
                    chartLabel.push(itemList[item].itemContent);
                    chartData.push(itemList[item].voteCount);
                }
                let ctx = $("#vote" + id);

                let myChart = new Chart(ctx, {
                    type: 'horizontalBar',
                    data: {
                        labels: chartLabel,
                        datasets: [{
                            label: title,
                            data: chartData,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ],
                            borderColor: [
                                'rgba(255,99,132,1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                            ],
                            borderWidth: 1,
                        }]
                    },
                    options: {
                        scales: {
                            yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                        }
                    }
                });

                let ctx1 = $("#vote_" + id);

                let myDoughnutChart = new Chart(ctx1, {
                    type: 'doughnut',
                    data: {
                        labels: chartLabel,
                        datasets: [{
                            label: title,
                            data: chartData,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ],
                            borderColor: [
                                'rgba(255,99,132,1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)',
                                'rgba(75, 192, 192, 1)',
                                'rgba(153, 102, 255, 1)',
                                'rgba(255, 159, 64, 1)'
                            ],
                            borderWidth: 1,
                        }]
                    },
                });
            }
            etag.val(dataHash);
        }
    },

    alreadyVote: function () {
        let ret = `<h>` + `you have voted` + `</h>`;
        return ret;
    },

    countdown: function (createTime, validSec) {
        let countDownDate = new Date(createTime).getTime() + 1000 * validSec;

        let now = new Date().getTime();
        // Find the distance between now and the count down date
        let distance = countDownDate - now;
        // Update the count down every 1 second
        let x = setInterval(function () {

            // Get todays date and time
            now = new Date().getTime();

            // Find the distance between now and the count down date
            distance = countDownDate - now;

            // Time calculations for days, hours, minutes and seconds
            let days = Math.floor(distance / (1000 * 60 * 60 * 24));
            let hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            let seconds = Math.floor((distance % (1000 * 60)) / 1000);

            // Display the result in the element with id="countDown"
            let ret = `remaining time: ${days} days ${hours}h ${minutes}m ${seconds}s`

            // If the count down is finished, write some text
            if (distance < 0) {
                clearInterval(x);
                ret = "EXPIRED";
            }
            $("#countDown").html(ret);
        }, 1000);
        return distance;
    }

}

$(document).ready(voteDisplay.onReady);