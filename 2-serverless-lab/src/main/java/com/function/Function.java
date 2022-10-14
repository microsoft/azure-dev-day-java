package com.function;

import java.util.Optional;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

public class Function {
    @FunctionName("ProcessFileUpload")
    public void ProcessFileUpload(
            @EventGridTrigger(name = "event") String uploadData,
            @CosmosDBOutput(name = "database",
                databaseName = "%DatabaseName%",
                collectionName = "%CollectionName%",
                connectionStringSetting = "CosmosConnectionString") OutputBinding<String> outputItem,
                final ExecutionContext context) {
        context.getLogger().info("Uploaded data: " + uploadData);
        outputItem.setValue(uploadData);
    }
    
    @FunctionName("GetUploads")
    public HttpResponseMessage GetUploads(
            @HttpTrigger(
                name = "req",
                methods = { HttpMethod.GET },
                route = "uploads",
                authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            @CosmosDBInput(
                name = "database",
                databaseName = "%DatabaseName%",
                collectionName = "%CollectionName%",
                connectionStringSetting = "CosmosConnectionString",
                sqlQuery = "select * from u") String uploads,
            final ExecutionContext context) {
        context.getLogger().info("Retrieved uploads.");
        return request.createResponseBuilder(HttpStatus.OK).body(uploads).build();
    }
    
    @FunctionName("GetUpload")
    public HttpResponseMessage GetUpload(
            @HttpTrigger(
                name = "req",
                methods = { HttpMethod.GET },
                    route = "uploads/{id}", 
                authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            @CosmosDBInput(
                name = "database",
                databaseName = "%DatabaseName%",
                collectionName = "%CollectionName%",
                connectionStringSetting = "CosmosConnectionString",
                id = "{id}",
                partitionKey = "{id}"
            ) Optional<String> upload,
            
            final ExecutionContext context) {

        if (upload.isPresent()) {
            context.getLogger().info("Retrieved upload.");
            return request.createResponseBuilder(HttpStatus.OK).body(upload.get()).build();
        } else {
            context.getLogger().warning("No upload with id found.");
            return request.createResponseBuilder(HttpStatus.NOT_FOUND).body("No upload with id found.").build();
        }
    }
}
