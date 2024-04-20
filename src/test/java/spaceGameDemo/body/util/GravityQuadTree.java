package spaceGameDemo.body.util;

public class GravityQuadTree {
    private float centerX, centerY;
    private float radius;

    public GravityQuadTree(float centerX, float centerY, float radius){
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    private class Node{
        private float mass;
        private Node parent;
        private Node leftTop;
        private Node rightTop;
        private Node leftBottom;
        private Node rightBottom;
        private float centerX;
        private float centerY;
        private float radius;

        public Node(float centerX, float centerY, float radius){
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }
    }
}
