# robertgraphql
convert graphql to hibernate sql 

./gradlew bootRun

Then go to localhost:8080/graphiql

and run 

```
{
  rob_departments(filter: [
    {
        name:{operator:contains, value: "test"},
        id:{operator:le, value: "10", queryOperator:"OR"}
     }
  ]) {
    id
    name
    organizations{
      id
      name
    }
    employees(filter:[
      {
        firstName:{operator:eq, value:"John"}
      }
    ]) {
      id
      firstName
      lastName
      addresses(
        filter:[
          {street:{operator:endsWith, value:"7"}},
          {street:{operator:contains, value:"Tes"}}
        ]
      ) {
        id
        street
      }
    }
   }
}
```