package com.github.bawey.graphqljava101;


import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

public class HelloGraphQL {

    final static String rawSchemaString = "type Query{hello: String}";
    final static SchemaParser schemaParser = new SchemaParser();
    final static SchemaGenerator schemaGenerator = new SchemaGenerator();

    public static void main(String[] args) {

        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(rawSchemaString);
        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        // A GraphQLSchema instance is created using TypeDefinitionRegistry and RuntimeWiring instances
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        // GraphQL instance is built from an instance of GraphQLSchema
        GraphQL graphQl = GraphQL.newGraphQL(graphQLSchema).build();

        // GraphQL instance can accept requests
        ExecutionResult executionResult = graphQl.execute("{hello}");

        System.out.println(executionResult.getData().toString());
        // Prints: {hello=world}
    }
}


