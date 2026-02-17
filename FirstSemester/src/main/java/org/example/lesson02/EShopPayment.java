package org.example.lesson02;

public class EShopPayment {

    public int calculateDiscount(float amountPaid) {
        float zeroDiscount = 300.00f;
        float fivePercentDiscount = 800.00f;

        if (Float.isNaN(amountPaid) || Float.isInfinite(amountPaid) || amountPaid < 0) {
            throw new IllegalArgumentException("amountPaid must be a finite number >= 0");
        }

        if (amountPaid > fivePercentDiscount) {
            return 10;
        }

        if (amountPaid < zeroDiscount) {
            return 0;
        }

        if(amountPaid > zeroDiscount && amountPaid <= fivePercentDiscount){
            return 5;
        }

        return 0;
    }
}
