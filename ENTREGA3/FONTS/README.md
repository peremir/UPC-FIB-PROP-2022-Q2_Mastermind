## Compilació del codi font

Hem utilitzat l'eina Gradle per realitzar la compilació i executar els diferents tests.

En aquesta carpeta podem trobar dues carpetes, dins la carpeta anomenada java trobem el codi de les diferents classes implementades per nosaltres. Dins la carepta JUnit trobem els diferents tests utilitzant Junit i Mockito.

Per compilar el codi en mac i linux només hem de fer:
```
./gradlew distZip
```
en cas d'estar utilitzant windows haurem de fer:
```
./gradlew.bat distZip
```

Aquestsa comanda ens generar una carpeta build, on dins d'aquesta hi trobarem una carpeta distributions, on dins d'ella trobarem un arxiu zip.
Si descomprimim aquesta carpeta, trobarem en el seu interior dos capretes més bin i libs, si el que volem és executar el codi, el que haurem de fer és anar a la carpeta bin i executar l'executable que pertoqui depenent del nostre sistema operatiu.
