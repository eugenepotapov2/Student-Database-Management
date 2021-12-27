interface MyShapeInterface {
    static MyRectangle intersectMyShapes(MyShape object1, MyShape object2) {
        MyRectangle R1 = object1.getMyBoundingRectangle(MyColor.RANDOM);
        MyRectangle R2 = object2.getMyBoundingRectangle(MyColor.RANDOM);
        int x1 = R1.getX();
        int x2 = R2.getX();
        int y1 = R1.getY();
        int y2 = R2.getY();
        int w1 = R1.getWidth();
        int w2 = R2.getWidth();
        int h1 = R1.getHeight();
        int h2 = R2.getHeight();

        if (y1 + h1 < y2 || y1 > y2 + h2) {
            return null;
        }
        if (x1 + w1 < x2 || x1 > x2 + w2) {
            return null;
        }
        int xMax = Math.max(x1, x2);
        int yMax = Math.max(y1, y2);
        int xMin = Math.min(x1 + w1, x2 + w2);
        int yMin = Math.min(y1 + h1, y2 + h2);
        return new MyRectangle(new MyPoint(xMax, yMax, MyColor.RANDOM), Math.abs(xMin-xMax), Math.abs(yMin-yMax), MyColor.RANDOM);

    }

    boolean pointInMyShape(MyPoint point);

    MyRectangle getMyBoundingRectangle(MyColor color);

}