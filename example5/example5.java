class Shape { // Supertyp
    int x;
    int y;
}

class Rectangle extends Shape { // Subtype from Shape in Nominal Typed Languages, because Rectangle extends Shape
    int height;
    int width;
}

class Rect { // This is not a Subtype from Shape
    int x;
    int y;
    int height;
    int width;
}

public void main(String[] args) {
    Shape rectangle1 = Rectangle(); // Possible, because Rectangle is a Sutype from Shape
    Shape rectangle2 = Spape(); 
    Shape rectangle3 = Rect(); // Error, this is not possible, but possible in Structual Typed Languages 
}