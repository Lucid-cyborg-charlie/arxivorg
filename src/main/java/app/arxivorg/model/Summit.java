package app.arxivorg.model;

import java.util.Objects;

public class Summit {
    private String name;
    private Point point;

    public Summit(String name, Point point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Summit summit = (Summit) o;
        return name.equals(summit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, point);
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", point=" + point +
                '}';
    }
}
