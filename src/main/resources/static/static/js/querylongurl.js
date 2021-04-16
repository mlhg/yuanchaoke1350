layui.config().use(['form','jquery','layer'], function(){

   var $ = layui.$;
   var form = layui.form;

    form.on("submit(generate)",function(){
        var  url = $('#url_textarea').val()
        if (url.length<1) {
            layer.alert("请输入短地址url")
        }else {
            index  =null
            $.ajax({//异步请求返回给后台
                url:'/url/queryLongUrl',
                type:'POST',
                data: {
                    "shortUrl":url
                },
                dataType:'json',
                beforeSend: function(re){
                    index = top.layer.msg('查询中',{icon: 16,time:false,shade:0.8});
                },
                success:function(d){
                    var r=d.result;
                    console.log(r);
                    if(d.success){
                        top.layer.msg("查询成功！");
                        $('#result_a').text(d.data.orgUrl).attr("href",d.data.orgUrl)
                        $('#result_div').css("display","block")
                    }else {
                        top.layer.msg("查询失败！");
                    }
                    layer.close(index)

                }
            })


        }
        return false;
    })

});
