# Título
ApiSkins

Descripción breve
ApiRest para gestión de skins de un videojuego.
Permite las operaciones: Obtener las skins disponibles, Comprar, Obtener skins compradas, Cambiar color skin,Obtener skin por ID, Añadir Skins mediante JSon y Eliminar skin.

## Tabla de Contenidos
- #instalación codigo
- #Creación de BD
- #uso

## Instalación codigo
1. Descargar proyecto desde Github
2. Hacer un maven install.
3. Levantar servidor web (si se usa Spring Tool Suite 4, está integrado "Tomcat").


##Creación de BD
1- Instalar algun gestor de BD SQL (Recomendado MySQL)
2- Importar BD mediante línea de comandos o interfaz gráfica. (La ubicación del fichero sql es "/ApiSkins/src/main/resources/configurations/BDskins_IngridAA.sql".)

## Uso de la API
1-) Obtener skins disponibles:
-Método petición http: GET
-Ruta: http://localhost:8080/skins/available
-Retorno: Skins disponibles en formato JSON.

2-) comprar skins:
-Método petición http: POST
-Ruta: http://localhost:8080/skins/buy
-Parámetros POST: "id"=[idSkinComprar]
-Retorno: "True" o "false" dependiendo de si se ha completado la compra.

3-) Obtener skins compradas:
-Método petición http: GET
-Ruta: http://localhost:8080/skins/myskins?userId=[id_usuario]
-Retorno: Skins compradas en formato JSON

4-) Cambiar color skin:
-Método petición http: PUT
-Ruta: http://localhost:8080/skins/color
-Retorno: ResponseEntity
-Parámetros PUT: Cadena formato JSON con la siguiente estructura, (donde "id" es el id de la skin):
{
  "id": 2,
  "color": "black"
}

5-) Obtener skin por id:
-Método petición http: GET
-Ruta: http://localhost:8080/skins/getskin/1
-Retorno: Información de la skin en formato JSON

6-) Subir skins a la BD pasándole un fichero JSON:
-Método petición http: POST
-Ruta: http://localhost:8080/skins/uploadJsonFile
-Retorno: ResponseEntity
-Parámetros POST: "file" = ficheroJSON (form-data) con la siguiente estructura:
[
    {
        "id": 1,
        "nombre": "Pili",
        "tipo": "mediaval",
        "precio": 2.0,
        "color": "red"
    },
    {
        "id": 2,
        "nombre": "bluefashion",
        "tipo": "fashionn",
        "precio": 200.0,
        "color": "blue"
    }
]

7-) Eliminar skin comprada:
-Método petición http: DELETE
-Ruta: http://localhost:8080/skins/delete/[idSkin]
-Retorno: ResponseEntity

