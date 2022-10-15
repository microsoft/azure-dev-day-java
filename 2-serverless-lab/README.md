# Azure Dev Day - Serverless Lab

<!-- TOC -->
- [Requirements](#requirements)
- [Step 1: Create a Resource Group](#step-1-create-a-resource-group)
- [Step 2: Create Cosmos DB resources](#step-2-create-cosmos-db-resources)
- [Step 3: Create a Storage Account](#step-3-create-a-storage-account)
- [Step 4: Create the Function App](#step-4-create-the-function-app)
- [Step 5: Deploy the Function App](#step-5-deploy-the-function-app)
- [Step 6: Create the Event Grid Topic and Subscription](#step-6-create-the-event-grid-topic-and-subscription)
- [Step 7: Test the Solution](#step-7-test-the-solution)
- [Step 8: Clean up resources](#step-8-clean-up-resources)

<!-- TOC -->

## Objectives

Azure Event Driven and Serverless offerings provide a wide array of capabilities to drive modern solution architecture. This lab demonstrates the following Azure services:

- Azure Event Grid
- Azure Function Apps
- Azure Cosmos DB

## Requirements

This example assumes the user already has an Azure subscription with contributor access.

## Step 1: Create a Resource Group

1. In the Azure Portal (<https://portal.azure.com>), select **Resource Groups** from the search bar
1. Select **+ Create** and enter the following values:
    1. Basics > Resource group: rg-add-serverless-[uniqueid]
    1. Basics > Region: East US

## Step 2: Create Cosmos DB resources

1. In the Azure portal (<https://portal.azure.com>), select **Azure Cosmos DB** from the search bar
1. Select **+ Create**, **Azure Cosmos DB for NoSQL**, and enter the following values:
    1. Basics > Resource group: rg-add-serverless-[uniqueid]
    1. Basics > Account name: cosmos-add-serverless-[uniqueid]
    1. Basics > Location: East US
1. Navigate to the Cosmos DB account.
1. Select **Data Explorer** from the left menu
1. Select **New Container** and enter the following values:
    1. Database id: AzureDevDay
    1. Container id: Uploads
    1. Partition key: /id
1. Select **Settings > Keys** from the left menu and copy the **Primary Connection String** for use later

## Step 3: Create a Storage Account

1. In the Azure Portal (<https://portal.azure.com>), select **Storage accounts** from the search bar 
1. Select **+ Create** and enter the following values:
    1. Basics > Resource group: rg-add-serverless-[uniqueid]
    1. Basics > Storage Account name: staddsless[waymack]
    1. Basics > Region: East US

## Step 4: Create the Function App

1. In the Azure Portal (<https://portal.azure.com>), select **Function App** from the search bar
1. Select **+ Create** and enter the following values:
    1. Basics > Resource group: rg-add-serverless-[uniqueid]
    1. Basics > Function App name: func-add-serverless-[uniqueid]
    1. Basics > Runtime stack: Java
    1. Basics > Version: 11
    1. Basics > Region: East US
    1. Hosting > Storage account: staddsless[uniqueid]
1. Navigate to the Function App.
1. Select **Settings > Configuration** and add the following **Application Setting**:
    1. CosmosConnectionString: [Primary connection string from above]
    1. DatabaseName: AzureDevDay
    1. CollectionName: Uploads

## Step 5: Deploy the Function App

1. In the Azure Portal (<https://portal.azure.com>), open the Cloud Shell
1. If you haven't already, clone the Azure Dev Day repository:

    ```bash
    git clone https://github.com/microsoft/azure-dev-day-java
    ```

1. Navigate to the **2-serverless-lab** folder

    ```bash
    cd .\azure-dev-day-java\2-serverless-lab
    ```

1. Ensure the Azure CLI is pointing to the right subscription:

   ```bash
   az account show
   ```

1. Upload the deployment package with the following command:

    ```bash
    az functionapp deployment source config-zip -g rg-add-serverless-[uniqueid] -n func-add-serverless-[uniqueid] --src deploy-package.zip
    ```

## Step 6: Create the Event Grid Topic and Subscription

1. In the Azure Portal (<https://portal.azure.com>), select **Event Grid System Topics** from the search bar
1. Select **+ Create** and enter the following values:
    1. Basics > Topic Types: Storage Accounts (Blob & GPv2)
    1. Basics > Resource group: rg-add-serverless-[uniqueid]
    1. Basics > Resource: staddsless[uniqueid]
    1. Basics > Name: eg-add-serverless-blob
    1. Basics > Region: East US
1. Navigate to the Event Grid topic
1. Select **+ Event Subscription**
1. Enter the following values:
    1. Basics > Name: FuncSub
    1. Basics > Filter to Event Types: Blob Created
    1. Basics > Endpoint Type: Azure Function
    1. Basics > Endpoint:
        1. Resource group: rg-add-serverless-[uniqueid]
        1. Function app: func-add-serverless-[uniqueid]
        1. Slot: Production
        1. Function: ProcessFileUpload

## Step 7: Test the Solution

1. In the Azure Portal (<https://portal.azure.com>), navigate to the Storage Account created earlier
1. Select **Data storage > Containers** and select **+ Container** with the following values:
    1. Name: uploads
1. Select the **uploads** container and select **^ Upload**
    1. Upload any file from your machine
1. In the browser, navigate to the Function App endpoint that displays all uploaded file information

    ```bash
    https://func-add-serverless-[uniqueid].azurewebsites.net/api/uploads
    ```

## Step 8: Clean up resources

Do NOT forget to remove the resources once you've completed the exercise, [Azure Group Delete](https://docs.microsoft.com/en-us/cli/azure/group?view=azure-cli-latest#az_group_delete)
