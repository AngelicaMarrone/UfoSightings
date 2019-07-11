package it.polito.tdp.ufo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SightingsDAO dao;

	

	//scelta tipo valori lista

	private List<String> vertex;

	

	//scelta tra uno dei due edges

	private List<Adiacenza> edges;

	

	//scelta tipo vertici e tipo archi

	public Model() {
		super();
		dao= new SightingsDAO();
	}



	private Graph<String, DefaultWeightedEdge> graph;



	 
public List<String> creaGrafo(Integer anno) {

		

		//scelta tipo vertici e archi

		graph = new SimpleDirectedGraph<String,DefaultWeightedEdge>( DefaultWeightedEdge.class);

		

		//scelta tipo valori lista

		vertex = new ArrayList<String>(dao.getVertex(anno));

		Graphs.addAllVertices(graph,vertex);

		

		edges = new ArrayList<Adiacenza>(dao.getEdges(anno));

		

		for(Adiacenza a : edges) {

			

			//CASO BASE POTRESTI DOVER AGGIUNGERE CONTROLLI

			String source = a.getId1();

			String target = a.getId2();
			
			int peso=1;
			
			graph.addEdge(source, target);

			System.out.println("AGGIUNTO ARCO TRA: "+source.toString()+" e "+target.toString());

		}

		

		System.out.println("#vertici: "+graph.vertexSet().size());

		System.out.println("#archi: "+graph.edgeSet().size());

		return vertex;

	}
	

	



	public List<AvvistamentiAnno> getTendina() {
	
		
		List<Integer> anno= new ArrayList<Integer>(dao.listAnni());
		List<AvvistamentiAnno> av= new ArrayList<AvvistamentiAnno>();
		
		for (Integer a: anno)
		{
			av.add(new AvvistamentiAnno(a, dao.getAvvistamenti(a)));
		}
		
		return av;
	}






	public String trova(String stato) {
		String ris= "Gli stati immediamente precedenti sono:\n";
		
		List<String> prima= Graphs.predecessorListOf(graph, stato);
		
		for(String p: prima)
		{
			ris+= "- "+ p+ "\n";
		}
		
		List<String> dopo= Graphs.successorListOf(graph, stato);
		
		ris+= "Gli stati immediatamente successivi sono:\n";
		
		for(String d: dopo)
		{
			ris+= "- "+ d+ "\n";
		}
		
		List<String> cc= calcolaComponenteConnessa(stato);
		
		ris+= "La componente connessa ha dimensione " + (cc.size()-1) + " ed è composta da: \n";
		int i;
		for (i=1; i<cc.size(); i++)
		{
			ris+= "- "+ cc.get(i) + "\n";
		}
		
		return ris;
		
	}






	private List<String> calcolaComponenteConnessa(String stato) {
		
		Map<String,String> backVisit;
		
		List<String> risultato = new ArrayList<String>();

		backVisit = new HashMap<>();



		//Creo iteratore e lo associo al grafo       

		//GraphIterator<Fermata, DefaultEdge> it = new BreadthFirstIterator<>(this.grafo,source); //in ampiezza

		GraphIterator<String, DefaultWeightedEdge> it = new DepthFirstIterator<>(this.graph, stato); //in profondita'



		it.addTraversalListener(new EdgeTraversedListener(backVisit, graph)); //Questa classe potrebbe essere definita anche dentro la classe Model

		//A fine iterazione mi ritroverò la mappa back riempita



		//Devo popolare la mappa almeno col nodo sorgente

		backVisit.put(stato, null);



		while(it.hasNext()) {

			risultato.add(it.next());

		}



		return risultato;

	}



	
}
