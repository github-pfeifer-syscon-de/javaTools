<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"/>
   <xsl:include href="../param.xsl" />

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="root">
        <xsl:variable name="test_prog" select="concat($proj, '_test')"/>
        <xsl:variable name="test_src" select="concat($test_prog, '.cpp')"/>
<xsl:text>
glibmm2_dep = dependency('glibmm-2.4')
thread_dep = dependency('threads')

deps = [glibmm2_dep, thread_dep ]

e = executable('</xsl:text><xsl:value-of select="$test_prog"/><xsl:text>'
    , '</xsl:text><xsl:value-of select="$test_src"/><xsl:text>'
    , dependencies: deps)
test('</xsl:text><xsl:value-of select="$test_prog"/><xsl:text>', e)
</xsl:text>
    </xsl:template>

</xsl:stylesheet>
