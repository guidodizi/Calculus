# PIS 2016 Calculus


El directorio **Calculus** contiene el proyecto creado con NetBeans y en webapps esta el **war** correspondiente.

----------

## DEPOLOYS
No hacerlo al menos de estar seguro que se esta haciendo y saber como hacer rollback (si no, preguntan a @tavidian).
Hay que compilar el proyecto, eso genera un .war
Luego:

cp Calculus/target/CalculusBackend.war webapps/
git add
git commit
git push origin master (Hay que estar en master!!!)
   
Asegurarse que las credenciales de bd (en persistence.xml) sean:
<properties>
  <property name="hibernate.connection.driver_class" value="org.postgresql.Driver"/>
  <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://nutty-custard-apple.db.elephantsql.com:5432/gxedfpdw"/>
  <property name="javax.persistence.jdbc.user" value="gxedfpdw"/>
  <property name="javax.persistence.jdbc.password" value="Ci05wYOZACatHbnzav_VmHiSIF6J-fNB"/>
  <property name="javax.persistence.jdbc.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
  <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
  <property name="javax.persistence.jdbc.show_sql" value="true"/>
</properties>


## PROTOTIPO ARQUITECTURA

Por ahora va a estar todo en un único servidor, pero ya estrucutrado de forma que se pueda separar en distintos servidores sin muchos problemas.

El proyecto contiene 4 paquetes:

- Logica
- ServletsAlumno
- ServletsProfesor
- Interfaces

### Logica

Este paquete contendrá todas las clases que implementan la lógica, lo que sería el Servidor Central. La idea es que todo el código en este paquete sea "autocontenido", o sea que no contenga dependencias o referencias a clases fuera de este paquete. De esta forma si en un futuro, por algún motivo se tiene que cambiar la lógica a un servidor propio, lo único que se tendría que hacer es agarrar este paquete entero, meterlo en otro servidor y exponer a los métodos de los controladores mediante web services o lo que sea.

### Interfaces

El equipo de Front-end obtendrá todos los datos necesarios de estas interfaces para generar la interfaz gráfica. 
Se va a tener una Fábrica para poder obtener la implementación de cada interfaz sin que el código de la parte web quede enganchado a la lógica.
Ahora que está todo junto, las implementacion de cada interfaz van a ser los respectivos controladores que están en la lógica, pero si en un futuro se separa todo, sólo se tendrían que implementar controladores que mapeen los métodos de las interfaces con los servicios web.


### Servlets

Los servlets que atienden al alumno y al profesar van en sus respectivos paquetes.


------

## Ejemplo

Si entran a http://pis2016.azurewebsites.net/Calculus/PruebaInterfazAlumno van a ver una respuesta por parte del servidor. En el proyecto está implementado un ejemplo con todo esto: un servlet PruebaInterfazAlumno, en el paquete AlumnoServlets, que obtiene la InterfazAlumno a través de la Fábrica, llama a uno de sus métodos y escribe un html (bien pedorro) con el resultado. 

