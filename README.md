# javaTools
some tools that help me build Gtkmm3-projects using Java8/xslt

To run use your favorite IDE or Maven (requires install) e.g.
<pre>
 mvn exec:java -Dexec.mainClass="de.pfeifer_syscon.proj.Wizard"
</pre>

Finder is a search and replace over multiple files,
previous version will be saved within .save.

Wizard is a Gtkmm3/autotools project generator that
will create a starting project from some
defaults see Wizard.java (you want to change these!).

The wizard relies mostly on xslt-scripts so if youre
not a java fan they might be portable to your favorite language.
