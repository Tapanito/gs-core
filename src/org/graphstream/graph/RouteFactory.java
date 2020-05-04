package org.graphstream.graph;

@FunctionalInterface
public interface RouteFactory<T extends Route> {

	T newInstance();
}
