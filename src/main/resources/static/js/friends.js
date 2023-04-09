$(function () {
    $(".friendsDel-btn").click(friendsDel);
});
function friendsDel() {
    var fId = document.getElementById("friendsId").value
    sendGET("friends/delete/"+fId, "", function (data) {
        location.reload();
    });
    location.reload();
}