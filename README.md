# javaTools
some tools that help me build Gtk-projects using mostly Java8/xslt

To run use your favorite IDE or use Maven (requires install)
<pre>
 mvn exec:java -Dexec.mainClass="de.pfeifer_syscon.proj.Wizard"
</pre>

Finder is a search and replace over multiple files,
previous version will be saved within .save.

Wizard is a Gtk/autotools project generator that
will create a starting project from some
defaults see lines ~275 (you want to change these!).
After using the create function use
the usual steps:
<pre>
autoreconf -fis
./configure --prefix=/usr
</pre>
should be sufficent to get you stated with a simple Gtk project.
The wizard relies mostly on xslt-scripts so if youre
not a java user they might be portable to your favorite language.