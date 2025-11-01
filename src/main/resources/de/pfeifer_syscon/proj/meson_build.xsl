<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text"/>
   <xsl:include href="param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
<xsl:text># use with:
# meson setup build
# cd build
# meson compile

project('</xsl:text><xsl:value-of select="$Proj"/><xsl:text>', 'c', 'cpp', default_options: ['c_std=c11', 'cpp_std=c++20'] )

subdir('res')
subdir('src')

genericimg_dep = dependency('genericimg')
thread_dep = dependency('threads')

executable(meson.project_name()
    , sources, app_resources
    , dependencies: [genericimg_dep, thread_dep ])
</xsl:text>
   </xsl:template>
</xsl:stylesheet>