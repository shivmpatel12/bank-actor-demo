Bank Actor Demo
===============

Orbit Skunkworks is an example project based on the idea of a guestbook, designed to help developers understand how to use the orbit framework to build REST based online services and for them to experiment freely without complex project setup.

### Features Exposed
* Orbit Actor Sample
  * Actor messaging
  * In-memory state persistence
* JSON Web API
* Websockets
* Streams

### Project Structure Overview
The skunkworks project is divided into 3 smaller modules.
* interfaces
  * Contains the orbit actor interfaces/api.
* backend
  * Contains the implementation of the orbit actor interfaces/api and a launcher.
* gateway
  * Contains the handlers, public REST interface and a launcher.
* single-launcher
  * Combines backend and gateway into a single launcher to run the entire solution in a single JVM.


### API Overview

**Adding a Post**

*Request Format*
```
POST /guestbook/{guestbookId}
{
   "name": "Joe",
   "comment": "This place was pretty awesome."
}
```
*Response Format*
```
204 NO CONTENT
```

**Retrieve Posts**

*Request Format*
```
GET /guestbook/{guestbookId}
```
*Response Format*
```
200 OK
[
  {
    "id": "86333ac1-a0b2-4a55-800b-eb16020db166",
    "name": "Joe",
    "comment": "This place was pretty awesome.",
    "date": 1464296773989
  }
]
```

**Delete a Post**

*Request Format*
```
DELETE /guestbook/{guestbookId}/{postId}
```
*Response Format*
```
204 NO CONTENT
```

**Delete a Guestbook**

*Request Format*
```
DELETE /guestbook/{guestbookId}
```
*Response Format*
```
204 NO CONTENT
```

**Websocket Stream**

### Stream Client
* Raw Websocket: /guestbook/ws
* Sample Web Client: /streamclient.html

Posts will be streamed to the web browser in real-time as they are posted.

```
Stream Client Sample

CONNECTED

RECEIVED: {"guestbook":"horribleHotel","name":"Joe","comment":"This place was awful","date":"Thu May 26 15:00:46 MDT 2016","id":"95af43f1-cb4f-4330-82bb-e3204499f30b"}

RECEIVED: {"guestbook":"niceHotel","name":"Joe","comment":"This place was pretty awesome.","date":"Thu May 26 15:01:07 MDT 2016","id":"269fbc74-6a63-47fd-a954-90b646c0f9d3"}

DISCONNECTED
```

### Running the Project
#### Running Requirements
In order to launch this project you must launch:
* At least 1 gateway & at least 1 backend **or**
* A single launcher configuration

#### From IDE
* backend: com.ea.eadpm.skunkworks.BackendBootstrap
* gateway: com.ea.eadpm.skunkworks.GatewayBootstrap
* single-launcher: com.ea.eadpm.skunkworks.SingleEnvironmentLauncher

#### From Command Line

*Single JVM Environment*
```
# Build
mvn clean install

# Launch Single JVM Environment
cd single-launcher
mvn exec:java
```

*Multi Environment*
```
# Build
mvn clean install

# Launch Backend
cd backend
mvn exec:java

# Launch Gateway
cd gateway
mvn exec:java
```

### Important Notes

#### Storage Restrictions
By default the skunkworks will use in-memory storage for actor persistence, as such storage is only guaranteed to be consistent when a single back-end is running. Multiple backends can result in inconsistent state after actor deactivation and similar scenarios.


#### Configurations
Orbit is configured via the conf/orbit.yaml file in each of the launchable projects.

If you wish to add new APIs/Handlers you must ensure that their package or class is added to the orbit.container.* configuration path. See the existing configuration for examples.

The storage provider is only present on the backend, and can also be changed in the orbit.yaml. See the existing definition for an example.

#### API Design
The API design of the guestbook service in this sample is not optimal and does not conform to accepted REST practices. It serves as a simple example.

### Succesful Logs
```
[main] INFO cloud.orbit.container.Container - Starting orbit container...
[main] INFO cloud.orbit.container.Container - Container discovered 2 addons.
[main] INFO cloud.orbit.container.Container - Container considered 10 classes and discovered 4 services.
[main] INFO cloud.orbit.web.EmbeddedJettyServer - Starting Jetty server...
[main] INFO org.eclipse.jetty.util.log - Logging initialized @1415ms
[main] INFO cloud.orbit.web.EmbeddedJettyServer - file:/C:/Users/joehe/.m2/repository/cloud/orbit/orbit-jetty/0.8.2/orbit-jetty-0.8.2.jar
[main] WARN org.eclipse.jetty.server.handler.ContextHandler - o.e.j.w.WebAppContext@6d1310f6{/,null,null}{file:/C:/Users/joehe/.m2/repository/cloud/orbit/orbit-jetty/0.8.2/orbit-jetty-0.8.2.jar} contextPath ends with /*
[main] WARN org.eclipse.jetty.server.handler.ContextHandler - Empty contextPath
[main] INFO org.eclipse.jetty.server.Server - jetty-9.3.8.v20160314
[main] INFO org.eclipse.jetty.server.handler.ContextHandler - Started o.e.j.s.h.ContextHandler@7e46d648{/,null,AVAILABLE}
[main] INFO org.eclipse.jetty.webapp.StandardDescriptorProcessor - NO JSP Support for /, did not find org.eclipse.jetty.jsp.JettyJspServlet
[main] INFO org.eclipse.jetty.server.handler.ContextHandler - Started o.e.j.w.WebAppContext@6d1310f6{/,file:///C:/Users/joehe/AppData/Local/Temp/jetty-0.0.0.0-8080-orbit-jetty-0.8.2.jar-_-any-563516072033951786.dir/webapp/,AVAILABLE}{file:/C:/Users/joehe/.m2/repository/cloud/orbit/orbit-jetty/0.8.2/orbit-jetty-0.8.2.jar}
[main] INFO org.eclipse.jetty.server.ServerConnector - Started ServerConnector@47768e74{HTTP/1.1,[http/1.1]}{0.0.0.0:8080}
[main] INFO org.eclipse.jetty.server.Server - Started @2309ms
[main] INFO cloud.orbit.web.EmbeddedJettyServer - Jetty server started.
May 26, 2016 9:49:44 PM org.infinispan.remoting.transport.jgroups.JGroupsTransport start
INFO: ISPN000078: Starting JGroups channel orbit-skunkworks-cluster
May 26, 2016 9:49:44 PM org.jgroups.stack.DiagnosticsHandler bindToInterfaces
WARNING: failed to join /224.0.75.75:7500 on net0: java.net.SocketException: Unrecognized Windows Sockets error: 0: no Inet4Address associated with interface

-------------------------------------------------------------------
GMS: address=orbit-skunkworks-single-host, cluster=orbit-skunkworks-cluster, physical address=192.168.1.74:50916
-------------------------------------------------------------------
May 26, 2016 9:49:46 PM org.infinispan.remoting.transport.jgroups.JGroupsTransport viewAccepted
INFO: ISPN000094: Received new cluster view for channel orbit-skunkworks-cluster: [orbit-skunkworks-single-host|0] (1) [orbit-skunkworks-single-host]
May 26, 2016 9:49:46 PM org.infinispan.remoting.transport.jgroups.JGroupsTransport startJGroupsChannelIfNeeded
INFO: ISPN000079: Channel orbit-skunkworks-cluster local address is orbit-skunkworks-single-host, physical addresses are [192.168.1.74:50916]
May 26, 2016 9:49:46 PM org.infinispan.factories.GlobalComponentRegistry start
INFO: ISPN000128: Infinispan version: Infinispan 'Mahou' 8.1.3.Final
[ForkJoinPool.commonPool-worker-1] INFO cloud.orbit.actors.cluster.JGroupsClusterPeer - Registering the local address
[ForkJoinPool.commonPool-worker-1] INFO cloud.orbit.actors.cluster.JGroupsClusterPeer - Done with JGroups initialization
[main] INFO cloud.orbit.container.Container - Container successfully started.
```