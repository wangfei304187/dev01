
http://jogamp.org/wiki/index.php/JogAmp_JAR_File_Handling

--> [Eclipse]

Manual tested w/ Eclipse:
   
   Preparations:
   ===============
   
   1) Set up a vanilla eclipse (3.7.0) workspace
   
   2) Add the JOGL User Library:
     - Window.Preference
      - Java.Build_Path.User_Libraries:
        + JOGL
           + gluegen-rt.jar
           + jogl-all.jar
           + gluegen-rt-natives-linux-amd64.jar
           + jogl-all-natives-linux-amd64.jar
   
           You may add all other native JARs here.
           Note that these are not required in the CLASSPATH by JOGL,
           however, they are required by Eclipse to export your project as a Runnable JAR File.
           

