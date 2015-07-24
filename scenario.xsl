<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" encoding="utf-8" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
<xsl:template match="/">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>Scenario</title>
</head>
<body>
<table border="1">
  <tr>
    <th scope="col">Action</th>
    <th scope="col">Type</th>
  </tr>
<xsl:for-each select="Scenario/Action">
  <tr>
    <td>
	<xsl:choose> 
		<xsl:when test="@class='info.kapable.sondes.scenarios.action.LancerApplication'"> 
			<xsl:variable name="url" select="param/@value"/>
			Ouvrir l'URL : <a href="{$url}"><xsl:value-of select="param/@value"/></a>
		</xsl:when> 
		<xsl:when test="@class='info.kapable.sondes.scenarios.action.AttendreAction'">
			Attendre : <b><xsl:value-of select="param/@value"/></b>ms
		</xsl:when> 
		<xsl:when test="@class='info.kapable.sondes.scenarios.action.VerifierTexteDansPageAction'">
			Verifier la présence du texte : <b><xsl:value-of select="param[1]/@value"/></b> dans la page (afficher "<xsl:value-of select="param[1]/@value"/>" si le texte n'est pas présent)
		</xsl:when> 
		<xsl:when test="@class='info.kapable.sondes.scenarios.action.SaisirTexteAction'">
			Saisir le texte : <b><xsl:value-of select="param[2]/@value"/></b> dans l'élément HTML : <b><xsl:value-of select="param[1]/@selector"/>=<xsl:value-of select="param[1]/@value"/></b>
		</xsl:when> 
		<xsl:when test="@class='info.kapable.sondes.scenarios.action.ClickerAction'">
			Cliquer sur l'élément HTML : <b><xsl:value-of select="param[1]/@selector"/>=<xsl:value-of select="param[1]/@value"/></b>
		</xsl:when> 
	</xsl:choose>
	</td>
    <td><xsl:value-of select="@class"/></td>
  </tr>
</xsl:for-each>
</table>
</body>
</html>
	</xsl:template>
</xsl:stylesheet>
