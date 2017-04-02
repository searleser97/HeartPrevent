  try{document.getElementById("imagen").addEventListener('change', handleFileSelect, false);}catch(e){}
function validLe(e)
            {
                if(window.event)
                    var teclas=e.keyCode;
                else 
                   if(e.which)
                        teclas=e.which;
                
                if(teclas===8)
                {
                    return true;
                }
                
                var patrons=/[A-Za-z\s\ñ]/;
                var tes =String.fromCharCode(teclas);
                return patrons.test(tes);
            }
function fillyearss()
            {   
                
                var year=document.getElementById('yearsssh');
               var posicion=1;
               var fecha=new Date();
                 for (var i=1915;i<fecha.getFullYear()+1;i++)
                {
                    year.options[posicion]= new Option(i);
                    posicion++;
                }
               
                
                
            }
            
            
            function aniochange(anio,mes,dia)
            {
                
                mes.selectedIndex="0";
                dia.selectedIndex="0";
            }
function adding(yearss,months,days)
       {
            var dato = months.options[months.selectedIndex].getAttribute('id');
            var anio=yearss.options[yearss.selectedIndex].value;
                
                if(dato==="1")
                {
                    for(var i=1; i<32;i++)
                    {
                        if(i<10){
                        days.options[i]=new Option("0"+i);
                    }
                    else{
                        days.options[i]=new Option(i);
                    }
                    }
                }
                
                 else if(dato==="2")
                {
                    days.options[31]=null;
                    for(var i=1; i<31;i++)
                    {
                        if(i<10){
                        days.options[i]=new Option("0"+i);
                    }
                    else{
                        days.options[i]=new Option(i);
                    }
                    }
                }
                
                else if(dato==="3")
                {
                    days.options[31]=null;
                    days.options[30]=null;
                    days.options[29]=null;
                    
                    if (((anio % 4) === 0) && ((anio % 100) === 0 || (anio % 400) !== 0))
                    {
                        for(var i=1; i<30;i++)
                    {
                        if(i<10){
                        days.options[i]=new Option("0"+i);
                    }
                    else{
                        days.options[i]=new Option(i);
                    }
                    }
                    }
                    else
                    {
                        for(var i=1; i<29;i++)
                    {
                        
                        if(i<10){
                        days.options[i]=new Option("0"+i);
                    }
                    else{
                        days.options[i]=new Option(i);
                    }
                    }
                    }
                        
                        
                    
                }
                
                
            }            

function numeross(esec)
            {
                if(window.event)
                   var tecla=esec.keyCode;
                else 
                   if(esec.which)
                        tecla=esec.which;
                
                if(tecla===8)
                {
                    return true;
                }
                
                if(tecla===13)
                {
                    return true;
                }
                
                var patron = /[0-9]/;
                var te =String.fromCharCode(tecla);
                return patron.test(te);
            }
            
            function NumConDecimalesss(esec, textos) {
               if(window.event)
                   var tecla=esec.keyCode;
                else 
                   if(esec.which)
                        tecla=esec.which;
                
                if(tecla===8)
                {
                    return true;
                }
                
                if(tecla===13)
                {
                    return true;
                }
                   if (tecla === 46) {
                   var posiciones = textos.value.indexOf('.');
                    if (posiciones === -1)
                        return true;
                    else
                        return false;
                }
                
                var patron = /[0-9]/;
                var te =String.fromCharCode(tecla);
                return patron.test(te);
               
            
                
            }
            
            
            
            
            function cargarArchivo(elemento){
var file = elemento.files[0];
//        if(!(/\.(gif|jpg|jpeg|tiff|png)$/i).test(file.name))
//        {
//            elemento.value=null;
//            alert("Este tipo de archivo no es una Imagen Valida");
//        }
        }
        
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        function handleFileSelect(evt) {

	var jj=document.getElementById("fotop");
if(jj!==null)
{
document.getElementById("fotop").parentNode.remove();
}
	
    var files = evt.target.files; // FileList object

    // Loop through the FileList and render image files as thumbnails.
    for (var i = 0, f; f = files[i]; i++) {

      // Only process image files.
      if (!f.type.match('image.*')) {
        continue;
      }

      var reader = new FileReader();

      // Closure to capture the file information.
      reader.onload = (function(theFile) {
        return function(e) {
          // Render thumbnail.
          var span = document.createElement('span');
          span.innerHTML = ['<img class="thumb" id="fotop" src="', e.target.result,
                            '" title="', escape(theFile.name), '"/>'].join('');
          document.getElementById('listis').insertBefore(span, null);
        };
      })(f);

      // Read in the image file as a data URL.
      reader.readAsDataURL(f);
    }
  }


  //////////////////////////////////////////////////////////////////////////////////////////////////////////
  
//var valor;
//if(document.cookie){
//	galleta = unescape(document.cookie);
//	galleta = galleta.split(';');
//	for(m=0; m<galleta.length; m++){
//		if(galleta[m].split('=')[0] === "recarga"){
//			valor = galleta[m].split('=')[1];
//			break;
//		}
//	}
//	if(valor === "sip"){
//		document.cookie = "recarga=nop";
//		document.location.reload();
//	}
//}
