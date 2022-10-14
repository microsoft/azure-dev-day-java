# Azure Dev Day - Serverless Lab

<!-- TOC -->
- [Requirements](#requirements)
- [Step 1: Setup Azure subscription and properties](#step-1-setup-azure-subscription-and-properties)
- [Step 2: Create an Azure Resource Group ](#step-2-create-an-azure-resource-group)
- [Step 3: Create Cosmos DB resources](#step-3-create-cosmos-db-resources)
- [Step 4: Create Function App](#step-4-create-function-app)
- [Step 5: Create Event Grid](#step-5-create-event-grid)
- [Step 6: Event Grid Blob Storage Test](#step-6-event-grid-blob-storage-test)
- [Step 7: Azure Cosmos DB Output Binding](#step-7-azure-cosmos-db-output-binding)
- [Step 8: Clean up resources](#step-8-clean-up-resources)
- [Bonus Material Order Management Orchestration](#bonus-material-order-management-orchestration)
- [Bonus Material Keda Scaling](#bonus-material-keda-scaling)

<!-- TOC -->

## Objectives

Azure Event Driven and Serverless offerings provide a wide array of capabilities to drive modern solution architecture. This lab demonstrates the following Azure services:

- Azure Event Grid
- Azure Function Apps
- Azure Cosmos DB

## Requirements

This example assumes the user already has an Azure subscription with contributor access. Additionally, the following services will be required during the lab:

--To Do--

## Step 1: Create a Resource Group

1. Log into the azure portal (https://portal.azure.com).
1. Select **Resource Groups** from the search bar results and select **+ Create**.
1. Enter the following values:
    1. Basics > Resource group: rg-add-serverless-[uniqueid]
    1. Basics > Region: East US

## Step 3: Create Cosmos DB resources

1. Log into the azure portal (https://portal.azure.com).
1. Select **Azure Cosmos DB** from the search bar results and select **+ Create**.
1. Select **Azure Cosmos DB for NoSQL**
1. Enter the following values:
    1. Basics > Resource group: rg-add-serverless-[uniqueid]
    1. Basics > Account name: cosmos-add-serverless-[uniqueid]
    1. Basics > Location: East US
1. Navigate to the Cosmos DB account.
1. Select **Data Explorer** from the left menu.
1. Select **New Container** and enter the following values:
    1. Database id: AzureDevDay
    1. Container id: Uploads
    1. Partition key: /id
1. Select **Settings > Keys** from the left menu and copy the **Primary Connection String** for use later.

## Step 3: Create a Storage Account

1. Log into the azure portal (https://portal.azure.com).
1. Select **Storage accounts** from the search bar results and select **+ Create**.
1. Enter the following values:
    1. Basics > Resource group: rg-add-serverless-[uniqueid]
    1. Basics > Storage Account name: staddsless[waymack]
    1. Basics > Region: East US

## Step 1: Create the Azure Function App

1. Log into the azure portal (https://portal.azure.com).
1. Select **Function App** from the search bar results and select **+ Create**.
1. Enter the following values:
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

## Step 3: Deploy the Function App

1. Open the **2-serverless-lab** folder in a terminal with the Azure CLI.
1. Ensure the Azure CLI is pointing to the right subscription by running

   ```bash
   az account show
   ```

1. Upload the deployment package with the following command:

    ```bash
    az functionapp deployment source config-zip -g rg-add-serverless-[uniqueid] -n func-add-serverless-[uniqueid] --src deploy-package.zip
    ```

## Step 5: Create the Event Grid Topic and Subscription

1. Log into the azure portal (https://portal.azure.com)
1. Select **Event Grid System Topics** from the search bar results and select **+ Create**
1. Enter the following values:
    1. Basics > Topic Types: Storage Accounts (Blob & GPv2)
    1. Basics > Resource group: rg-add-serverless-[uniqueid]
    1. Basics > Resource: staddsless[uniqueid]
    1. Basics > Name: eg-add-serverless-blob
    1. Basics > Region: East US
1. Navigate to the Event Grid topic.
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

## Step 6: Test the Solution



## Step 8: Clean up resources

Do NOT forget to remove the resources once you've completed the exercise, [Azure Group Delete](https://docs.microsoft.com/en-us/cli/azure/group?view=azure-cli-latest#az_group_delete)
