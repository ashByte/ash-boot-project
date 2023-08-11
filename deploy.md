# Ashley Interview Prep Agenda

## (1) Review application [X]
## (2) Deploy application to Azure

To "deploy" an application means to get it off your local machine so that it can be accessed by other users!

The "cloud" is a generic term for "someone else's server running my application."

### Deployment Targets

- A Virtual Machine (VM)
- A Docker container on a Virtual Machine
- Kubernetes (a cluster of VMs running Docker containers)

#### Virtual Machines

A server running some operating system, but not the WHOLE server. You are allocated a sandboxed slice of compute.

This is different than a container, which is typically ANOTHER sandbox that can run inside a VM.

#### Containers

A typical application has several dependencies, all of which are machine-specific.

For example, your application is currently running on a WINDOWS operating system with a JAVA installation (jdk and the jre) and MAVEN.

If I wanted to run your application, I would have to INSTALL java (for linux) and maven (linux).

Typically, if people want to run your application, they dont wanna have to install all the machine-specific dependencies to do so.

The purpose of **containers** is to package an application WITH its dependencies so it can run ANYWHERE.

#### Kubernetes

If you have a ton of services, they can get tough to manage and network between and independently scale, so Kubernetes is a "cluster" of these containers
(that may run on MULTIPLE VMs). This cluster is managed by developers to expose and share their application to the world.

### Deploying an application

1. Test code => `mvn clean test`
2. Build code => `mvn clean package`
3. Build image from code and all dependencies => `docker build . -t docker.io/ashByte/ash-boot:0.0.1`
4. Deploy image as container on VM => `docker push docker.io/ashByte/ash-boot:0.0.1 && azure deploy ...`

## (3) Understand all components of application

## (4) Discussion on scaling application / architecture / microservices

