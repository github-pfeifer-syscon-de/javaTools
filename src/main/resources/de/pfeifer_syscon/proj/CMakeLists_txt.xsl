<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>
    <xsl:include href="param.xsl" />

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="root">
        <xsl:text>
# build with:
#  cmake -B build
#  cmake --build build
cmake_minimum_required(VERSION 3.0...4.0)
project(</xsl:text><xsl:value-of select="$Proj"/><xsl:text>)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(GRESOURCE_C resources.c)

find_package(PkgConfig)
pkg_check_modules(GTKMM gtkmm-3.0)
pkg_check_modules(GLIBMM glibmm-2.4 )

set(VERSION "</xsl:text><xsl:value-of select="$version"/><xsl:text>")
configure_file(config.h.in config.h @ONLY)

add_subdirectory(res)
add_subdirectory(src)
add_subdirectory(test)
        </xsl:text>
    </xsl:template>
</xsl:stylesheet>
