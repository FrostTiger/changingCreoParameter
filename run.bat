@echo  off
echo ---------------------------------------------
echo Creo has been started with j-link option...
echo ---------------------------------------------
cd C:\Users\Frosty\Documents\NetBeansProjects\jlinkhelloworld
del std.out
del trail.txt.1
set CREO_HOME="C:\Progra~1\PTC\Creo 3.0\F000\Common~1"
%CREO_HOME%\..\Parametric\bin\parametric.bat

