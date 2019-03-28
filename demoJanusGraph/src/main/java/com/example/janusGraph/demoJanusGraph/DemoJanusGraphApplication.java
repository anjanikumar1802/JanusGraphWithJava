package com.example.janusGraph.demoJanusGraph;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphTransaction;
import org.janusgraph.core.attribute.Geoshape;
import org.janusgraph.example.GraphOfTheGodsFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoJanusGraphApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(DemoJanusGraphApplication.class, args);
//	}

	public static void main(String[] args) {

		JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-cassandra-follow.properties");
		GraphTraversalSource g = graph.traversal();

		if (g.V().count().next() == 0) {
			// load the schema and graph data
			System.out.println(g.V().count().next() + "  ***** -----------------");
			GraphOfTheGodsFactory.loadWithoutMixedIndex(graph, true);
		}

		System.out.println(g.V().count().next() + " ---=-=-=-count is ");
		
		generateGraphData(graph);

		// look up vertex by name can use a composite index in JanusGraph
		final Optional<Map<Object, Object>> v = g.V().has("name", "marko").valueMap(true).tryNext();
		if (v.isPresent()) {
			System.out.println(v.get().toString() + " =========    marko is present");
		} else {
			System.out.println("-------------- marko not found");
		}

		// look up an incident edge
		final Optional<Map<Object, Object>> edge = g.V().has("name", "marko").outE("created").as("e").inV()
				.has("name", "lop").select("e").valueMap(true).tryNext();
		if (edge.isPresent()) {
			System.out.println(edge.get().toString() + "========== software edge is there");
		} else {
			System.out.println("--------------- edge not found");
		}

		// numerical range query can use a mixed index in JanusGraph
		final List<Object> list = g.V().has("age", P.gte(20)).values("age").toList();
		System.out.println(list.toString() + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		
		Optional<Map<Object, Object>> lis = g.V().valueMap(true).tryNext();
		
		System.out.println("-=-=-=-------------------------------------------------------------------------------=-=-====");
		System.out.println(lis.get().toString());
		System.out.println("-=-=-=-------------------------------------------------------------------------------=-=-====");
		System.exit(0);
	}
	
	public static void generateGraphData(JanusGraph graph) {
		JanusGraphTransaction tx = graph.newTransaction();
		// vertices

		Vertex v1 = tx.addVertex(T.label, "person1", "name", "marko1", "age", 26);
		Vertex v2 = tx.addVertex(T.label, "software1", "name", "lop1");
		
		v1.addEdge("created", v2);

		// commit the transaction to disk
		tx.commit();
	}
	
	
	
//	public static void generateGraphData(JanusGraph graph) {
//	JanusGraphTransaction tx = graph.newTransaction();
//	// vertices
//
//	Vertex saturn = tx.addVertex(T.label, "titan", "name", "saturn1", "age", 10000);
//	Vertex sky = tx.addVertex(T.label, "location", "name", "sky1");
//	Vertex sea = tx.addVertex(T.label, "location", "name", "sea1");
//	Vertex jupiter = tx.addVertex(T.label, "god", "name", "jupiter1", "age", 5000);
//	Vertex neptune = tx.addVertex(T.label, "god", "name", "neptune1", "age", 4500);
//	Vertex hercules = tx.addVertex(T.label, "demigod", "name", "hercules1", "age", 30);
//	Vertex alcmene = tx.addVertex(T.label, "human", "name", "alcmene1", "age", 45);
//	Vertex pluto = tx.addVertex(T.label, "god", "name", "pluto1", "age", 4000);
//	Vertex nemean = tx.addVertex(T.label, "monster", "name", "nemean1");
//	Vertex hydra = tx.addVertex(T.label, "monster", "name", "hydra1");
//	Vertex cerberus = tx.addVertex(T.label, "monster", "name", "cerberus1");
//	Vertex tartarus = tx.addVertex(T.label, "location", "name", "tartarus1");
//
//	// edges
//	jupiter.addEdge("father", saturn);
//	jupiter.addEdge("lives", sky, "reason", "loves fresh breezes");
//	jupiter.addEdge("brother", neptune);
//	jupiter.addEdge("brother", pluto);
//
//	neptune.addEdge("lives", sea).property("reason", "loves waves");
//	neptune.addEdge("brother", jupiter);
//	neptune.addEdge("brother", pluto);
//
//	hercules.addEdge("father", jupiter);
//	hercules.addEdge("mother", alcmene);
//	hercules.addEdge("battled", nemean, "time", 1, "place", Geoshape.point(38.1f, 23.7f));
//	hercules.addEdge("battled", hydra, "time", 2, "place", Geoshape.point(37.7f, 23.9f));
//	hercules.addEdge("battled", cerberus, "time", 12, "place", Geoshape.point(39f, 22f));
//
//	pluto.addEdge("brother", jupiter);
//	pluto.addEdge("brother", neptune);
//	pluto.addEdge("lives", tartarus, "reason", "no fear of death");
//	pluto.addEdge("pet", cerberus);
//
//	cerberus.addEdge("lives", tartarus);
//
//	// commit the transaction to disk
//	tx.commit();
//}

}














































//
//public static void createVertices(JanusGraph graph) {
//	JanusGraphTransaction tx = graph.newTransaction();
//	// vertices
//
//	Vertex saturn = tx.addVertex(T.label, "titan", "name", "saturn", "age", 10000);
//	Vertex sky = tx.addVertex(T.label, "location", "name", "sky");
//	Vertex sea = tx.addVertex(T.label, "location", "name", "sea");
//	Vertex jupiter = tx.addVertex(T.label, "god", "name", "jupiter", "age", 5000);
//	Vertex neptune = tx.addVertex(T.label, "god", "name", "neptune", "age", 4500);
//	Vertex hercules = tx.addVertex(T.label, "demigod", "name", "hercules", "age", 30);
//	Vertex alcmene = tx.addVertex(T.label, "human", "name", "alcmene", "age", 45);
//	Vertex pluto = tx.addVertex(T.label, "god", "name", "pluto", "age", 4000);
//	Vertex nemean = tx.addVertex(T.label, "monster", "name", "nemean");
//	Vertex hydra = tx.addVertex(T.label, "monster", "name", "hydra");
//	Vertex cerberus = tx.addVertex(T.label, "monster", "name", "cerberus");
//	Vertex tartarus = tx.addVertex(T.label, "location", "name", "tartarus");
//
//	// edges
//	jupiter.addEdge("father", saturn);
//	jupiter.addEdge("lives", sky, "reason", "loves fresh breezes");
//	jupiter.addEdge("brother", neptune);
//	jupiter.addEdge("brother", pluto);
//
//	neptune.addEdge("lives", sea).property("reason", "loves waves");
//	neptune.addEdge("brother", jupiter);
//	neptune.addEdge("brother", pluto);
//
//	hercules.addEdge("father", jupiter);
//	hercules.addEdge("mother", alcmene);
//	hercules.addEdge("battled", nemean, "time", 1, "place", Geoshape.point(38.1f, 23.7f));
//	hercules.addEdge("battled", hydra, "time", 2, "place", Geoshape.point(37.7f, 23.9f));
//	hercules.addEdge("battled", cerberus, "time", 12, "place", Geoshape.point(39f, 22f));
//
//	pluto.addEdge("brother", jupiter);
//	pluto.addEdge("brother", neptune);
//	pluto.addEdge("lives", tartarus, "reason", "no fear of death");
//	pluto.addEdge("pet", cerberus);
//
//	cerberus.addEdge("lives", tartarus);
//
//	// commit the transaction to disk
//	tx.commit();
//}
