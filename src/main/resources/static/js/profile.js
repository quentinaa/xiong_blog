$(function () {

    $(".follow-btn").click(follow);
    $(".friends-btn").click(friends);
});

function follow() {
    let id = $(this).prev().val();
    sendGET("follower/click/" +id, "", function (data) {
        location.reload();
    });
    // var btn = this;
    // if ($(btn).hasClass("btn-info")) {
    //     // 关注TA
    //     $.post(
    //         CONTEXT_PATH + "/follow",
    //         {"entityType": 3, "entityId":},
    //         function (data) {
    //             data = $.parseJSON(data);
    //             if (data.code == 0) {
    //                 // 偷个懒，直接刷新界面
    //                 window.location.reload();
    //             } else {
    //                 alert(data.msg);
    //             }
    //         }
    //     )
    // } else {
    //     // 取消关注
    //     $.post(
    //         CONTEXT_PATH + "/unfollow",
    //         {"entityType": 3, "entityId": $(btn).prev().val()},
    //         function (data) {
    //             data = $.parseJSON(data);
    //             if (data.code == 0) {
    //                 // 偷个懒，直接刷新界面
    //                 window.location.reload();
    //             } else {
    //                 alert(data.msg);
    //             }
    //         }
    //     )
    // }
}

function friends() {
    const id = document.getElementById("entityId").value
    sendGET("friends/click/" + id, "", function (data) {
        location.reload();
    });
}


