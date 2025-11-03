<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text"/>
   <xsl:include href="../param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
<xsl:text>
bin_PROGRAMS = </xsl:text><xsl:value-of select="$proj"/><xsl:text>

AM_CPPFLAGS = \
	-DPACKAGE_LOCALE_DIR=\""$(localedir)"\" \
        -DPACKAGE_SRC_DIR=\""$(srcdir)"\" \
        -DPACKAGE_DATA_DIR=\""$(pkgdatadir)"\" \
	$(GENERICIMG_CFLAGS) \
	$(GLIBMM_CFLAGS) \
	$(GTKMM_CFLAGS)

</xsl:text><xsl:value-of select="$proj"/><xsl:text>_CFLAGS = -Wall
</xsl:text><xsl:value-of select="$proj"/><xsl:text>_CXXFLAGS = -Wall \
	-Wpedantic \
	-Wconversion \
	-Wextra \
	-Wno-unused-parameter

</xsl:text><xsl:value-of select="$proj"/><xsl:text>_LDFLAGS = @EXTRA_LDFLAGS@

</xsl:text><xsl:value-of select="$proj"/><xsl:text>_LDADD =  \
	$(GENERICIMG_LIBS) \
	$(GLIBMM_LIBS) \
	$(GTKMM_LIBS)

</xsl:text><xsl:value-of select="$proj"/><xsl:text>_SOURCES = resources.c \
	</xsl:text><xsl:value-of select="$classApp"/><xsl:text>.cpp \
	</xsl:text><xsl:value-of select="$classApp"/><xsl:text>.hpp \
	</xsl:text><xsl:value-of select="$classWin"/><xsl:text>.cpp \
	</xsl:text><xsl:value-of select="$classWin"/><xsl:text>.hpp

# Remove ui directory on uninstall
uninstall-local:
	-rm -r $(pkgdatadir)

</xsl:text>
<!-- use with po EXTRA_DIST = m4/ChangeLog -->
</xsl:template>
</xsl:stylesheet>