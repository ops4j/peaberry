
     peaberry - dynamic service extension for Google-Guice


  What is it?
  -----------

  The peaberry project is an extension library for Google-Guice
  that supports dependency injection of dynamic services.

  It provides OSGi integration out of the box, and has plug-in
  support for other registry-based service frameworks. It ships
  as an OSGi bundle that can be dropped into any R4 framework,
  such as Apache Felix or Eclipse/Equinox.


  Using peaberry
  --------------

  To use peaberry in an OSGi application, install the following
  bundles from the distribution onto your chosen OSGi framework:

    aopalliance-@AOP_VERSION@.jar
    guice-@GUICE_VERSION@.jar
    peaberry-@VERSION@.jar

  To use peaberry in a non-OSGi application, add the above group
  of bundles to your application classpath (in the order shown).

  The peaberry distribution contains the AOP Alliance API classes
  repackaged as an OSGi bundle and a snapshot of the latest Guice
  codebase.

  These bundles can be replaced with alternative compatible jars.


  The Latest Version
  ------------------

  The latest version can be found at <http://code.google.com/p/peaberry/>


  Documentation
  -------------

  API documentation is available in the javadoc jar.

  Quick summary:

    "org.ops4j.peaberry.Peaberry" builds the service providers.

    "org.ops4j.peaberry.util" contains optional helper classes.

  Injecting a dynamic service from OSGi:

    @Inject
    LogService logService;
    ...
    bind(LogService.class).to(service(LogService.class).single());

  Exporting an implementation as an OSGi service:

    @Inject
    Export<LogService> exportedLogService;
    ...
    bind(export(LogService.class)).to(registration(logImplKey).export());

  More examples can be found in the javadoc for the Peaberry class.


  Licensing
  ---------

  This software is licensed under the terms you may find in the
  file named "LICENSE" in this directory. The distribution also
  contains binary classes from the ASM project, licensed under
  terms found in the file named "LICENSE.asm".


  Thank you for using peaberry.

