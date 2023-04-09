$(function () {
    $("#topBtn").click(updateTop);
    $("#wonderfulBtn").click(setWonderful);
    $("#deleteBtn").click(setDelete);
});
/**
 * topic
 * @param btn 点赞的按钮
 * @param entityType 点赞的实体(1:帖子，2：评论)
 * @param entityId  点赞的实体ID(帖子id/评论id)
 * @param entityUserId 通知的用户id(帖子的作者/评论的作者)
 * @param postId 和点赞实体
 */
function like(btn, entityType, entityId, entityUserId, postId) {

    // 1.封装参数
    var param = new Object();
    param.entityType=entityType;
    param.entityId=entityId;
    param.entityUserId=entityUserId;

    sendPost("like/clickLike", param, function (data) {
        $(btn).children("i").text(data.likeCount);
        $(btn).children("b").text(data.likeStatus == 1 ? '已赞' : '赞');
    });
}

// 置顶 or 取消置顶
function updateTop() {
    $.post(
        CONTEXT_PATH + "/discuss/top",
        {
            "id": $("#postId").val(),
            // $("#postType").val() 帖子当前的 type
            "type": ($("#postType").val() == 1) ? 0 : 1
        },
        function (data) {
            data = $.parseJSON(data);
            if (data.code == 0) {
                // 偷个懒，直接刷新界面
                window.location.reload();
            } else {
                alert(data.msg);
            }
        }
    )
}

// 加精
function setWonderful() {
    $.post(
        CONTEXT_PATH + "/discuss/wonderful",
        {"id": $("#postId").val()},
        function (data) {
            data = $.parseJSON(data);
            if (data.code == 0) {
                // 加精成功后，将加精按钮设置为不可用
                $("#wonderfulBtn").attr("disabled", "disable")
            } else {
                alert(data.msg);
            }
        }
    )
}

// 删除
function setDelete() {
    $.post(
        CONTEXT_PATH + "/discuss/delete",
        {"id": $("#postId").val()},
        function (data) {
            data = $.parseJSON(data);
            if (data.code == 0) {
                // 删除成功后，跳转到首页
                location.href = CONTEXT_PATH + "/index";
            } else {
                alert(data.msg);
            }
        }
    )
}