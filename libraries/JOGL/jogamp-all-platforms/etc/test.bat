
set BLD_DIR=jar

set CP_ALL=.;%BLD_DIR%\gluegen-rt.jar;%BLD_DIR%\jogl-all.jar
echo CP_ALL %CP_ALL%

set X_ARGS="-Dsun.java2d.noddraw=true" "-Dsun.awt.noerasebackground=true"

REM java -classpath %CP_ALL% %X_ARGS% com.jogamp.opengl.awt.GLCanvas > java-win64.log 2>&1

REM java -classpath %CP_ALL% %X_ARGS% com.jogamp.common.GlueGenVersion > application.test.log 2>&1
REM java -classpath %CP_ALL% %X_ARGS% com.jogamp.nativewindow.NativeWindowVersion >> application.test.log 2>&1
REM java -classpath %CP_ALL% %X_ARGS% com.jogamp.opengl.JoglVersion >> application.test.log 2>&1
REM java -classpath %CP_ALL% %X_ARGS% com.jogamp.newt.NewtVersion >> application.test.log 2>&1
REM java -classpath %CP_ALL% %X_ARGS% com.jogamp.newt.opengl.GLCanvas >> application.test.log 2>&1
java -classpath %CP_ALL% %X_ARGS% com.jogamp.newt.opengl.GLWindow >> application.test.log 2>&1

type application.test.log
