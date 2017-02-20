<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
  <meta content="text/html; charset=ISO-8859-1"
 http-equiv="content-type">
  <title>Resultat</title>
</head>
<body>
<table style="text-align: left; width: 800px; height: 60px;"
 border="1" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="width: 390px;"><#if testInfo.logo??>${testInfo.logo}</#if></td>
      <td style="width: 390px;"><#if testInfo.short??>${testInfo.short}</#if></td>
    </tr>
    <tr>
      <td style="width: 390px;" colspan="2" rowspan="1"><#if testInfo.long??>${testInfo.long}</#if></td>
    </tr>
  </tbody>
</table>
<br>
<h2>Resultat :</h2>
<table style="text-align: left; width: 800px; height: 176px;"
 border="1" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="width: 392px;"></td>
      <td style="width: 179px;">Resultat</td>
      <#if OK>
      	<td style="width: 201px; background-color: rgb(51, 255, 51);">OK</td>
      <#else>
      	<td style="width: 201px; background-color: red;">KO</td>
      </#if>
    </tr>
    <tr>
      <td style="width: 392px;" colspan="1" rowspan="3">Variables
d'environnements utilisées :<br>
      <ul>
      	<#list envVars?keys as key>
        <li>${key} : ${envVars[key]}</li>
        </#list>
      </ul>
      </td>
      <td style="width: 179px;">Durée du test</td>
      <td style="width: 201px;">${durationStr}</td>
    </tr>
    <tr>
      <td style="width: 179px;">Début</td>
      <td style="width: 201px;">${startTimeStr}</td>
    </tr>
    <tr>
      <td style="width: 179px;">Fin</td>
      <td style="width: 201px;">${endTimeStr}</td>
    </tr>
  </tbody>
</table>
<h2>Actions :</h2>
<table style="text-align: left; width: 801px; height: 212px;"
 border="1" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="width: 251px;">Action</td>
      <td style="width: 535px;">Commentaire</td>
      <td>Résultat</td>
    </tr>
    <#list results as result>
    <tr>
      <td style="width: 251px;">${result.description}</td>
      <#if result.OK>
      <td style="width: 535px;">${result.durationStr}</td>
      <td style="background-color: rgb(51, 255, 51);">OK</td>
      <#else>
      <td style="width: 535px;background-color: red;">${result.erreur} (durée : ${result.durationStr})</td>
      <td style="background-color: red;">KO</td>
      </#if>
    </tr>
    </#list>
  </tbody>
</table>
<br>
<#if OK == false>
<h2>Capture de l'erreur :</h2>
<img style="width: 800px; height: 450px;" alt="Erreur"
 src="erreur.png"><br>
</#if>
</body>
</html>