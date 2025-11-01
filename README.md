# javaTools
some tools that help me build Gtk-projects using Java8/xslt

To run use your favorite IDE or Maven (requires install)
<pre>
 mvn exec:java -Dexec.mainClass="de.pfeifer_syscon.proj.Wizard"
</pre>

Finder is a search and replace over multiple files,
previous version will be saved within .save.

Wizard is a Gtk/autotools project generator that
will create a starting project from some
defaults see lines Wizard.java:275f (you want to change these!).
After using the create function use
the usual steps for autotools:
<pre>
autoreconf -fis
./configure --prefix=/usr
</pre>
that gets you started with a basic Gtk project.
As an alternative meson may be prefere it:
<pre>
meson setup build
cd build
meson compile
</pre>
The wizard relies mostly on xslt-scripts so if youre
not a java user they might be portable to your favorite language.