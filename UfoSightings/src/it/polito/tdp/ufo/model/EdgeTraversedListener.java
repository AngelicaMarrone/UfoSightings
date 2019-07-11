package it.polito.tdp.ufo.model;


import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultWeightedEdge;

public class EdgeTraversedListener implements TraversalListener<String, DefaultWeightedEdge> {



		Graph<String, DefaultWeightedEdge> grafo;

		Map<String,String> back;



		public EdgeTraversedListener(Map<String, String> back,Graph<String, DefaultWeightedEdge> grafo) {

			super();

			this.back = back;

			this.grafo=grafo;

		}



		@Override

		public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {		

		}



		@Override

		public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {		

		}



		public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> ev) {	



			/*Back codifica le relazioni si tipo CHILD -> PARENT

			 * 

			 * Per un nuovo vertice 'CHILD' scoperto devo avere che:

			 * 

			 * -CHILD è ancora sconosciuto (non ancora scoperto)

			 * -PARENT è gia stato visitato

			 */



			//Estraggo gli estremi dell'arco

			String sourceVertex = grafo.getEdgeSource(ev.getEdge());

			String targetVertex = grafo.getEdgeTarget(ev.getEdge());



			/*

			 * Se il grafo e' orientato, allora SOURCE==PARENT , TARGET==CHILD

			 * Se il grafo NON è orientato potrebbe essere il contrario..

			 */



			//Codice da riutilizzare

			if( !back.containsKey(targetVertex) && back.containsKey(sourceVertex)) {

				back.put(targetVertex, sourceVertex);

			} else if(!back.containsKey(sourceVertex) && back.containsKey(targetVertex)) {

				back.put(sourceVertex, targetVertex);

			}

		}



		@Override

		public void vertexFinished(VertexTraversalEvent<String> arg0) {		

		}



		@Override

		public void vertexTraversed(VertexTraversalEvent<String> arg0) {		

		}



		


	}
