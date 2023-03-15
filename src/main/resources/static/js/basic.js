let host = 'http://' + window.location.host;
$(document).ready(function () {
//     const auth = getToken();
//
//     if (auth !== '') {
//         // 로그인한 유저 이름
//         $.ajax({
//             type: 'GET',
//             url: `/api/user-info`,
//             beforeSend: function (xhr) {
//                 xhr.setRequestHeader("Authorization", auth);
//             },
//             success: function (response) {
//                 if (response === 'fail') {
//                     logout();
//                     window.location.reload();
//                 } else {
//                     $('#username').text(response);
//                 }
//             },
//             error(error, status, request) {
//                 console.error(error);
//                 logout();
//                 window.location.href = host + "/member/login-page";
//             }
//         });
//     }
// })
//
// function showAddPostForm(){
//     const auth = getToken();
//     if(auth===''){
//         return;
//     }
//
//     $.ajax({
//         type: 'GET',
//         url: `/api/posts/new`,
//         beforeSend: function (xhr) {
//             xhr.setRequestHeader("Authorization", auth);
//         }
//     });
})

function logout(check) {
    // 토큰 값 ''으로 덮어쓰기
    document.cookie =
        'Authorization' + '=' + '' + ';path=/';
    if (check) {
        window.location.reload();
    }
}

function getToken() {
    let cName = 'Authorization' + '=';
    let cookieData = document.cookie;
    let cookie = cookieData.indexOf('Authorization');
    let auth = '';
    if (cookie !== -1) {
        cookie += cName.length;
        let end = cookieData.indexOf(';', cookie);
        if (end === -1) end = cookieData.length;
        auth = cookieData.substring(cookie, end);
    }

    // // kakao 로그인 사용한 경우 Bearer 추가
    // if(auth.indexOf('Bearer') === -1 && auth !== ''){
    //     auth = 'Bearer ' + auth;
    // }

    return auth;
}