<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text"/>
   <xsl:include href="../param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
        <xsl:variable name="test_prog" select="concat($proj, '_test')"/>
        <xsl:variable name="test_src" select="concat($test_prog, '.cpp')"/>
<xsl:text>
TESTS = </xsl:text><xsl:value-of select="$test_prog"/><xsl:text>

check_PROGRAMS = </xsl:text><xsl:value-of select="$test_prog"/><xsl:text>

AM_CPPFLAGS = \
	-DPACKAGE_LOCALE_DIR=\""$(localedir)"\" \
	-DPACKAGE_SRC_DIR=\""$(srcdir)."\" \
	-DPACKAGE_DATA_DIR=\""$(pkgdatadir)"\" \
	$(GENERICIMG_CFLAGS) \
	$(GLIBMM_CFLAGS) \
	-I ../src

AM_CFLAGS= -Wall

AM_CXXFLAGS = \
	-Wpedantic \
	-Wconversion \
	-Wextra \
	-Wno-unused-parameter

AM_LDFLAGS =

</xsl:text><xsl:value-of select="$test_prog"/><xsl:text>_LDADD =  \
	$(GLIBMM_LIBS) \
	$(GENERICIMG_LIBS)

</xsl:text><xsl:value-of select="$test_prog"/><xsl:text>_SOURCES = \
    </xsl:text><xsl:value-of select="$test_src"/><xsl:text>
</xsl:text>
</xsl:template>
</xsl:stylesheet>