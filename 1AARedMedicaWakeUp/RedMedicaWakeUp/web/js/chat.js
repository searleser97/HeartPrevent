
var websocket = new WebSocket("ws://" + document.getElementById("wsdir").value);
var divchatmessages = document.getElementById("divchatmsj");
divchatmessages.scrollTop = divchatmessages.scrollHeight;
websocket.onopen = function (event) {
    if (event.data === undefined) {
        return;
    }
};
websocket.onmessage = function processMessage(message)
{
    var jsonData = JSON.parse(message.data);
    if (jsonData.message !== null)
    {
        var splitmsj = jsonData.message.split("|");
        var idusu = $.trim(splitmsj[0]);
        var mensaje = $.trim(splitmsj[1]);
        var idusujs = document.getElementById("idusuariosession").value;
        var fecha = new Date();
        var mesajuste = "" + fecha.getMonth();
        var diaajuste = "" + fecha.getDate();
        var minajuste = "" + fecha.getMinutes();
        if (mesajuste.length === 1) {
            mesajuste = '0' + fecha.getMonth();
        }
        if (diaajuste.length === 1) {
            diaajuste = '0' + fecha.getDate();
        }
        if (minajuste.lenght === 1) {
            minajuste = '0' + fecha.getMinutes();
        }


        var fechatiempo = fecha.getFullYear() + "-" + mesajuste + "-" + diaajuste + " " + fecha.getHours() + ":" + fecha.getMinutes();
        if (idusu === idusujs)
        {
            $(".chatmessages").append("<li style='list-style-type: none; height: auto; margin-bottom: 10px;clear: both; padding-left: 10px; padding-right: 10px; word-break: break-all;'>"
                    + "<span data-toggle='tooltip' data-placement='right' title='" + fechatiempo + "' style='max-width: 85%; background-color: white;padding: 6px; position: relative; border-width: 1px; border-style: solid;border-color: grey; float: right; border-radius: 20px 0 20px 20px; background-color: #dbedfe;'>"
                    + "" + mensaje + ""
                    + "</span>"
                    + "<div style='clear: both;'></div>"
                    + "</li>");
            divchatmessages.scrollTop = divchatmessages.scrollHeight;
            $('[data-toggle="tooltip"]').tooltip();
        } else
        {
            $(".chatmessages").append("<li style='list-style-type: none; height: auto; margin-bottom: 10px;vclear: both; padding-left: 10px; padding-right: 10px; word-break: break-all;'>"
                    + "<span data-toggle='tooltip' data-placement='left' title='" + fechatiempo + "' style='max-width: 85%; background-color: white;padding: 6px; position: relative; border-width: 1px; border-style: solid;border-color: grey; float: left; border-radius: 0 20px 20px 20px; background-color: white;'>"
                    + "" + mensaje + ""
                    + "</span>"
                    + "<div style='clear: both;'></div>"
                    + "</li>");
            divchatmessages.scrollTop = divchatmessages.scrollHeight;
            $('[data-toggle="tooltip"]').tooltip();
        }
    }
};

$(function () {
    $('form').each(function () {
        $(this).find('.msjarea').keypress(function (e) {
            // Enter pressed?
            if (e.which === 10 || e.which === 13) {
                e.preventDefault();
                var msj = document.getElementById("msjtext").value;
                websocket.send('{"tipomsj":"1","mensaje":"' + msj + '"}');
                document.getElementById("msjform").reset();
            }
        });
    });
});

function irAperfil(idfriend) {
    window.location = "InicioRed.jsp?Usr=" + idfriend;
}

var percentNow = 0;
var percent = 0;
var upInterval;
var imgchatcuenta = 0;
//----------------------Valida Imagen----------------------------------------------------------------------------------
$('#imgspan').click(function () {
    $('#inimgmsg').click();
});
$('#inimgmsg').change(function () {
    var file = document.getElementById("inimgmsg");
    if (file.files.length !== 0) {
        var fileextension = file.value.substr(file.value.lastIndexOf('.') + 1);
        if (fileextension.toLowerCase() === 'jpg' || fileextension.toLowerCase() === 'png' || fileextension.toLowerCase() === 'gif' || fileextension.toLowerCase() === 'ico' || fileextension.toLowerCase() === 'jpeg')
        {
            upInterval = setInterval(function () {
                progressPercentage();
            }, 5);
            $('#progressContainer').slideDown(150);
            $('#sendimgmsg').submit();
        } else {
            swal({title: "El archivo seleccionado no corresponde a un tipo de imagen", type: "warning"});
        }
    }

});
//-------------------------subeImagen--------------------------------------------------------------------------------
$('#sendimgmsg').submit(function (e) {
    $('#imgspan').css("pointer-events", "none");
    $.ajax({
        xhr: function ()
        {
            var xhr = new window.XMLHttpRequest();
            //Upload progress
            xhr.upload.addEventListener("progress", function (evt) {
                if (evt.lengthComputable) {
                    var percentComplete = (evt.loaded / evt.total) * 50;
                    percentNow = parseInt(percentComplete, 10);

                }
            }, false);
            //Download progress
            xhr.addEventListener("progress", function (evt) {
                if (evt.lengthComputable) {
                    var percentComplete = 50 + (evt.loaded / evt.total) * 50;
                    percentNow = parseInt(percentComplete, 10);
                    $("#divprogress").css("width", "" + percentComplete + '%');
                }
            }, false);
            return xhr;
        },
        url: 'upimgchat',
        type: 'POST',
        data: new FormData(this),
        processData: false,
        contentType: false,
        success: function (data) {
            setTimeout(function () {
                $('#progressContainer').slideUp(350);
            }, 1000);
            setTimeout(resetupimg, 2000);
            var dato = data.split("||"),
                    estatus = dato[0];
            if (estatus === "ok") {
                var imgsrc = dato[1], usrid = dato[2];
                websocket.send("{\"tipomsj\":\"2\",\"imgtag\":\"<img id='imgmsjchat" + usrid + imgchatcuenta + "' class='imageneschat' src='" + imgsrc + "'>\"}");
                imgchatcuenta++;
            } else {
                swal({title: "Ha ocurrido un error y no se pudo efectuar la peticion", type: "warning"});
            }
        }
    });
    e.preventDefault();
});

function resetupimg() {
    $('#imgspan').css("pointer-events", "all");
    $("#divprogress").css("width", "0px");
    $("#divprogress").text("");
}

$(document).on('keyup',function(evt) {
    if (evt.keyCode === 27) {
       cierraImg();
    }
});

$('.imageneschat').click(function (e) {
    var imgid = event.target.id;
    var imgsrc = $("#" + imgid).attr("src");
    $("#imgbigid").attr("src", imgsrc);
    $(".divimgbig").slideDown();
    $(".imgbig").css("max-height", "100%");
    $(".imgbigoptions").css("display", "block");
});

function cierraImg() {
    $(".divimgbig").slideUp();
    $(".imgbig").css("max-height", "0%");
    $(".imgbigoptions").css("display", "none");
}
$(".imgbigoptions").click(function (e) {
    e.preventDefault();
    cierraImg();
});

function progressPercentage() {
    if (percent === 100) {
        $("#divprogress").css("width", "" + percentNow + '%');
        $("#divprogress").text(percent + "%");
        clearInterval(upInterval);
        percent = 0;
    } else {
        if (percentNow !== percent) {
            $("#divprogress").css("width", "" + percentNow + '%');
            $("#divprogress").text(percent + "%");
            percent++;
        } else {
            $("#divprogress").css("width", "" + percentNow + '%');
            $("#divprogress").text(percent + "%");
        }
    }
}