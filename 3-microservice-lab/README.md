# Azure Dev Day - Microservice Lab

- [Step 1: Create a Resource Group](#step-1-create-a-resource-group)
- [Step 2: Create a Container Registry](#step-2-create-a-container-registry)
- [Step 3: Push Container Images](#step-3-push-container-images)
- [Step 4: Create the Web API Container App](#step-4-create-the-web-api-container-app)
- [Step 5: Create the Web App Container App](#step-5-create-the-web-app-container-app)
- [Step 6: Create a New Web API Revision](#step-6-create-a-new-web-api-revision)
- [Step 7: Clean up Resources](step-7-clean-up-resources)


## Objectives

Azure container offerings allow for robust microservice solutions. This lab demonstrates the following Azure services:  

- Azure Container Registry
- Azure Container Apps

## Step 1: Create a Resource Group

1. In the Azure Portal (<https://portal.azure.com>), select **Resource Groups** from the search bar
1. Select **+ Create** and enter the following values:
    1. Basics > Resource group: rg-add-microservice-[uniqueid]
    1. Basics > Region: East US

## Step 2: Create a Container Registry

1. In the Azure Portal (<https://portal.azure.com>), select **Container registries** from the search bar
1. Select **+ Create** and enter the following values:
    1. Basics > Resource group: rg-add-microservice-[uniqueid]
    1. Basics > Registry name: crms[uniqueid]
    1. Basics > Location: East US
    1. Basics > SKU: Basic
1. Navigate to the **Container Registry**
    1. Select **Settings > Access keys**
    1. Enable the **Admin user**

## Step 3: Push Container Images

1. In the Azure Portal (<https://portal.azure.com>), open the Cloud Shell
1. If you haven't alread, clone the Azure Dev Day repository:

    ```bash
    git clone https://github.com/microsoft/azure-dev-day-java
    ```

1. Navigate to the **3-microservice-lab** folder

    ```bash
    cd .\azure-dev-day-java\3-microservice-lab
    ```

1. Push the container image for the Web API

    ```bash
    az acr build --image sample/demoweb-api:v1 --registry crms[uniqueid] --file .\WebAPI\Dockerfile .
    ```

1. Push the container image for the Web App

    ```bash
    az acr build --image sample/demoweb-app:v1 --registry crms[uniqueid] --file .\WebApp\Dockerfile .
    ```

## Step 4: Create the Web API Container App

1. In the Azure Portal (<https://portal.azure.com>), select **Container Apps** from the search bar
1. Select **+ Create** and enter the following values:
    1. Basics > Resource group: rg-add-microservice-[uniqueid]
    1. Basics > Container app name: ca-add-webapi-[uniqueid]
    1. Basics > Region: East US
    1. App settings > Use quickstart image: **unchecked**
    1. App settings > Registry: crms[uniqueid].azurecr.io
    1. App settings > Image: sample/demoweb-api
    1. App settings > Image tag: v1
    1. App settings > Ingress: Enabled
    1. App settings > Target port: 80
1. Navigate to the **Container App** and save the **Application URL** for later.

## Step 5: Create the Web App Container App

1. In the Azure Portal (<https://portal.azure.com>), select **Container Apps** from the search bar
1. Select **+ Create** and enter the following values:
    1. Basics > Resource group: rg-add-microservice-[uniqueid]
    1. Basics > Container app name: ca-add-webapp-[uniqueid]
    1. Basics > Region: East US
    1. App settings > Use quickstart image: **unchecked**
    1. App settings > Registry: crms[uniqueid].azurecr.io
    1. App settings > Image: sample/demoweb-app
    1. App settings > Image tag: v1
    1. App settings > Environment variables:
        1. ApiHost: [Application URL from above]
    1. App settings > Ingress: Enabled
    1. App settings > Ingress traffic: Accepting traffic from anywhere
    1. App settings > Target port: 80

## Step 6: Create a New Web API Revision

1. In the Azure Portal (<https://portal.azure.com>), navigate to your Web API Container App
1. Select **Application > Revision management**
1. Select **+ Create new revision**
1. Select the current **Container Image**
1. Add a new **Environment Variable**
    1. response: [Any response you want]

## Step 7: Clean up Resources

Do NOT forget to remove the resources once you have completed the lab.
