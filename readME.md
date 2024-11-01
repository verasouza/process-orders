## Desafio

- receber arquivo via REST e processa-lo
- Exemplo de entrada:

``
0000000002 Medeiros00000123450000000111 256.2420201201``

- Exemplo de saída:

```json
{
  "user_id":1,
  "name":"Zarelli",
  "orders":[
    {
      "order_id":123,
      "total":"1024.48",
      "date":"2021-12-01",
      "products":[
        {
          "product_id":111,
          "value":"512.24"
        },
        {
          "product_id":122,
          "value":"512.24"
        }
      ]
    }
  ]
}
```

### Solução aplicada

- o arquivo é recebido no endpoint:
  
> http://localhost:8088/files/upload

- cuRl:

```shell
 curl --request POST \
--url http://localhost:8088/files/upload \
--header 'Content-Type: multipart/form-data' \
--header 'User-Agent: Insomnia/2023.5.7' \
--cookie JSESSIONID=0125342ECEDC59698711B53B6DBBB75A \
--form 'file=@data_1.txt'
 ```
- o arquivos é processado e salvo no banco para consultas futuras;
- é possível fazer uma consulta usando os seguintes filtros:
``` json
{
    "orderId":"",
    "startDate":"2021-11-01",
    "endDate":"",
    "username":"",
    "page":1
}
```
- endpoint de consulta: http://localhost:8088/orders/filter
- swagger: http://localhost:8088/swagger-ui/index.html
