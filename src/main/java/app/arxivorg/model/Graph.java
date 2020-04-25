package app.arxivorg.model;

import java.util.*;

public class Graph {
    private Set<Edge> edges;
    private List<String> authors;
    private Set<Summit> summits;
    private ManagerArticle managerArticle;

    public Graph(ManagerArticle managerArticle){
        this.edges = new HashSet<>();
        this.summits = new HashSet<>();
        this.managerArticle = managerArticle;
        this.authors = new ArrayList<>();
        authors.addAll(managerArticle.getAuthors());
        buildSetSummits(authors);
        buildEdges();
    }


    /**
     * Build the edges of graph
     */
    private void buildEdges(){
        for(Summit s1: summits){
            for(Summit s2: summits){
                if(!s1.equals(s2) && managerArticle.isCoauthors(s1.getName(), s2.getName())){
                    edges.add(new Edge(s1, s2));
                }
            }
        }
    }

    /**
     * Build the summits of graph
     * @param authors
     */
    private void buildSetSummits(List<String> authors){
        List<Point> points = new ArrayList<>();
        Random random = new Random();
        int count=0;
        while(count < authors.size()){
            int x = random.nextInt(120)*10;
            int y = random.nextInt(78)*10;
            Point point = new Point(x,y);
            if(!points.contains(point)){
                summits.add(new Summit(authors.get(count), point));
                points.add(point);
                count++;
            }
        }
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Set<Summit> getSummits() {
        return summits;
    }
}
