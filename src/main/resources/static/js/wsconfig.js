var ws_url = 'ws://' + window.location.hostname + ':2048/ws';
var reconnectflag = false;// 避免重复连接
var socket = null;
var ws_callback = null;

var MSG_CMD = {
    CONNECT: 1, // 1. 第一次(或重连)初始化连接
    CHAT: 2, // 2. 聊天消息
    SIGNED: 3, // 3. 消息签收
    KEEPALIVE: 4,// 4. 客户端保持心跳
    GROUP_MSG: 5 // 5. 群消息
}


/**
 * 创建连接
 */
function createWS(callback) {
    if (!ws_callback){
        ws_callback = callback
    }
    try {
        if (!window.WebSocket) window.WebSocket = window.MozWebSocket;
        if (window.WebSocket){
            socket = new WebSocket(ws_url);
            callback();
        }else{
            alert('您的浏览器不支持WebSocket协议,无法进行连接')
        }
    }catch (e) {
        reConnectWS(callback)
    }
}

/**
 * 重新连接
 */
function reConnectWS(callback) {
    if (reconnectflag) return;
    reconnectflag = true;
    setTimeout(function () {
        createWS(callback);
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
        if(cmd !== 4){
            alert("连接没有开启");
        }else{
            // 心跳包尝试重连
            reConnectWS(ws_url,ws_callback)
        }
    }
}

/**
 * 每20秒进行心跳检查
 */
function keepAlive(mine) {
    setInterval(() => {
        console.log('keep-alive , id = '+mine.id);
        sendMessage(MSG_CMD.KEEPALIVE,mine)
    },5*1000)
}