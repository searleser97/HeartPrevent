#pdf
use redmedica;
select Nombre,apellido, Correo,Fecha, Genero, Peso, Alergias, Estatura, Imagen
from persona 
where Correo='eKHjjd3n6512hOhcZ8x+QzVBcW3Gz+BpuM7XNAqKU48=';

select enfermedades.enfermedad from enfermedades
inner join enfermusu on enfermusu.Enfermedad=enfermedades.idenfermedades
inner join persona on persona.idPersona=enfermusu.idUsuario
where Correo='eKHjjd3n6512hOhcZ8x+QzVBcW3Gz+BpuM7XNAqKU48=';

select medicamentos.Medicamento, FechaInicio, FechaFin, periodo from relusumedicamentos
inner join medicamentos on medicamentos.idMedicamentos=relusumedicamentos.Medicamento
inner join persona on persona.idPersona=relusumedicamentos.idUsuario
where Correo='eKHjjd3n6512hOhcZ8x+QzVBcW3Gz+BpuM7XNAqKU48=';

