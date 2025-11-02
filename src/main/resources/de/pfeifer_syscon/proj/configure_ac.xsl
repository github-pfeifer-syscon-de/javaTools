<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:output method="text"/>
   <xsl:include href="param.xsl" />

   <xsl:template match="/">
        <xsl:apply-templates/>
   </xsl:template>

   <xsl:template match="root">
<xsl:text>
dnl Process this file with autoconf to produce a configure script.

AC_CONFIG_MACRO_DIR([m4])

AC_INIT([</xsl:text><xsl:value-of select="$proj"/><xsl:text>],[</xsl:text><xsl:value-of select="$version"/><xsl:text>],[</xsl:text><xsl:value-of select="$mail"/><xsl:text>])

dnl point to unique-file-in-source-dir, for safety check
AC_CONFIG_SRCDIR([src/</xsl:text><xsl:value-of select="$classApp"/><xsl:text>.cpp])

dnl foreign -> leaves out INSTALL,
dnl otherwise -> there is complaining about the shell, sorry for all non shell places :(
AM_INIT_AUTOMAKE([1.11])

AC_CONFIG_HEADERS([config.h])

AM_SILENT_RULES([yes])

AX_CHECK_ENABLE_DEBUG([info], [DEBUG], [RELEASE], [IS-RELEASE])
AC_PROG_CC
AC_PROG_CXX
AX_CXX_COMPILE_STDCXX([20], [noext], [optional])

LT_INIT

AC_CANONICAL_HOST
AC_MSG_NOTICE([host_os $host_os])

dnl don't repeat dependencies
PKG_CHECK_MODULES([GTKMM], [gtkmm-3.0])
PKG_CHECK_MODULES([GLIBMM], [glibmm-2.4 giomm-2.4])

dnl PKG_CHECK_MODULES([GENERICIMG],[genericimg >= 0.4.0])

dnl for nls use:
dnl AM_GNU_GETTEXT([external])
dnl and with AC_CONFIG_FILES:
dnl po/Makefile.in

if test "$host_os" = "mingw32" ; then
    AC_SUBST(EXTRA_LDFLAGS, "-mwindows")
fi

AC_CONFIG_FILES([
Makefile
test/Makefile
res/Makefile
src/Makefile
])
AC_OUTPUT
</xsl:text>
   </xsl:template>
</xsl:stylesheet>