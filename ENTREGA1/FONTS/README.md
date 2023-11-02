## Compilació del codi font

Hem utilitzat l'eina Gradle per realitzar la compilació i executar els diferents tests.

En aquesta carpeta podem trobar dues carpetes, dins la carpeta anomenada java trobem el codi de les diferents classes implementades per nosaltres. Dins la carepta JUnit trobem els diferents tests utilitzant Junit i Mockito.

Per compilar el codi en mac i linux només hem de fer:
```
./gradlew build
```
en cas d'estar utilitzant windows haurem de fer:
```
./gradlew.bat build
```

Aquestsa comanda ens generar una carpeta build, on dins d'aquesta hi trobarem, els diferents arxius classe de java, així com un fitxer .jar, per la facilitat d'ús del programa.
