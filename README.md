# GraphStore

[![build](https://github.com/gephi/graphstore/actions/workflows/ci.yml/badge.svg)](https://github.com/gephi/graphstore/actions/workflows/ci.yml)
[![Coverage Status](https://coveralls.io/repos/gephi/graphstore/badge.svg?branch=master&service=github)](https://coveralls.io/github/gephi/graphstore?branch=master)

GraphStore is an in-memory graph structure implementation written in Java. It's designed to be powerful, efficient and robust. It's powering the Gephi software and supports large graphs in intensive applications.

## Features Highlight

* Blazing fast graph data structure optimized for reading and writing
* Comprehensive APIs to read and modify the graph structure
* Low memory footprint - reduced usage of Java objects and collections optimized for caching
* Supports directed, undirected and mixed graphs
* Supports parallel edges (i.e. edges can have a label)
* Any number of attributes can be associated with nodes or edges
* Thread-safe - Implements read-write locking mechanism to allow multiple reading threads
* Supports dynamic graphs (graphs over time)
* Built-in index on attribute values
* Fast and compact binary serialization
* Spatial indexing based on a quadtree

## Download

Stable releases can be found on [Maven central](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.gephi%22%20AND%20a%3A%22graphstore%22).

Development builds can be found on [Sonatype's Snapshot Repository](https://oss.sonatype.org/content/repositories/snapshots/org/gephi/graphstore/).

## Documentation

API Documentation is available [here](https://www.javadoc.io/doc/org.gephi/graphstore/latest/index.html).

## Dependencies

GraphStore depends on FastUtil >= 6.0, Colt 1.2.0 and Joda-Time 2.2.

For a complete list of dependencies, consult the `pom.xml` file.

## Developers

### How to build

GraphStore uses Maven for building. 

	> mvn clean install
		
### How to test

	> mvn test

## How to obtain code coverage report

	> mvn jacoco:report

## How to run the benchmark code

	> mvn integration-test

## Contribute

The source code is available under the Apache 2.0 license. Contributions are welcome.
