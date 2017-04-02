
# agregamos a esa tabla, global, para poder chatear todos
insert into `tiporelusuarios` (`idtiporelusuarios`, `tiporelusuarios`) values ('5', 'global');

DELIMITER $$
drop procedure if exists mensajeschat $$
CREATE PROCEDURE mensajeschat(in opciones int, in nombreamigo nvarchar(100), in cormio nvarchar(100), in mensajechat nvarchar(200))
BEGIN
	declare maxim int;
	declare relusu int;
    declare relusu2 int;
    declare usu int;
    declare amigoid int;
    
    set usu=(select idpersona from persona where correo=cormio);
    set amigoid=(select idpersona from persona where nombre=nombreamigo);
    
	set relusu=(select idrelusuarios from relusuarios where idusuario1=usu 
	and idusuario2=amigoid order by idrelusuarios);
    
    set relusu2=(select idrelusuarios from relusuarios where idusuario1=amigoid
	and idusuario2=usu order by idrelusuarios);
    
	case opciones 
    # leer mensajes de usuario cormio con coramigo
		when 1 then
			select *,date_format(m.fecha,'%d/%m/%Y') as fechachat, date_format(m.fecha,'%H:%i:%s') as horachat
            from mensajes m inner join relusuarios r on m.idrelusuarios=r.idrelusuarios 
            inner join persona p on p.idpersona=r.idusuario1 where m.idrelusuarios=relusu or m.idrelusuarios=relusu2
            order by fechachat, horachat;
		
        when 2 then
        # nuevo mensaje
			set maxim=(select ifnull(max(idmensaje),0)+1 from mensajes);
			insert into mensajes (idmensaje,mensaje,idrelusuarios,usuario,estatus) values(maxim,mensajechat,relusu,usu,1);
		when 3 then
        # cargo mensajes chat global
            select *,date_format(m.fecha,'%d/%m/%Y') as fechachat, date_format(m.fecha,'%H:%i:%s') as horachat
            from mensajes m inner join persona 
            on persona.idpersona=m.Usuario where m.idrelusuarios=0;
		when 4 then
        # nuevo mensaje chat global
            set maxim=(select ifnull(max(idmensaje),0)+1 from mensajes);
			insert into mensajes (idmensaje,mensaje,idrelusuarios,usuario,estatus) values(maxim,mensajechat,0,usu,1);
            
		when 5 then
            select nombre from persona where correo=cormio;
	end case;
END $$
DELIMITER ;


DELIMITER $$

drop procedure if exists friendschat $$
CREATE PROCEDURE `friendschat`(in opc int, in cor nvarchar(300))
BEGIN
declare idUsu int;
set idUsu=(select idpersona from persona where correo=cor);
	Case opc
		when 0 then
			select * from (select idusuario1,idUsuario2 from relusuarios where idUsuario1=idUsu) v 
			inner join (select * from relusuarios where idUsuario2=idUsu) vv on vv.idUsuario1=v.idUsuario2
			inner join persona on v.idUsuario2=idpersona;
    end case;
END $$
DELIMITER ;

call mensajeschat(1,'Yhael','BI8X9SLrQqpnSJdRGrD194wqKGCCOgQ/X5SdbgeciQ0=','');
