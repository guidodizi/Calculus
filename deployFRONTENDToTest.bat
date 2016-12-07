set source=C:\PIS\Main\
set destination=C:\Program Files\Apache Software Foundation\apache-tomcat-8.0.37\webapps\

robocopy "%source%CalculusAdmin" "%destination%CalculusAdmin" /MIR /FFT /Z /XA:H /W:5
robocopy "%source%CalculusAlumnos" "%destination%CalculusAlumnos" /MIR /FFT /Z /XA:H /W:5
robocopy "%source%juego-triangle" "%destination%juego-triangle" /MIR /FFT /Z /XA:H /W:5
