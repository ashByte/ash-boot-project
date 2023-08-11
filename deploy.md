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
4. Deploy image as container on VM:

```bash
# create VM on Azure UI
docker push docker.io/ashByte/ash-boot:0.0.1
ssh "${AZURE_USER}@${AZURE_VM_IP}"
sudo apt install docker.io
docker run -p 80:4001 "docker.io/${DOCKER_USER}/${DOCKER_IMAGE}:${VERSION}"
# open $AZURE_VM_IP in browser
```

## (3) Understand all components of application

### Spring Boot Lifecycle

The Spring Boot Lifecycle is the "phases" that your application goes through while it is starting up.

This lifecycle is initiated in `BootsApplication.java` in the `main` method. We call this the app's "entrypoint."

From here, the "magic" of Spring Boot kicks in, and through the following equivalent concepts:

- inversion-of-control (standard, language-agnostic)
- beans (spring specific)
- autowiring (legacy spring)
- dependency injection (standard, language agnostic)

... the application magically connects each different component into the "app."

Anything that has an annotation imported from a Spring package can be a spring "bean."

A Spring "bean" is anything that is in the spring application's "context."

Things that are in the spring "context" are candidates for autowiring into a spring application.

Once a spring application has satisfied the dependencies of all of its beans, it is able to start.

## (4) Discussion on scaling application / architecture / microservices

What is a microservice??????
- A microservice is just an API that performs a specific task. We call such an API a microservice when it's in the context of other microservices.

Why do we use microservices?????
- When an application has many components, sometimes, some components are used more heavily than others, or are written in different languages, or need to be accessible by different users.
- This type of segregation of capability is hard/impossible to implement when all of these components are in the same service.
- We use microservices to create independently manageable and controllable "chunks" of our application.

### EXAMPLE: Outfit Shopping Application

- Aggregator (Outfit App)
- Microservices
    - Boots Service (x1) => talks to a dedicated Boots DB
    - Hats Service (x5) => all replicas talk to a single Hats DB
    - Pants Service (x10) => ...
    - Shirts Service (x5)
    - Checkout Service (x20)

### Scaling

When we started this project, we picked a super cheap VM with only 0.5Gb of RAM. This wasn't able to support even a single instance of our application, so we had to employ VERTICAL SCALING to make the VM more powerful, with more RAM, to allow our application to start.

If we ended up having 500 users of our service, they might start to overload even the VERTICAL SCALING solution, AND the vertical scaling solution is not always cost-effective. In these scenarios, we can employ HORIZONTAL SCALING, such that we have multiple replicas of our app on potentially multiple VMs. In front of these replicas, we have a load balancer that applies a "round-robin" strategy to select the replica to send the user request to.
