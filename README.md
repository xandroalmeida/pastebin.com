
## Como executar o projeto sistema

O projeto esta usando um banco de dados (H2) embutido, então não existe a necessidade de nenhum servidor externo.

A pasta ./data será utilizada para armazenar or arquivos enviados e do banco de dados. Não é necessário criar esta pasta, o sistema irá cria-la automaticamente.

```
mvn clean package

java -jar ./target/backend-0.0.1-SNAPSHOT.jar
```

## Script de Testes

### Criação dos usuário

Usuário 1

```
curl --request POST \
  --url http://localhost:8080/users \
  --header 'content-type: application/json' \
  --data '{ "name": "Usuario 1", "email": "usuario1.teste@gmail.com", "password": "123456"}' -v
```

Usuário 2

```
curl --request POST \
  --url http://localhost:8080/users \
  --header 'content-type: application/json' \
  --data '{ "name": "Usuario 2", "email": "usuario2.teste@gmail.com", "password": "123456"}' -v
```

### Login

A autenticação da API está bastante simples (por questões prática) usando o HTTP Basic, então o login é apenas para pegar o token encodado para ser colocado nas requisições que necessitem de autenticação.


Inválido

```
curl --request POST \
  --url http://localhost:8080/users/login \
  --form email=usuario1.teste@gmail.com \
  --form password=qwerty -v
```

Válido 

```
curl --request POST \
  --url http://localhost:8080/users/login \
  --form email=usuario1.teste@gmail.com \
  --form password=123456
```


  
### Usuário obtendo as informações de si mesmo

Obs: Não é possível obter informações de outros usuário

``` 
 curl --request GET \
  --header 'authorization: Basic <TOKEN OBTIDO NO LOGIN>' \
  --url http://localhost:8080/users/me -v
 ```
 
### Enviar os arquivos

Arquivos Publico 


``` 
curl -i -X POST -H "Content-Type: multipart/form-data"  \
  -F "postFile=@<PATH_DO_ARQUIVO_ENVIADO>" \
  -F"publicAccess=true" \
  --header 'authorization: Basic <TOKEN OBTIDO NO LOGIN_USUARIO1>' \
  http://localhost:8080/posts -v
  
  curl -i -X POST -H "Content-Type: multipart/form-data"  \
  -F "postFile=@<PATH_DO_ARQUIVO_ENVIADO>" \
  -F"publicAccess=true" \
  --header 'authorization: Basic <TOKEN OBTIDO NO LOGIN_USUARIO2>' \
  http://localhost:8080/posts -v
```
  
Arquivos Privado 

``` 
curl -i -X POST -H "Content-Type: multipart/form-data"  \
  -F "postFile=@<PATH_DO_ARQUIVO_ENVIADO>" \
  -F"publicAccess=false" \
  --header 'authorization: Basic <TOKEN OBTIDO NO LOGIN_USUARIO1>' \
  http://localhost:8080/posts -v
  
  curl -i -X POST -H "Content-Type: multipart/form-data"  \
  -F "postFile=@<PATH_DO_ARQUIVO_ENVIADO>" \
  -F"publicAccess=false" \
  --header 'authorization: Basic <TOKEN OBTIDO NO LOGIN_USUARIO2>' \
  http://localhost:8080/posts -v
```

### Lista todos os Posts publicos

``` 
 curl --request GET \
  --url http://localhost:8080/posts -v
 ```

### Lista todos os Posts que cada usuário tem acesso (publicos e privados)

``` 
 curl --request GET \
 --header 'authorization: Basic <TOKEN OBTIDO NO LOGIN_USUARIO1>' \
  --url http://localhost:8080/posts -v
  
 curl --request GET \
 --header 'authorization: Basic <TOKEN OBTIDO NO LOGIN_USUARIO2>' \
  --url http://localhost:8080/posts -v
  
 ```
 
### Obtendo o conteúdo dos Posts
 

Público

 ```
 curl --request GET   --url http://localhost:8080/posts/2
 ```
 
Private

 ```
curl --request GET \
  --header 'authorization: Basic <TOKEN OBTIDO NO LOGIN_USUARIO2>'  
  --url http://localhost:8080/posts/3 
 ```
  