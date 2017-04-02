delimiter //
drop procedure if exists traepacientes2//
CREATE PROCEDURE traepacientes2(in cor nvarchar(100))
BEGIN
declare isd int;
declare idu int;
declare msj int;
declare vacio int;

set isd = (select count(*) from persona p inner join relpersonatipo r on p.idpersona=r.idusuario 
where (correo=cor or idpersona=cor) and r.idtipo = 2);
if(isd > 0) then
	set idu =(select p.idpersona from persona p inner join relpersonatipo r on p.idpersona=r.idusuario where (correo=cor or idpersona=cor) and r.idtipo = 2);
    set vacio = (select count(*) from relusuarios r inner join tiporelusuarios t on r.idtiporelusuarios=t.idtiporelusuarios 
    inner join persona p on p.idpersona=r.idusuario2 where idusuario1=idu and t.idtiporelusuarios=4);
    if vacio = 0 then
		set msj = 1;
        select msj;
	else
    set msj = 2;
	select *,msj from relusuarios r inner join tiporelusuarios t on r.idtiporelusuarios=t.idtiporelusuarios 
    inner join persona p on p.idpersona=r.idusuario2 where idusuario1=idu and t.idtiporelusuarios=4;
    end if;
else
set msj=0;
    select msj;
end if;

END //
delimiter ;

delimiter //
drop procedure if exists traepacientesarch//
CREATE PROCEDURE traepacientesarch(in cor nvarchar(100))
BEGIN
	declare idu int;
    set idu=(select idpersona from persona where correo=cor);
    
select p.nombre,archnube.* from relusuarios r inner join tiporelusuarios t 
on r.idtiporelusuarios=t.idtiporelusuarios inner join persona p 
on p.idpersona=r.idusuario2 
inner join relarchnube on relarchnube.idusuario=p.idpersona
inner join archnube on relarchnube.idarchnube=archnube.idarchnube
where idusuario1=idu and t.idtiporelusuarios=4 and historial=1;    
END //
delimiter ;

delimiter //
drop procedure if exists traepacientesarchxid//
CREATE PROCEDURE traepacientesarchxid(in cor nvarchar(100), in corclick nvarchar(100))
BEGIN
declare idu int;
declare idp int;
set idu=(select idpersona from persona where correo=cor);
set idp=(select idpersona from persona where correo=corclick);
    
if idu=idp then
		select archnube.* from archnube inner join relarchnube on relarchnube.idarchnube=archnube.idarchnube where
        idusuario=idu and archnube.historial=1;
else
	select p.nombre,archnube.* from relusuarios r inner join tiporelusuarios t 
on r.idtiporelusuarios=t.idtiporelusuarios inner join persona p 
on p.idpersona=r.idusuario2 
inner join relarchnube on relarchnube.idusuario=p.idpersona
inner join archnube on relarchnube.idarchnube=archnube.idarchnube
where idusuario1=idu and idusuario2=idp and t.idtiporelusuarios=4 and archnube.historial=1;   

end if; 
END //
delimiter ;

delimiter //
drop procedure if exists tipohistorial//
CREATE PROCEDURE tipohistorial(in cor nvarchar(100))
BEGIN
declare idu int;
declare usamed int;
declare tieneenf int;
set idu=(select idpersona from persona where correo=cor);

set tieneenf=(select count(*) from enfermusu where idusuario=idu);
set usamed=(select count(*) from  relusumedicamentos where idusuario=idu);

if tieneenf=0 and usamed=0 then
	select 0 as msg;
else 
	if tieneenf>0 and usamed>0 then
		select 1 as msg;
    else 
		if tieneenf>0 then
			select 2 as msg;
        end if;
        if usamed>0 then
			select 3 as msg;
        end if;
    end if;   
end if;
#0 no tiene nada,1 tiene ambas, 2 tiene enfermedades pero no usa medicamentos, 3 tiene medicamentos pero no enfermedades
END //
delimiter ;

delimiter //
drop procedure if exists yaexistehistorial//
CREATE PROCEDURE yaexistehistorial(in cor nvarchar(100), in nomhistorial nvarchar(100))
BEGIN
declare idu int;
declare existe int;
	set idu=(select idpersona from persona where correo=cor);
    
    set existe=(select count(*) from archnube inner join relarchnube
    on relarchnube.idarchnube=archnube.idarchnube 
    inner join persona on persona.idpersona=relarchnube.idusuario
    where idusuario=idu and name=nomHistorial);
    
    if existe=0 then
		select existe as msg;
    else
		select existe as msg;
    end if;

END //
delimiter ;