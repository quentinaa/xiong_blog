var baseUrl = "http://localhost:8001/"

// 提交表单
function submitForm(url, form, callback) {

    // 1.获取表单数据
    var param = getFormData(form);

    // 2.发送请求
    sendPost(url, param, callback);
}

function sendPost(url, param, callback) {
    request(url, "POST", param, callback);
}

function sendGET(url, param, callback) {
    request(url, "GET", param, callback);
}

// 发送请求的方法
function request(url, method, data, callback) {
    $.ajax({
        url: baseUrl + url, // 请求地址
        type: method, // 请求方式
        dataType: "JSON", // 服务端返回的数据为json
        contentType: "application/json", // 传递给服务端的数据编码是json
        data: JSON.stringify(data), // 请求携带数据
        success: function (resp) {
            if (resp.code == 0) { // 成功
                callback(resp); // 处理成功后续该如何完成，又调用者来完成
            } else {
                console.error("请求处理失败:" + resp.msg);
            }
        },
        error: function () {
        }
    });
}


// 获取表单数据
function getFormData(form) {

    // 1.获取表单的中数据，返回的是个对象数据，name(表单中key),value(表单值)
    var array = form.serializeArray();

    // 2.准备一个对象
    var param = new Object();
    // param.name = "admin";

    // 3.循环遍历，把数组中的数据封装到param对象中
    for (var i = 0; i < array.length; i++) {
        param[array[i].name] = array[i].value; // param.name = "admin"
    }

    return param;
}
