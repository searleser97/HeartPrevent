DELIMITER $$
drop procedure if exists altapdf $$
CREATE PROCEDURE altapdf(in file mediumblob,in nombre varchar(100), in user varchar(45), in tipoarc tinyint(1))
BEGIN
declare maxim int;
declare maximr int;
declare idu int;
declare nombreigual int;

set idu=(select idpersona from persona where correo=user);
set nombreigual=(select count(*) from archnube
inner join relarchnube 
on relarchnube.idarchnube=archnube.idarchnube where name=nombre and idusuario=idu);

if nombreigual=0 then
set maxim=(select ifnull(max(idarchnube),0)+ 1 from archnube);
set maximr = (select ifnull(max(idrelarchnube),0)+ 1  from relarchnube);

	insert into archnube values(maxim, file,current_timestamp,nombre,tipoarc);
    insert into relarchnube values (maximr,idu,maxim);
elseif nombreigual>0 then
	select 'pongale otro nombre' as mensaje;
end if;
END $$
DELIMITER ;

DELIMITER $$
drop procedure if exists borrarpdf $$
CREATE PROCEDURE borrarpdf(in nombre varchar(45), in user varchar(45))
BEGIN    
declare counta int;
declare ida int;
declare idu int;

set idu=(select idpersona from persona where correo=user);

set ida =(select archnube.idarchnube from archnube inner join relarchnube
on relarchnube.idarchnube=archnube.idarchnube
where archnube.name=nombre and relarchnube.idusuario=idu);

set counta =(select count(*) from relarchnube where idusuario=idu and idarchnube=ida);

if counta=1 then
    delete from relarchnube where idusuario=idu and idarchnube=ida;
	delete from archnube where idarchnube=ida;
    select 'Borrado' as mensaje;
elseif counta=0 then
	select 'No hay archivos llamados asi' as mensaje;
else
	select 'Mal' as mensaje;
end if;

END $$
DELIMITER ;

DELIMITER $$
drop procedure if exists archivospersona $$
CREATE PROCEDURE archivospersona(in cor nvarchar(100), in tipoarc int)
BEGIN
	select archnube.idarchnube,archnube.file, archnube.fechaarch,archnube.name from relarchnube 
	inner join persona on persona.idpersona=relarchnube.idusuario
	inner join archnube on archnube.idarchnube=relarchnube.idarchnube where (correo=cor or idpersona=cor) and archnube.historial=tipoarc
    order by archnube.name;
END $$
DELIMITER ;

DELIMITER $$
drop procedure if exists archivospersonat $$
CREATE PROCEDURE archivospersonat(in cor nvarchar(100))
BEGIN
	select archnube.idarchnube,archnube.file, archnube.fechaarch,archnube.name from relarchnube 
	inner join persona on persona.idpersona=relarchnube.idusuario
	inner join archnube on archnube.idarchnube=relarchnube.idarchnube where correo=cor;
END $$
DELIMITER ;

DELIMITER $$
drop procedure if exists datospersona $$
CREATE PROCEDURE datospersona(in cor nvarchar(100))
BEGIN
select * from persona p inner join relpersonatipo r on p.idpersona=r.idusuario where correo=cor or idpersona=cor;
END $$
DELIMITER ;

DELIMITER $$
drop procedure if exists verajustes $$
CREATE PROCEDURE verajustes(in cor nvarchar(100))
BEGIN
declare idu int;
	set idu=(select idpersona from persona where correo=cor);
    
    select path,syncact, inicio from ajustes inner join relajustesusu on relajustesusu.idajustes=ajustes.idajustes
    where idpersona=idu;
END $$
DELIMITER ;


DELIMITER $$
drop procedure if exists path $$
CREATE PROCEDURE path(in cor nvarchar(500), in direcnueva nvarchar(500))
BEGIN
declare idu int;
declare maxim int;
declare maximra int;
declare yaTiene int;
declare ida int;

set idu=(select idpersona from persona where correo=cor);
set maxim=(select ifnull(max(idajustes),0)+ 1 from ajustes); 
set maximra=(select ifnull(max(idrelajustesusu),0)+ 1 from relajustesusu);
set yaTiene=(select count(*) from relajustesusu where idpersona=idu);

if direcnueva='' then
	if yaTiene=0 then
	insert into ajustes values (maxim,1,1,'C:/HeartCloud');
    insert into relajustesusu VALUES (maximra, idu,maxim);
    select 'Agregado' as msj;    	
	end if;
else
    set ida=(select idajustes from relajustesusu where idpersona=idu); 
	UPDATE `redmedica`.`ajustes` SET `path`=direcnueva WHERE `idajustes`=ida;
   select 'Modificado' as msj;
end if;
END $$
DELIMITER ;

DELIMITER $$
drop procedure if exists modajustes $$
CREATE PROCEDURE modajustes(in cor nvarchar(100),in modque int,in onoff int)
BEGIN
declare ida int;
declare idu int;
set idu=(select idpersona from persona where correo=cor);
set ida=(select idajustes from relajustesusu where idpersona=idu); 

if modque=1 then
	if onoff=1 then
		UPDATE `redmedica`.`ajustes` SET `syncact`=1 WHERE `idajustes`=ida;
	else if onoff=0 then
		UPDATE `redmedica`.`ajustes` SET `syncact`=0 WHERE `idajustes`=ida;
	end if;
	end if;
else if modque=0 then
	if onoff=1 then
		UPDATE `redmedica`.`ajustes` SET `inicio`=1 WHERE `idajustes`=ida;
	else if onoff=0 then
		UPDATE `redmedica`.`ajustes` SET `inicio`=0 WHERE `idajustes`=ida;
	end if;
	end if;
end if;
end if;
END $$
DELIMITER ;


DELIMITER $$
drop procedure if exists archivopersona $$
CREATE PROCEDURE archivopersona(in cor nvarchar(100), in nompdf nvarchar(100))
BEGIN
	select archnube.idarchnube,archnube.file, archnube.fechaarch,archnube.name from relarchnube 
	inner join persona on persona.idpersona=relarchnube.idusuario
	inner join archnube on archnube.idarchnube=relarchnube.idarchnube where correo=cor and archnube.historial=0 and 
    archnube.name like concat('%',nompdf,'%');
END $$
DELIMITER ;

DELIMITER $$
drop procedure if exists nuevoarchivo $$
CREATE PROCEDURE nuevoarchivo(in cor nvarchar(100), in nompdf nvarchar(100))
BEGIN
	select archnube.idarchnube,archnube.file, archnube.fechaarch,archnube.name from relarchnube 
	inner join persona on persona.idpersona=relarchnube.idusuario
	inner join archnube on archnube.idarchnube=relarchnube.idarchnube where correo=cor and archnube.name like concat('%',nompdf,'%');
END $$
DELIMITER ;

DELIMITER $$
drop procedure if exists datospresion $$
CREATE PROCEDURE datospresion(in cor nvarchar(100), in fechames nvarchar(15), in fechaanio int)
BEGIN
declare idu int;
declare msg int;
set idu=(select idpersona from persona where correo=cor);


set @count=(select count(*) as presion 
from presion inner join relusupresion on
    presion.idpresion=relusupresion.idpresion
    inner join persona on relusupresion.idusuario=persona.idpersona 
    where relusupresion.idusuario=idu and MONTH(relusupresion.fecha)=fechames and YEAR(relusupresion.fecha)=fechaanio);
    
if @count>0 then    
set msg=1;     
	select msg,DAYOFMONTH(relusupresion.fecha) as dia, 
	month(relusupresion.fecha) as mes,
	year(relusupresion.fecha) as anio, 
	presion.presion as presion 
	from presion inner join relusupresion on
    presion.idpresion=relusupresion.idpresion
    inner join persona on relusupresion.idusuario=persona.idpersona 
    where relusupresion.idusuario=idu and month(relusupresion.fecha)=fechaMes and year(relusupresion.fecha)=fechaAnio
    order by dia;
else 
set msg=0;
	select msg;
end if;
END $$
DELIMITER ;

DELIMITER $$
drop procedure if exists validausrnube $$
CREATE PROCEDURE validausrnube(in usr nvarchar(100),in pass nvarchar(100))
BEGIN
declare valida int;
declare valida2 int;
declare msg int;
set valida=(select count(*) from persona where correo=usr and contraseña=pass and idcategoria=2);
set valida2=(select count(*) from persona where correo=usr and contraseña=pass and idcategoria=1);

if(valida2>0)then 
	set msg=2;
	select *,msg from persona where correo=usr and contraseña=pass;
else
	if(valida>0)then
		set msg=1;
		select *,msg from persona where correo=usr and contraseña=pass;
	else 
		set msg=0;
		select msg;
	end if;
end if;

END $$
DELIMITER ;

DELIMITER $$
drop procedure if exists validaarchivo $$
CREATE PROCEDURE validaarchivo(in user nvarchar(100),in nombre nvarchar(100))
BEGIN
declare nombreigual int;
declare idu int;
set idu=(select idpersona from persona where correo=user);
set nombreigual=(select count(*) from archnube
inner join relarchnube 
on relarchnube.idarchnube=archnube.idarchnube where name=nombre and idusuario=idu);

if nombreigual=0 then
	select 1 as msg;
else 
    select 0 as msg;
end if;

END $$
DELIMITER ;

