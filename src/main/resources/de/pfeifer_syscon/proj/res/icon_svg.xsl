<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="xml" version="1.0" indent="yes" />
   <xsl:include href="../param.xsl" />

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="root">
        <xsl:text>
</xsl:text>
<svg
   width="80mm"
   height="80mm"
   viewBox="0 0 80 80"
   version="1.1"
   id="svg1"
   xmlns="http://www.w3.org/2000/svg"
   xmlns:svg="http://www.w3.org/2000/svg">
  <defs id="defs1" />
  <g id="layer1">
    <text xml:space="preserve"
       style="font-size:20px;line-height:1.25;font-family:sans-serif;fill:#ffffff;fill-opacity:1"
       x="5"
       y="45"
       id="text1">
        <tspan id="tspan1"><xsl:value-of select="$Proj"/></tspan>
    </text>
  </g>
</svg>
    </xsl:template>
</xsl:stylesheet>
