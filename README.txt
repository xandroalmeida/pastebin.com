## Validação de campos (sem email)

```
curl --request POST \
  --url http://localhost:8080/users \
  --header 'content-type: application/json' \
  --data '{ "name": "Usuario de Teste", "password": "123456"}' -v
```

## Validação de campos (nome muito curto)

```
curl --request POST \
  --url http://localhost:8080/users \
  --header 'content-type: application/json' \
  --data '{ "name": "U", "email": "usuario.teste@gmail.com", "password": "123456"}' -v
```

## Criacao de um usuário válido

```
curl --request POST \
  --url http://localhost:8080/users \
  --header 'content-type: application/json' \
  --data '{ "name": "Usuario de Teste", "email": "usuario.teste@gmail.com", "password": "123456"}' -v
  ```
  
## usuário obtendo a informação de si mesmo
``` 
 curl --request GET \
  -u usuario.teste@gmail.com:123456 \
  --url http://localhost:8080/users/1
 ```
 
  