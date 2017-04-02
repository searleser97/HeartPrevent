$('.form').find('input, textarea').on('keyup blur focus', function (e) {

    var $this = $(this),
        label = $this.prev('label');

      if (e.type === 'keyup') {
        if ($this.val() === '') {
            label.removeClass('active highlight');
          } else {
            label.addClass('active highlight');
          }
      } else if (e.type === 'blur') {
        if( $this.val() === '' ) {
          label.removeClass('active highlight'); 
        } else {
          label.removeClass('highlight');   
        }   
      } else if (e.type === 'focus') {

        if( $this.val() === '' ) {
          label.removeClass('highlight'); 
        } 
        else if( $this.val() !== '' ) {
          label.addClass('highlight');
        }
      }

  });

  $('.tab a').on('click', function (e) {

    e.preventDefault();

    $(this).parent().addClass('active');
    $(this).parent().siblings().removeClass('active');

    target = $(this).attr('href');

    $('.tab-content > div').not(target).hide();

    $(target).fadeIn(600);

  });
  
  
  $('#tipo').focus(function (e){
              e.preventDefault();
              var as = document.getElementById('cedula');
              var as2 = document.getElementById('labcedula');
              as.style.display = 'block';
              as2.style.display = 'block';
              
          });
          
          $('#tipo2').focus(function (e){
              e.preventDefault();
              
              var as = document.getElementById('cedula');
              var as2 = document.getElementById('labcedula');
              as.style.display = 'none';
              as2.style.display = 'none';
          }); 
          
          
          $('#tipoP1').focus(function (e){
              e.preventDefault();
              var as = document.getElementById('pulsera');
              var as2 = document.getElementById('labpulsera');
              as.style.display = 'block';
              as2.style.display = 'block';
              
          });
          
          $('#tipoP2').focus(function (e){
              e.preventDefault();
              
              var as = document.getElementById('pulsera');
              var as2 = document.getElementById('labpulsera');
              as.style.display = 'none';
              as2.style.display = 'none';
          }); 
          
           
          
          $('#tipoP4').focus(function (e){
              e.preventDefault();
              
              $('#pulsera').fadeIn();
              
          });