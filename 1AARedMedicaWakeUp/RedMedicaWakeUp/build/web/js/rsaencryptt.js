// Create the encryption object and set the key.
var crypt = new JSEncrypt();
crypt.setPublicKey('-----BEGIN PUBLIC KEY-----MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOBZ1xqhB2ZKZvh7111t/Acbw+OJ8ER+0RODu9GrUqgRfibvfFj+I6j9tZvgePMFMdKuvg44uagnXUIRxOCkwH8CAwEAAQ==-----END PUBLIC KEY-----'); //You can use also setPrivateKey and setPublicKey, they are both alias to setKey
crypt.setPrivateKey('-----BEGIN RSA PRIVATE KEY-----MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEA4FnXGqEHZkpm+HvXXW38BxvD44nwRH7RE4O70atSqBF+Ju98WP4jqP21m+B48wUx0q6+Dji5qCddQhHE4KTAfwIDAQABAkAAuAj6at2tNDvUPAFaqUmPdEVSa1S7w3REDb4CU/w10ab+RQJN+y08TVxro9hJaI7qx2mvWyp7Lu8sgni+/Y0BAiEA+m7KmDYpVxoEKMrAxLurnMRPB8m/7nFb5WRlBw6EVsECIQDlVp4YkS2GfS5OpOont4nygwAPiiOop6jmQhGcV9wnPwIhAOXMTjk30d1sMQ7hZdvgvcu/Uym5VlZNTN8ErbO4/XTBAiEA11LIBRDpyYfm3ngAUWIF5ugQOobcjBE+h1Io7r2fOkkCICrDx+BNfG9LSx73NZJakbhbiLxWo2k0dE7SaXg555Sf-----END RSA PRIVATE KEY-----');
//Eventhough the methods are called setPublicKey and setPrivateKey, remember
//that they are only alias to setKey, so you can pass them both a private or
//a public openssl key, just remember that setting a public key allows you to only encrypt.

//var text = 'test';
// Encrypt the data with the public key.
//var enc = crypt.encrypt(text);
// Now decrypt the crypted text with the private key.
//var dec = crypt.decrypt(enc);

// Now a simple check to see if the round-trip worked.
//if (dec === text){
//    alert('It works!!!');
//} else {
//    alert('Something went wrong....');
//}

function securitat()
{
    var mail=document.getElementById("mail");
    var pasemail=document.getElementById("pasemail");
    var texto=document.getElementById("pass3");
    var pase=document.getElementById("pase");
    //texto2.value=crypt.encrypt(texto2.value);
    pasemail.value=crypt.encrypt(mail.value);
    pase.value=crypt.encrypt(texto.value);
    texto.value="";
    //h.submit();
    return true;
}

function check()
            {
                var tel=document.getElementById("telefono");
                var pass=document.getElementById("pass");
                var pass1=document.getElementById("pass1");
                var mail=document.getElementById("correo");
                var pasacorreo=document.getElementById("pasacorreo");
                var pasapass=document.getElementById("pasapass");
                var pasapass2=document.getElementById("pasapass2");
                var pasacel=document.getElementById("pasacel");
                if(pass.value.length<6)
                {
//                    alert("La contrase\u00F1a debe tener almenos 6 caracteres");
//                    return false;
pasacel.value=crypt.encrypt(tel.value);
                    pasacorreo.value=crypt.encrypt(mail.value);
                    pasapass.value=crypt.encrypt(pass.value);
                    pasapass2.value=crypt.encrypt(pass1.value);
                    return true;
                }
                else if(pass.value!==pass1.value)
                {
//                    alert("Las contrase\u00F1aas no coinciden");
//                    return false;
pasacel.value=crypt.encrypt(tel.value);
                    pasacorreo.value=crypt.encrypt(mail.value);
                    pasapass.value=crypt.encrypt(pass.value);
                    pasapass2.value=crypt.encrypt(pass1.value);
                    return true;
                }
                else if(tel.value.length<10)
                {
//                    alert("ingrese un telefono valido");
//                    return false;
pasacel.value=crypt.encrypt(tel.value);
                    pasacorreo.value=crypt.encrypt(mail.value);
                    pasapass.value=crypt.encrypt(pass.value);
                    pasapass2.value=crypt.encrypt(pass1.value);
                    return true;
                }
                else if(checkemail())
                {
//                    alert("Ingresa un Correo valido");
pasacel.value=crypt.encrypt(tel.value);
                    pasacorreo.value=crypt.encrypt(mail.value);
                    pasapass.value=crypt.encrypt(pass.value);
                    pasapass2.value=crypt.encrypt(pass1.value);
                    return true;
                }
                else
                {
                    pasacel.value=crypt.encrypt(tel.value);
                    pasacorreo.value=crypt.encrypt(mail.value);
                    pasapass.value=crypt.encrypt(pass.value);
                    pasapass2.value=crypt.encrypt(pass1.value);
                    return true;
                }
                
                
            }
            
            function check2()
            {
                var tel=document.getElementById("telefono");
                var mail=document.getElementById("correo");
                var pasacorreo=document.getElementById("pasacorreo");
                var pasacel=document.getElementById("pasacel");
                if(tel.value.length<10)
                {
                    alert("ingrese un telefono valido");
                    return false;
                }
                else{
                    pasacel.value=crypt.encrypt(tel.value);
                    pasacorreo.value=crypt.encrypt(mail.value);
                    return true;
                }
                
                
            }
            function check3()
            {
                
                var pass=document.getElementById("pass");
                var pass1=document.getElementById("pass1");
                var pasapass=document.getElementById("pasapass");
                var oldpass=document.getElementById("pass4");
                var pasaoldpass=document.getElementById("pasaoldpass");
                if(pass.value.length<6)
                {
                    alert("La contrase\u00F1a debe tener almenos 6 caracteres");
                    return false;
                }
                else if(pass.value!==pass1.value)
                {
                    alert("Las contrase\u00F1aas no coinciden");
                    return false;
                }
                else{
                    pasaoldpass.value=crypt.encrypt(oldpass.value);
                    pasapass.value=crypt.encrypt(pass.value);
                    return true;
                }
                
                
            }
            function check4()
            {
                
                var pass=document.getElementById("pass");
                var pass1=document.getElementById("pass1");
                var pasapass=document.getElementById("pasapass");
                if(pass.value.length<6)
                {
                    alert("La contrase\u00F1a debe tener almenos 6 caracteres");
                    return false;
                }
                else if(pass.value!==pass1.value)
                {
                    alert("Las contrase\u00F1aas no coinciden");
                    return false;
                }
                else{
                    pasapass.value=crypt.encrypt(pass.value);
                    return true;
                }
                
                
            }
            
            function check5()
            {
                
                var pass=document.getElementById("pass");
                var pasapass=document.getElementById("pasapass");
                
                    pasapass.value=crypt.encrypt(pass.value);
                    return true;
            }
            
            
            var testresults;
function checkemail(){
//var str=document.getElementById("correo").value;
//var filter=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
//if (filter.test(str))
//testresults=false;
//else{
//testresults=true;
//}
//return (testresults);
return false;
}

