var serverurl = document.getElementById('NodeJsSR').value;
var clienttoken = document.getElementById('clienttoken').value;
var clientid = document.getElementById('clientid').value;
var server = io.connect('http://' + serverurl);
var contactsUpdates = 0;
server.emit('Notifications-conn', {"token": clienttoken, "usrid": clientid});

server.on('newNotification', function (data) {
    var link1 = document.getElementById("notinotiicon");
    if (data.globalnotis.toString().trim() !== "0")
    {
        link1.innerHTML = "" + data.globalnotis;
        link1.style.display = 'block';
    } else
    {
        link1.style.display = 'none';
    }
    
    var link2 = document.getElementById("notimsjicon");
    if (data.msjs.toString().trim() !== "0")
    {
        link2.innerHTML = "" + data.msjs;
        link2.style.display = 'block';
    } else
    {
        link2.style.display = 'none';
    }
    
    var link3 = document.getElementById("notiamigoicon");
    if (data.contacts.toString().trim() !== "0")
    {
        link3.innerHTML = "" + data.contacts;
        link3.style.display = 'block';
    } else
    {
        link3.style.display = 'none';
    }
    
    try {
        var link4 = document.getElementById("notipacientesicon");
        if (data.patients.toString().trim() !== "0")
        {
            link4.innerHTML = "" + data.patients;
            link4.style.display = 'block';
        } else
        {
            link4.style.display = 'none';
        }
    } catch (err) {
    }
    
    
    if (data.friendsUpdates.toString().trim() !== contactsUpdates){
        var iduser = document.getElementById("iduser").value;
    var chat = document.getElementById("divchatright");
    $.post("../solicitudes", {opc: 10, idamigo: iduser}).done(function (data) {
        
        if (data.trim() !== "igual")
        {
            chat.innerHTML = data;
        }
    });
    contactsUpdates=data.friendsUpdates;
    }
    
});

server.on('contactStatus', function (data) {
    var chat = document.getElementById("divchatright");
    chat.innerHTML(data);
});