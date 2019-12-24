public class Node {
    private Node parent;
    private double f;
    private double g;
    private double h;
    private Point p;
    public Node(Node parent,Point p, double g, double h){
        this.parent=parent;
        this.f=g+h;
        this.g=g;
        this.h=h;
        this.p=p;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public Node getParent() {
        return parent;
    }

    public Point getP() {
        return p;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null){
            return false;
        }
        if(! (obj instanceof Node)){
            return false;
        }
        Node o = (Node) obj;
        return this.getP().x==o.getP().x&&this.getP().y==o.getP().y;
    }
}
