<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<%
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head lang="en">
    <%-- <script src="${ctxStatic }/websocket/sockjs-0.3.min.js"></script> --%>
    <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/css/bootstrap.css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <link rel="stylesheet" href="/css/bootstrap-theme.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="/js/jquery.min.js" type="text/javascript"></script>
    <!--<script type="text/javascript" src="js/jquery-1.7.2.js"></script>-->
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="/js/bootstrap.min.js"></script>
    <%-- <script src="${ctxStatic }/stomp/stomp.mini.js"></script> --%>
    <script src="http://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>

    <title>stomp测试</title>
    <script type="text/javascript">
        $(document).ready(function() {
            var sock = null;
            var stomp = null;

            function connect(){
                if(stomp!=null){
                    stomp.disconnect();
                    stomp=null;
                }
                if(sock!=null){
                    sock.close();
                    sock=null;
                }
                sock = new SockJS("/hello");
                stomp = Stomp.over(sock);
                stomp.connect('guest', 'guest', function(frame) {
                    console.log('*****  Connected  *****');
                    $('#msg').append("<b>*****  Connected  *****</b><br/>");
                    //一次性订阅，只返回一次
                    stomp.subscribe("/app/subscribeme", handleOneTime);
                    //订阅代理，代理发消息将会接收到
                    stomp.subscribe("/topic/hi", handleMsg);
                }, function(error) {
                    console.log('error:'+error);

                });
            }



            function handleOneTime(message) {
                //alert('123');
                console.log('Received: ', message);
                $('#msg').append("<b>handleOneTime - Received: " +
                    message.body + "</b><br/>");
            }

            function handleMsg(message) {
                console.log('Received: ', message);
                $('#msg').append("<b>Received: " +
                    message.body + "</b><br/>");
                // if (JSON.parse(message.body).message === 'Polo!') {
                //     setTimeout(function(){sayMarco()}, 2000);
                // }
            }

            function handleErrors(message) {
                console.log('RECEIVED ERROR: ', message);
                $('#msg').append("<b>GOT AN ERROR!!!: " +
                    JSON.parse(message.body).message + "</b><br/>");
            }

            function send() {
                if(stomp&&stomp.send){
                    //发送
                    console.log('Sending msg!');
                    stomp.send("/app/hi", {},
                        JSON.stringify({ 'message':  $('#message').val()}));
//        stomp.send("/topic/marco", {},
//                JSON.stringify({ 'message': 'Marco!' }));
                    $('#msg').append("<b>Send: " + $('#message').val() + "!</b><br/>");
                }else{
                    return;
                }

            }

            $('#stop').click(function() {
                if(stomp!=null){
                    stomp.disconnect();
                }
                if(sock!=null){
                    sock.close();
                }

                $('#msg').append("<b>Connection closed!</b><br/>");
            });

            $('#send').bind('click', function() {
                send();
            });
            $('#connect').bind('click', function() {
                connect();
            });


        });
    </script>
</head>
<body>

<div class="page-header" id="tou">
    webSocket-stomp测试程序
    <button class="btn btn-default" type="button" id="stop" >关闭连接</button>
    <button class="btn btn-default" type="button" id="connect" >开始连接</button>
</div>
<div class="well" id="msg">
</div>
<div class="col-lg">
    <div class="input-group">
        <input type="text" class="form-control" placeholder="发送信息..." id="message">
        <span class="input-group-btn">
        <button class="btn btn-default" type="button" id="send" >发送</button>
      </span>
    </div><!-- /input-group -->
</div><!-- /.col-lg-6 -->
</body>
</html>