$(document).ready(function () {
    $(document).tooltip({
        selector: '[data-toggle="tooltip"]'
    });
    $(document).click(function (e) {
        e.stopPropagation();
        var func = $(e.target).attr("fname");
        if (func !== undefined) {
            var Uid = $(e.target).attr("data-us");
            switch (func) {
                case 'goto' :
                    goToUser(Uid);
                    break;
                case 'delc':
                    var uname = document.getElementById('username_' + Uid).innerHTML;
                    deleteContact(uname, Uid);
                    break;
                case 'deld' :
                    var docname = document.getElementById('username_' + Uid).innerHTML;
                    deleteDoctor(docname, Uid);
                    break;
            }
        }
    });
});

$(".material-design-hamburger__icon").click(function (e)
{
    e.preventDefault();
    var child = this.childNodes[1].classList;

    if (child.contains('material-design-hamburger__icon--to-arrow')) {
        $("#sidebar-wrapper").css("left", "-180px");
//        $(".chat,#page-content-wrapper").css("opacity","1");
        child.remove('material-design-hamburger__icon--to-arrow');
        child.add('material-design-hamburger__icon--from-arrow');
    } else {
        $("#sidebar-wrapper").css("left", "0px");
//        $(".chat,#page-content-wrapper").css("opacity","0.4");
        child.remove('material-design-hamburger__icon--from-arrow');
        child.add('material-design-hamburger__icon--to-arrow');
    }
});

$('#sacasubmenu').click(function (e) {
    e.preventDefault();
    if ($("#submenuside").css("height") === "0px") {
        $("#submenuside").css("height", "120px");
    } else
    {
        $("#submenuside").css("height", "0px");
    }

});

$('.botonbuscausu').focus(function (e) {
    e.preventDefault();
    $(".botonbuscausu").toggleClass("toggled");
});


$(function () {
    $('form').each(function () {
        $(this).find('.tcomment').keypress(function (e) {
            // Enter pressed?
            if (e.which === 10 || e.which === 13) {

                this.form.submit();
            }
        });

        $(this).find('.subb').hide();
    });
});


function nuevochat(id) {
    var idusu = id.getAttribute("id");
    alert(idusu);
    $("body").append();
}

function enviachat(forma) {
    forma.submit();
}


$('.msjnoti').click(function (event) {
    event.stopPropagation();
});

function confirmafriend(idamigo)
{
    var divunico = document.getElementById("divsolicitud" + idamigo);
    var chat = document.getElementById("divchatright");
    var linkn = document.getElementById("notiamigoicon");
    $.post("../solicitudes", {opc: 1, idamigo: idamigo}).done(function (data) {
        divunico.remove();
        chat.innerHTML = data;
        linkn.style.display = 'none';
    });
}

function rechazafriend(idamigo)
{
    var divunico = document.getElementById("divsolicitud" + idamigo);
    var linkn = document.getElementById("notiamigoicon");
    $.post("../solicitudes", {opc: 2, idamigo: idamigo}).done(function (data) {
        divunico.remove();
        linkn.style.display = 'none';
    });
}
function confirmapaciente(idpaciente)
{
    var divunico = document.getElementById("divsolicitudpaciente" + idpaciente);
    var chat = document.getElementById("divchatright");
    var linkn = document.getElementById("notipacientesicon");
    $.post("../solicitudes", {opc: 9, idamigo: idpaciente}).done(function (data) {
        divunico.remove();
        linkn.style.display = 'none';
        chat.innerHTML = data;

    });
}
function rechazapaciente(idpaciente)
{
    var divunico = document.getElementById("divsolicitudpaciente" + idpaciente);
    var linkn = document.getElementById("notipacientesicon");
    $.post("../solicitudes", {opc: 6, idamigo: idpaciente}).done(function (data) {
        divunico.remove();
        linkn.style.display = 'none';
    });
}

$('#page-content-wrapper').click(function () {
    var elem = document.getElementById("mdhi");
    var child = elem.childNodes[1].classList;
    $("#sidebar-wrapper").css("left", "-180px");
    child.remove('material-design-hamburger__icon--to-arrow');
    child.add('material-design-hamburger__icon--from-arrow');
});

$('html').click(function () {
    var as = document.getElementById("postnoti");
    var as2 = document.getElementById("msjnoti");
    var as3 = document.getElementById("amigonoti");
    var apunta = document.getElementById("apuntanoti");
    try {
        var as5 = document.getElementById("pacientesnoti");
        as5.style.display = 'none';
    } catch (jj)
    {

    }
    apunta.style.display = 'none';
    as.style.display = 'none';
    as2.style.display = 'none';
    as3.style.display = 'none';
});

function onlineono1yTodo()
{
    ////////////////////////////////////////////////////////////////////Busqueda en vivo-------------------------------------------------------------------------------------------
    try {
        var query = document.getElementById("querybusc").value;
        $.post("../solicitudes", {opc: 15, idamigo: query.trim()}).done(function (data5) {
            if (data5.trim() !== "igual")
            {
                var divbusqueda = document.getElementById("divbusqueda");

                divbusqueda.innerHTML = "" + data5;
            }


        });
    } catch (e)
    {

    }
    ///////////////////////////////////////////////////////////////////LinksSolicitudMensaje en el perfil de un contacto en vivo
    try {
        var query = document.getElementById("UsrPerfil").value;
        $.post("../solicitudes", {opc: 16, idamigo: query}).done(function (data6) {
            if (data6.trim() !== "igual")
            {
                try {
                    var divlinksusuario = document.getElementById("linksusuario");

                    divlinksusuario.innerHTML = "" + data6;

                } catch (e) {
                }
            }


        });
    } catch (e) {
    }

}
try {
    setInterval(onlineono1yTodo, 1000);
} catch (e)
{

}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

function deleteContact(nombre, idUsr2)
{
    //opc=8;
    swal({title: "Deseas eliminar a " + nombre,
        showCancelButton: true,
        confirmButtonText: "S\355",
        cancelButtonText: "No"},
            function () {
                $.post("../solicitudes", {opc: 8, idamigo: idUsr2});
            });
}
function deleteDoctor(nombre, idUsr2)
{
    //opc=8;
    swal({title: "Deseas que " + nombre + " deje de ser tu doctor?",
        showCancelButton: true,
        confirmButtonText: "S\355",
        cancelButtonText: "No"},
            function () {
                $.post("../solicitudes", {opc: 7, idamigo: idUsr2});
            });
}
function busquedaChat() {
    var valor = document.getElementById("busquedaChatInput").value;
    var chat = document.getElementById("divchatright");
    $.post("../solicitudes", {opc: 17, idamigo: valor}).done(function (data) {
        chat.innerHTML = data;
    });
}


function goToUser(id) {
    window.location = "InicioRed.jsp?Usr=" + id + "";
}
function irAPost(idpost) {
    window.location = "InicioRed.jsp?post=" + idpost;
}
function irAPost2(idpost) {
    window.location = "HPhelp.jsp?post=" + idpost;
}


$("#encierraUsubuscado").click(function (e) {
    e.preventDefault();
    if ($(".encierraUsubuscado").text().trim() === "Atras")
    {
        setTimeout(function () {
            $(".encierraUsubuscado").text("Ver Contactos de Confianza");
        }, 999);

    } else {
        $(".encierraUsubuscado").text("Atras");
    }

    if ($(".usubuscado").css("display") === "none")
    {

        $(".usubuscado2").toggleClass("toggled");
        setTimeout(function () {
            $(".usubuscado2").css('display', 'none');
        }, 699);
        setTimeout(function () {
            $(".usubuscado").css('display', 'block');
        }, 700);

        setTimeout(function () {
            $(".usubuscado").toggleClass("toggled");
        }, 750);

    } else {
        $(".usubuscado2").css('display', 'block');
        $(".usubuscado").toggleClass("toggled");
        setTimeout(function () {
            $(".usubuscado2").toggleClass("toggled");
            $(".usubuscado").css('display', 'none');
        }, 500);
    }




});

function addmenuconfianza() {
    $("#encierraUsubuscado").text("Ver contactos de confianza");
}
function addespaciocontacts() {
    $("#encierraUsubuscado").text("");
}

$('.heartprevent').each(function () {
    var link = $(this).html();
    $(this).contents().wrap('<a style="outline: none; text-decoration: none; color: white;" href="InicioRed.jsp"></a>');
});


function verinfo(email, tel) {
    swal({title: "Info:",
        text: "Email: " + email + "\nTel: " + tel});
}

var iduser = document.getElementById("iduser").value;

function noticontacts() {
    $.post("../solicitudes", {opc: 13, idamigo: iduser}).done(function (data3) {
        var dato3 = data3.split("||||");
        if (dato3[0] !== "igual")
        {
            var divamigonoti = document.getElementById("amigonoti");

            divamigonoti.innerHTML = "<div id='divsolicitud' class='msjnotiIn titulonoti'>"
                    + "<span>"
                    + "Solicitudes amigos:"
                    + "</span>"
                    + "</div>" + dato3[0];
        }
    });
    var as2 = document.getElementById("amigonoti");
    var as4 = document.getElementById("msjnoti");
    var as3 = document.getElementById("postnoti");

    var apunta = document.getElementById("apuntanoti");
    apunta.style.left = '70.6vw';
    apunta.style.display = 'block';
    try {
        var as5 = document.getElementById("pacientesnoti");
        as5.style.display = 'none';
    } catch (jj)
    {

    }
    as4.style.display = 'none';
    as3.style.display = 'none';
    as2.style.display = 'block';
}
function notiposts() {
    $.post("../solicitudes", {opc: 11, idamigo: iduser}).done(function (data) {
        var dato = data.split("||||");
        if (dato[0].trim() !== "igual")
        {
            var divpostnoti = document.getElementById("postnoti");
            divpostnoti.innerHTML = "<div id='divsolicitud' class='msjnotiIn titulonoti'>"
                    + "<span>"
                    + "Notificaciones:"
                    + "</span>"
                    + "</div>" + dato[0];
        }
    });
    var as2 = document.getElementById("postnoti");
    var as4 = document.getElementById("amigonoti");
    var as3 = document.getElementById("msjnoti");
    var apunta = document.getElementById("apuntanoti");
    apunta.style.left = '76.6vw';
    apunta.style.display = 'block';
    as3.style.display = 'none';
    as4.style.display = 'none';
    as2.style.display = 'block';
    try {
        var as5 = document.getElementById("pacientesnoti");
        as5.style.display = 'none';
    } catch (jj)
    {

    }
    var linknn = document.getElementById("notinotiicon");
    linknn.style.display = 'none';
}
function notimsjs() {
    $.post("../solicitudes", {opc: 12, idamigo: iduser}).done(function (data2) {
        var dato2 = data2.split("||||");
        if (dato2[0].trim() !== "igual")
        {
            var divmsjnoti = document.getElementById("msjnoti");

            divmsjnoti.innerHTML = "<div id='divsolicitud' class='msjnotiIn titulonoti'>"
                    + "<span>"
                    + "Mensajes:"
                    + "</span>"
                    + "</div>" + dato2[0];
        }
    });
    var as2 = document.getElementById("msjnoti");
    var as3 = document.getElementById("postnoti");
    var as4 = document.getElementById("amigonoti");
    var apunta = document.getElementById("apuntanoti");
    apunta.style.left = '73.6vw';
    apunta.style.display = 'block';
    as4.style.display = 'none';
    as3.style.display = 'none';
    as2.style.display = 'block';
    try {
        var as5 = document.getElementById("pacientesnoti");
        as5.style.display = 'none';
    } catch (jj)
    {

    }
}
function notipatients() {
    $.post("../solicitudes", {opc: 14, idamigo: iduser}).done(function (data4) {
        var dato4 = data4.split("||||");
        if (dato4[0] !== "igual")
        {
            var divpacientesnoti = document.getElementById("pacientesnoti");

            divpacientesnoti.innerHTML = "<div id='divsolicitud' class='msjnotiIn titulonoti'>"
                    + "<span>"
                    + "Solicitudes Pacientes:"
                    + "</span>"
                    + "</div>" + dato4[0];
        }
    });
    var as2 = document.getElementById("msjnoti");
    var as3 = document.getElementById("postnoti");
    var as4 = document.getElementById("amigonoti");
    var apunta = document.getElementById("apuntanoti");
    try {
        var as5 = document.getElementById("pacientesnoti");
        as5.style.display = 'block';
    } catch (jj)
    {

    }
    apunta.style.left = '79.6vw';
    apunta.style.display = 'block';
    as4.style.display = 'none';
    as3.style.display = 'none';
    as2.style.display = 'none';
}

$(document).ready(function () {
    $('.encabezado').click(function (event) {
        event.stopPropagation();
    });
    $('#notifriend').click(function (e) {
        e.stopPropagation();
        noticontacts();
    });
    $('#notiamigoicon').click(function (e) {
        e.stopPropagation();
        noticontacts();
    });
    $('#notipost').click(function (e) {
        e.stopPropagation();
        notiposts();
    });
    $('#notinotiicon').click(function (e) {
        e.stopPropagation();
        notiposts();
    });
    $('#notimsj').click(function (e) {
        e.stopPropagation();
        notimsjs();
    });
    $('#notimsjicon').click(function (e) {
        e.stopPropagation();
        notimsjs();
    });
    $('#notipacientes').click(function (e) {
        e.stopPropagation();
        notipatients();
    });
    $('#notipacientesicon').click(function (e) {
        e.stopPropagation();
        notipatients();
    });
});