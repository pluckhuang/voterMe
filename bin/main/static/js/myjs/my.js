let Tex = {
    radioBtn: function () {
        $("#filterDay .btn").on("click", function () {
            let ele = $(this).find('input');
            let val = ele.attr('id');
            console.log(val);
        });
    }
}

$(document).ready(Tex.radioBtn);
