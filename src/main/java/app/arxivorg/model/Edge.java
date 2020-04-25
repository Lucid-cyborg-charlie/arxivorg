package app.arxivorg.model;

import java.util.Objects;

public class Edge {
    private Summit s1;
    private Summit s2;

    public Edge(Summit s1, Summit s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public Summit getS1() {
        return s1;
    }

    public Summit getS2() {
        return s2;
    }

    public void setS1(Summit s1) {
        this.s1 = s1;
    }

    public void setS2(Summit s2) {
        this.s2 = s2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return (s1.equals(edge.s1) || s1.equals(edge.s2)) && (s2.equals(edge.s2) || s2.equals(edge.s1));
    }

    @Override
    public int hashCode() {
        return (Objects.hash(s1, s2)+Objects.hash(s2, s1));
    }

    @Override
    public String toString() {
        return "Edge{" +
                "s1=" + s1 +
                ", s2=" + s2 +
                "}\n\n";
    }
}
