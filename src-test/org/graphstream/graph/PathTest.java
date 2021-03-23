package org.graphstream.graph;

import org.graphstream.graph.implementations.DefaultGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathTest {

	@Test(expected = IllegalStateException.class)
	public void setRoot_rootNodeMustBeNull() {
		Graph graph = createSimpleGraph();
		Path path = new Path();

		path.setRoot(graph.getNode("a"));

		// this has to fail, as root is already set
		path.setRoot(graph.getNode("b"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void add_nodeHeadMustBeInEdge() {
		Graph graph = createSimpleGraph();
		Path path = new Path();

		path.setRoot(graph.getNode("a"));

		// this has to fail as there is no edge between nodes "a" and "c"
		path.add(graph.getEdge("cd"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void add_whenAddingEdgeRootMustBeSet() {
		Graph graph = createSimpleGraph();
		Path path = new Path();

		// this has to fail as root of the path is not set
		path.add(graph.getEdge("ab"));
	}

	@Test
	public void clone_createsShallowCopy() {
		Graph graph = createSimpleGraph();
		Path path = new Path();
		path.add(graph.getNode("a"), graph.getEdge("ab"));
		path.add(graph.getEdge("bc"));

		Path clone = path.copy();
		assertEquals(path.size(), clone.size());
		assertEquals(path, clone);
	}

	@Test
	public void join_joinsTwoPaths() {
		Graph graph = createSimpleGraph();
		Path p1 = new Path();
		p1.add(graph.getNode("a"), graph.getEdge("ab"));
		Path p2 = new Path();
		p2.add(graph.getNode("b"), graph.getEdge("bc"));

		Path expected = new Path();
		expected.add(graph.getNode("a"), graph.getEdge("ab"));
		expected.add(graph.getEdge("bc"));

		Route actual = p1.join(p2);

		assertEquals(expected, actual);
	}

	private Graph createSimpleGraph() {
		Graph graph = new DefaultGraph("test");
		graph.setStrict(false);
		graph.setAutoCreate(true);

		graph.addEdge("ab", "a", "b");
		graph.addEdge("bc", "b", "c");
		graph.addEdge("cd", "c", "d");

		return graph;
	}
}