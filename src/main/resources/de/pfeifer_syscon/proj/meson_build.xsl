<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text"/>
   <xsl:include href="param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

// this might be helpful: https://mesonbuild.com/Porting-from-autotools.html
   <xsl:template match="root">
<xsl:text># use with:
# meson setup build
# cd build
# meson compile

project('</xsl:text><xsl:value-of select="$Proj"/><xsl:text>', 'c', 'cpp', default_options: ['c_std=c11', 'cpp_std=c++20'] )

subdir('res')
subdir('src')
subdir('test')

conf = configuration_data()
as_version = '</xsl:text><xsl:value-of select="$version"/><xsl:text>'
# Surround the version in quotes to make it a C string
conf.set_quoted('VERSION', as_version)
configure_file(output : 'config.h',
               configuration : conf)

# genericimg_dep = dependency('genericimg', version : '>= 0.4.0')
gtkmm3_dep = dependency('gtkmm-3.0')
glibmm2_dep = dependency('glibmm-2.4')
thread_dep = dependency('threads')

deps = [gtkmm3_dep, glibmm2_dep, thread_dep ]

executable(meson.project_name()
    , sources, app_resources
    , dependencies: deps)
</xsl:text>
   </xsl:template>
</xsl:stylesheet>