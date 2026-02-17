package org.example.lesson02;

public class PrinterCartridge {

    public float calculateCartridges(int cartridges) {
        int minimumOrderQuantity = 5;
        int minimumOrderQuantityForDiscount = 100;
        if(cartridges < minimumOrderQuantity){
            throw new IllegalArgumentException("Minimum order quantity is "
                    + minimumOrderQuantity);
        }
            if(cartridges >= minimumOrderQuantityForDiscount) {
                return 0.2f;
            }


        return 0.0f;
    }
}
