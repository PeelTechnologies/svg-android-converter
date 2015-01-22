<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:android="http://schemas.android.com/apk/res/android" version="1.0">
<xsl:output method="xml" indent="yes" />
  <!-- >xsl:template match="/svg">
    <svg xmlns:android="http://schemas.android.com/apk/res/android">
      <xsl:apply-templates select="@*|node()"/>
    </svg>
  </xsl:template-->
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()" />
    </xsl:copy>
  </xsl:template>
  <xsl:template match="@fill[parent::path]">
    <xsl:attribute name="android:fillColor">
      <xsl:value-of select="."/>
    </xsl:attribute>
  </xsl:template>
  <xsl:template match="@d[parent::path]">
    <xsl:attribute name="android:pathData">
      <xsl:value-of select="."/>
    </xsl:attribute>
  </xsl:template>
</xsl:stylesheet>
