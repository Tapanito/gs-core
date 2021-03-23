package org.graphstream.graph;

import java.util.List;

public interface Route extends Structure {

	/**
	 * Get the root (the first node) of the path.
	 *
	 * @return the root of the path.
	 */
	Node getRoot();

	/**
	 * Set the root (first node) of the path.
	 *
	 * @param root
	 * 		The root of the path.
	 */
	void setRoot(Node root);

	/**
	 * Adds a node and an edge to the path. If root is not set, the node will be
	 * set as root. Otherwise from node must be the same as the head node of the
	 * path.
	 *
	 * @param from
	 * 		The start node.
	 * @param edge
	 * 		The edge used.
	 */
	void add(Node from, Edge edge);

	/**
	 * Add an edge to the path.
	 *
	 * @param edge
	 * 		The edge to add to the path.
	 */
	void add(Edge edge);

	/**
	 * Says whether the path contains this node or not.
	 *
	 * @param node
	 * 		The node tested for existence in the path.
	 * @return <code>true</code> if the path contains the node.
	 */
	boolean contains(Node node);

	/**
	 * Says whether the path contains this edge or not.
	 *
	 * @param edge
	 * 		The edge tested for existence in the path.
	 * @return <code>true</code> if the path contains the edge.
	 */
	boolean contains(Edge edge);

	/**
	 * Returns true if the path is empty.
	 *
	 * @return <code>true</code> if the path is empty.
	 */
	boolean empty();

	/**
	 * Returns the size of the path
	 */
	int size();

	List<Edge> getEdgePath();

	List<Node> getNodePath();

	Route copy();

	default Route join(Route other) {
		if (size() == 0) {
			return other.copy();
		}

		if (!other.getRoot().equals(getNodePath().get(size() - 1))) {
			throw new IllegalStateException(
					String.format("Root node %s does not match head node %s",
							other.getRoot(), getNodePath().get(size() - 1))
			);
		}

		Route clone = copy();

		other.edges().forEach(clone::add);
		return clone;
	}
}
