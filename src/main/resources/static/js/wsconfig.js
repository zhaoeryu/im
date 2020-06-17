var ws_url = 'ws://' + window.location.hostname + ':2048/ws';
var reconnectflag = false;// 避免重复连接
var socket = null;

/**
 * 创建连接
 */
function createWS(url,callback) {
    try {
        if (!window.WebSocket) window.WebSocket = window.MozWebSocket;
        if (window.WebSocket){
            socket = new WebSocket(url);
            callback();
        }else{
            alert('您的浏览器不支持WebSocket协议,无法进行连接')
        }
    }catch (e) {
        reConnectWS(url,callback)
    }
}

/**
 * 重新连接
 */
function reConnectWS(url,callback) {
    if (reconnectflag) return;
    reconnectflag = true;
    setTimeout(function () {
        createWS(url,callback);
        reconnectflag = false;
    },2*1000)
}


/**
 * 发送数据
 * @param cmd 指令
 * @param message 消息体
 */
function sendMessage(cmd,message){
    if (!cmd || !message || message.length < 1){
        return
    }
    if(!window.WebSocket){
        return;
    }
    //当websocket状态打开
    if(socket.readyState == WebSocket.OPEN){
        var msg = {
            cmd: cmd,
            message: JSON.stringify(message)
        }
        socket.send(JSON.stringify(msg));
    }else{
        alert("连接没有开启");
        // 是否进行重新连接
    }
}