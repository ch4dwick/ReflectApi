# Simple Exposure of REST Endpoints using Java Reflections

## Overview

I came up with this algorithm when building a REST API server. I wanted to be able to generate methods declared as endpoints in the code on the fly. The original ideas we had was either declare the list of endpoints in a database, properties/xml file, or have the code dynamically generate the endpoints when needed.

Since most the Objects in the original code was proprietary, the closest parallel library I can use as an example was the one that inspired it: Spring Boot. 

Most seasoned Spring Developers would probably point out the usage of Spring Actuator or whatnot but note that we're not actually using this in my original code. The algorithm's aim was to be agnostic so rather than use Spring's built-in APIs I'm just merely using its Annotations as practical examples of how to use the algorithm. 

The algorithm is as follows:

1. Get ```this``` class or any target class' declared methods.
2. Check if that method is declared as ```RequestMapping```.  This tells us that this method is an endpoint.
3. If an explicit value is set in the annotation use that as the name of the endpoint, otherwise, assume the method name is endpoint's name.
4. Here's the tricky part. Because java cannot tell you the paramter's name at run time - it iterates over tham as ```argN``` - we'll need to use some other indicator to get the name. In this case, Spring has a ```RequestParam(name="")``` annotation. We used a similar annotation approach in our company's code so we were able to extract a more meaningful string label out of it. You may or may not need this but having a label helps developers identify what the parameters are for.
5. Get the list of supported request methods.
6. Finally populate an array of all those endpoints and return it.

Just like in Spring Boot, our framework also returns the results in JSON format.


