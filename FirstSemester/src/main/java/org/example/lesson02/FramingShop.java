package org.example.lesson02;

import org.example.exceptions.InvalidFrameSizeException;

public class FramingShop {

    public int calculatePrice(int width, int height) throws InvalidFrameSizeException {
        if(width < 30 || width > 100) {
            throw new InvalidFrameSizeException("Width must be between 30 and 100.");
        }

        if(height < 30 || height > 60){
            throw new InvalidFrameSizeException("Height must be between 30 and 60.");
        }

        int surfaceArea = width * height;

        if(surfaceArea < 1600){
            return 3500;
        } else return 3000;

    }
}
